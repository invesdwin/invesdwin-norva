package de.invesdwin.norva.apt.constants.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.BeanPathStrings;
import de.invesdwin.norva.beanpath.impl.model.BeanModelContext;
import de.invesdwin.norva.beanpath.spi.BeanPathUtil;
import de.invesdwin.norva.beanpath.spi.element.IActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IPropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.SimpleBeanPathVisitorSupport;

@NotThreadSafe
public class ConstantsGeneratorVisitor extends SimpleBeanPathVisitorSupport {

    private final List<IBeanPathElement> constants = new ArrayList<IBeanPathElement>();

    public ConstantsGeneratorVisitor(final BeanModelContext context) {
        super(context);
    }

    @Override
    public BeanModelContext getContext() {
        return (BeanModelContext) super.getContext();
    }

    @Override
    public void visitProperty(final IPropertyBeanPathElement e) {
        constants.add(e);
    }

    @Override
    public void visitAction(final IActionBeanPathElement e) {
        constants.add(e);
    }

    @Override
    public void visitOther(final IBeanPathElement e) {
        constants.add(e);
    }

    private CharSequence generateConstant(final IBeanPathElement e, final String value, final String indent) {
        final StringBuilder sb = new StringBuilder();
        sb.append(indent);
        sb.append("/** ");
        sb.append(e.getClass().getSimpleName().toString());
        sb.append(": ");
        sb.append(org.apache.commons.text.StringEscapeUtils.escapeHtml4(e.getTypePath()));
        sb.append(" */\n");
        sb.append(indent);
        sb.append("public static final String ");
        sb.append(e.getBeanPath().replace(BeanPathUtil.BEAN_PATH_SEPARATOR, "_"));
        sb.append(" = \"");
        sb.append(value);
        sb.append("\";\n");
        return sb;
    }

