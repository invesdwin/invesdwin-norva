package de.invesdwin.norva.apt.staticfacade.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import de.invesdwin.norva.apt.staticfacade.StaticFacadeDefinition;
import de.invesdwin.norva.beanpath.BeanPathObjects;
import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.BeanPathStrings;

@NotThreadSafe
public class StaticFacadeGenerator implements Runnable {

    private final ProcessingEnvironment env;
    private final StaticFacadeDefinition def;
    private final Element originatingElement;

    public StaticFacadeGenerator(final ProcessingEnvironment env, final StaticFacadeDefinition def,
            final Element originatingElement) {
        this.env = env;
        this.def = def;
        this.originatingElement = originatingElement;
    }

    @Override
    public void run() {
        // load target class
        final List<? extends TypeMirror> targetClasses = getTargetClasses();
        // create facade class file
        final String staticFacadeClassStr = def.name();
        final String staticFacadePackage = staticFacadeClassStr.substring(0, staticFacadeClassStr.lastIndexOf("."));
        final String staticFacadeClassName = staticFacadeClassStr.substring(staticFacadeClassStr.lastIndexOf(".") + 1);
        // write file content
        final String content = generateContent(targetClasses, staticFacadePackage, staticFacadeClassName);

        final FileObject fileObject;
        try {
            fileObject = env.getFiler()
                    .createResource(StandardLocation.SOURCE_OUTPUT, staticFacadePackage,
                            staticFacadeClassName + ".java", originatingElement);

            final Writer gen = fileObject.openWriter();
            try {
                gen.write(content);
            } finally {
                gen.close();
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateContent(final List<? extends TypeMirror> targetClasses, final String staticFacadePackage,
            final String staticFacadeClassName) {
        final StringBuilder sb = new StringBuilder();
        sb.append("package " + staticFacadePackage + ";\n");
        sb.append("\n");
        sb.append("public abstract class " + staticFacadeClassName + " {\n");
        sb.append("\n");

        final Set<String> duplicateMethodSignatureFilter = new HashSet<String>();
        for (final TypeMirror targetClassTypeMirror : targetClasses) {
            final TypeElement targetClass = (TypeElement) env.getTypeUtils().asElement(targetClassTypeMirror);
            sb.append("    ///////////// static facade methods for " + targetClass.getQualifiedName()
                    + " /////////////\n\n");
            TypeElement parentTargetClass = targetClass;
            while (parentTargetClass != null) {
                for (final Element element : parentTargetClass.getEnclosedElements()) {
                    generateMethodSignature(sb, duplicateMethodSignatureFilter, targetClass, element);
                }
                final TypeMirror superclass = parentTargetClass.getSuperclass();
                if (superclass == null) {
                    parentTargetClass = null;
                } else {
                    parentTargetClass = (TypeElement) env.getTypeUtils().asElement(superclass);
                }
            }
        }

        sb.append("}\n");
        return sb.toString();
    }

    private void generateMethodSignature(final StringBuilder sb, final Set<String> duplicateMethodSignatureFilter,
            final TypeElement targetClass, final Element element) {
        final Set<Modifier> modifiers = element.getModifiers();
        if (element instanceof ExecutableElement && element.getKind() == ElementKind.METHOD
                && modifiers.contains(Modifier.PUBLIC) && modifiers.contains(Modifier.STATIC)) {
            final ExecutableElement m = (ExecutableElement) element;
            final List<? extends VariableElement> params = m.getParameters();
            final List<String> paramNames = new ArrayList<String>();
            for (final VariableElement p : params) {
                paramNames.add(p.getSimpleName().toString());
            }

            final String seeMethodSignature = newSeeMethodSignature(targetClass, m, params);
            if (org.apache.commons.lang3.StringUtils.equalsAny(seeMethodSignature, def.filterSeeMethodSignatures())) {
                return;
            }
            final String methodSignature = newMethodSignature(m, params, paramNames);
            for (final String filterExpression : def.filterMethodSignatureExpressions()) {
                if (methodSignature.matches(filterExpression)) {
                    return;
                }
            }

            final String uniqueMethodSignature = newUniqueMethodSignature(m, params);
            if (duplicateMethodSignatureFilter.add(uniqueMethodSignature)) {
                addMethodSignature(sb, targetClass, m, params, methodSignature, seeMethodSignature, paramNames);
            }
        }
    }

    private String newMethodSignature(final ExecutableElement m, final List<? extends VariableElement> params,
            final List<String> paramNames) {
        final StringBuilder sb = new StringBuilder();
        // method signature
        sb.append("public static ");
        // generic type variables
        sb.append(generateGenericTypeArguments(m));
        // return type
        sb.append(typeToString(m.getReturnType(), false));
        // method name
        sb.append(" ");
        sb.append(m.getSimpleName());
        sb.append("(");
        // params
        for (int i = 0; i < params.size(); i++) {
            final boolean isVarArgsParam = m.isVarArgs() && i == params.size() - 1;
            sb.append(typeToString(params.get(i).asType(), isVarArgsParam));
            sb.append(" ");
            sb.append(paramNames.get(i));
            if (i < params.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private String generateGenericTypeArguments(final ExecutableElement m) {
        String genericTypeArgument = "";
        final List<? extends TypeParameterElement> genericTypeVariables = m.getTypeParameters();
        if (genericTypeVariables.size() > 0) {
            genericTypeArgument += "<";
            for (int i = 0; i < genericTypeVariables.size(); i++) {
                final TypeParameterElement typeParameterElement = genericTypeVariables.get(i);
                genericTypeArgument += typeParameterElement;
                boolean firstBound = true;
                for (final TypeMirror bound : typeParameterElement.getBounds()) {
                    if (firstBound) {
                        genericTypeArgument += " extends ";
                        firstBound = false;
                    } else {
                        genericTypeArgument += " & ";
                    }
                    genericTypeArgument += bound;
                }
                if (i < genericTypeVariables.size() - 1) {
                    genericTypeArgument += ", ";
                }
            }
            genericTypeArgument += "> ";
        }
        return genericTypeArgument.replace(" extends java.lang.Object", "");
    }

    private void addMethodSignature(final StringBuilder sb, final TypeElement targetClass, final ExecutableElement m,
            final List<? extends VariableElement> params, final String methodSignature, final String seeMethodSignature,
            final List<String> paramNames) {
        // javadoc
        sb.append("    /** @see ");
        sb.append(seeMethodSignature);
        sb.append(" */\n");
        if (BeanPathReflections.getAnnotation(env, m, Deprecated.class) != null) {
            sb.append("    @");
            sb.append(Deprecated.class.getName());
            sb.append("\n");
        }
        sb.append("    ");
        sb.append(methodSignature);
        sb.append(" ");
        // throws
        final List<? extends TypeMirror> genericExceptionTypes = m.getThrownTypes();
        if (genericExceptionTypes.size() > 0) {
            sb.append("throws ");
            for (int i = 0; i < genericExceptionTypes.size(); i++) {
                sb.append(typeToString(genericExceptionTypes.get(i), false));
                if (i < genericExceptionTypes.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        // body
        sb.append("{\n");
        sb.append("        ");
        if (m.getReturnType().getKind() != TypeKind.VOID) {
            sb.append("return ");
        }
        sb.append(targetClass.getQualifiedName() + "." + m.getSimpleName() + "(");
        for (int i = 0; i < params.size(); i++) {
            sb.append(paramNames.get(i));
            if (i < params.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(");\n");
        sb.append("    }\n");
        sb.append("\n");
    }

    private String newSeeMethodSignature(final TypeElement targetClass, final ExecutableElement m,
            final List<? extends VariableElement> params) {
        final StringBuilder sb = new StringBuilder();
        sb.append(targetClass.getQualifiedName() + "#" + m.getSimpleName() + "(");
        for (int i = 0; i < params.size(); i++) {
            final boolean isVarArgsParam = m.isVarArgs() && i == params.size() - 1;
            sb.append(BeanPathObjects
                    .removeGenericsFromQualifiedName(typeToString(params.get(i).asType(), isVarArgsParam)));
            if (i < params.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private String newUniqueMethodSignature(final ExecutableElement m, final List<? extends VariableElement> params) {
        final StringBuilder sb = new StringBuilder();
        sb.append(generateGenericTypeArguments(m));
        sb.append("#");
        sb.append(m.getSimpleName() + "(");
        for (int i = 0; i < params.size(); i++) {
            final boolean isVarArgsParam = m.isVarArgs() && i == params.size() - 1;
            sb.append(BeanPathObjects
                    .removeGenericsFromQualifiedName(typeToString(params.get(i).asType(), isVarArgsParam)));
            if (i < params.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private List<? extends TypeMirror> getTargetClasses() {
        try {
            def.targets();
            throw new RuntimeException(MirroredTypesException.class.getSimpleName() + " expected!");
        } catch (final MirroredTypesException e) {
            return e.getTypeMirrors();
        }
    }

    private String typeToString(final TypeMirror type, final boolean isVarArgsParam) {
        String str = type.toString();
        if (isVarArgsParam) {
            str = BeanPathStrings.replaceEnd(str, "[]", "...");
        }
        return str;
    }

}