    @Override
    public void finish() {
        final Collection<IBeanPathElement> elements = getContext().getElementRegistry().getElements();
        if (elements.isEmpty()) {
            return;
        }

        final TypeElement rootTypeElement = getContext().getRootContainer().getType().getTypeElement();
        final String originatingClassName = String.valueOf(rootTypeElement.getSimpleName());
        final String targetClassName = originatingClassName + "Constants";
        final String packageName = String.valueOf(rootTypeElement.getQualifiedName())
                .replace("." + originatingClassName, "");
        final String content = generateContent(elements, targetClassName, packageName);
        final FileObject fileObject;
        try {
            fileObject = getContext().getEnv()
                    .getFiler()
                    .createResource(StandardLocation.SOURCE_OUTPUT, packageName, targetClassName + ".java");
            final Writer w = fileObject.openWriter();
            try {
                w.write(content);
            } finally {
                if (w != null) {
                    w.close();
                }
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateContent(final Collection<IBeanPathElement> elements, final String targetClassName,
            final String packageName) {
        final StringBuilder sb = new StringBuilder();
        sb.append("package ");
        sb.append(packageName);
        sb.append(";\n");
        sb.append("\n");
        sb.append("public final class ");
        sb.append(targetClassName);
        sb.append(" {\n");
        sb.append("\n");
        sb.append("\tprivate ");
        sb.append(targetClassName);
        //CHECKSTYLE:OFF
        sb.append("() {}\n");
        //CHECKSTYLE:ON
        sb.append("\n");
        for (final IBeanPathElement constant : constants) {
            sb.append(generateConstant(constant, constant.getBeanPath(), "\t"));
        }
        sb.append("\n");

        //getters
        final StringBuilder getters = newGetters(targetClassName);
        if (getters != null) {
            sb.append(getters);
        }

        //setters
        final StringBuilder setters = newSetters(targetClassName);
        if (setters != null) {
            sb.append(setters);
        }

        //fields
        final StringBuilder fields = newFields(targetClassName);
        if (fields != null) {
            sb.append(fields);
        }

        //actions
        final StringBuilder actions = newActions(targetClassName);
        if (actions != null) {
            sb.append(actions);
        }

        sb.append("}\n");
        return sb.toString();
    }

    private StringBuilder newGetters(final String targetClassName) {
        final StringBuilder sb = new StringBuilder();
        final String getTargetClassName = targetClassName + "Get";
        sb.append("\tpublic static final ");
        sb.append(getTargetClassName);
        sb.append(" GET = new ");
        sb.append(getTargetClassName);
        sb.append("();");
        sb.append("\n\n");
        sb.append("\tpublic static final class ");
        sb.append(getTargetClassName);
        sb.append(" {\n");
        sb.append("\n");
        sb.append("\t\tprivate ");
        sb.append(getTargetClassName);
        //CHECKSTYLE:OFF
        sb.append("() {}\n");
        //CHECKSTYLE:ON
        sb.append("\n");
        boolean found = false;
        for (final IBeanPathElement constant : constants) {
            if (!constant.getBeanPath().contains(BeanPathUtil.BEAN_PATH_SEPARATOR)
                    && constant.getAccessor().hasPublicGetter()) {
                final String getter = BeanPathReflections.PROPERTY_GET_METHOD_PREFIX
                        + BeanPathStrings.capitalize(constant.getBeanPath());
                sb.append(generateConstant(constant, getter, "\t\t"));
                found = true;
            }
        }
        sb.append("\n");
        sb.append("\t}\n");
        if (found) {
            return sb;
        } else {
            return null;
        }
    }

    private StringBuilder newSetters(final String targetClassName) {
        final StringBuilder sb = new StringBuilder();
        final String setTargetClassName = targetClassName + "Set";
        sb.append("\tpublic static final ");
        sb.append(setTargetClassName);
        sb.append(" SET = new ");
        sb.append(setTargetClassName);
        sb.append("();");
        sb.append("\n\n");
        sb.append("\tpublic static final class ");
        sb.append(setTargetClassName);
        sb.append(" {\n");
        sb.append("\n");
        sb.append("\t\tprivate ");
        sb.append(setTargetClassName);
        //CHECKSTYLE:OFF
        sb.append("() {}\n");
        //CHECKSTYLE:ON
        sb.append("\n");
        boolean found = false;
        for (final IBeanPathElement constant : constants) {
            if (!constant.getBeanPath().contains(BeanPathUtil.BEAN_PATH_SEPARATOR)
                    && constant.getAccessor().hasPublicSetter()) {
                final String setter = BeanPathReflections.PROPERTY_SET_METHOD_PREFIX
                        + BeanPathStrings.capitalize(constant.getBeanPath());
                sb.append(generateConstant(constant, setter, "\t\t"));
                found = true;
            }
        }
        sb.append("\n");
        sb.append("\t}\n");
        if (found) {
            return sb;
        } else {
            return null;
        }
    }

    private StringBuilder newFields(final String targetClassName) {
        final StringBuilder sb = new StringBuilder();
        final String fieldTargetClassName = targetClassName + "Field";
        sb.append("\tpublic static final ");
        sb.append(fieldTargetClassName);
        sb.append(" FIELD = new ");
        sb.append(fieldTargetClassName);
        sb.append("();");
        sb.append("\n\n");
        sb.append("\tpublic static final class ");
        sb.append(fieldTargetClassName);
        sb.append(" {\n");
        sb.append("\n");
        sb.append("\t\tprivate ");
        sb.append(fieldTargetClassName);
        //CHECKSTYLE:OFF
        sb.append("() {}\n");
        //CHECKSTYLE:ON
        sb.append("\n");
        boolean found = false;
        for (final IBeanPathElement constant : constants) {
            if (!constant.getBeanPath().contains(BeanPathUtil.BEAN_PATH_SEPARATOR)
                    && constant.getAccessor().hasPublicField()) {
                final String field = constant.getBeanPath();
                sb.append(generateConstant(constant, field, "\t\t"));
                found = true;
            }
        }
        sb.append("\n");
        sb.append("\t}\n");
        if (found) {
            return sb;
        } else {
            return null;
        }
    }

    private StringBuilder newActions(final String targetClassName) {
        final StringBuilder sb = new StringBuilder();
        final String fieldTargetClassName = targetClassName + "Field";
        sb.append("\tpublic static final ");
        sb.append(fieldTargetClassName);
        sb.append(" ACTION = new ");
        sb.append(fieldTargetClassName);
        sb.append("();");
        sb.append("\n\n");
        sb.append("\tpublic static final class ");
        sb.append(fieldTargetClassName);
        sb.append(" {\n");
        sb.append("\n");
        sb.append("\t\tprivate ");
        sb.append(fieldTargetClassName);
        //CHECKSTYLE:OFF
        sb.append("() {}\n");
        //CHECKSTYLE:ON
        sb.append("\n");
        boolean found = false;
        for (final IBeanPathElement constant : constants) {
            if (!constant.getBeanPath().contains(BeanPathUtil.BEAN_PATH_SEPARATOR)
                    && constant.getAccessor().hasPublicAction()) {
                final String action = constant.getBeanPath();
                sb.append(generateConstant(constant, action, "\t\t"));
                found = true;
            }
        }
        sb.append("\n");
        sb.append("\t}\n");
        if (found) {
            return sb;
        } else {
            return null;
        }
    }

}
