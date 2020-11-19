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

import de.invesdwin.norva.beanpath.BeanPathStrings;
import de.invesdwin.norva.beanpath.impl.model.BeanModelContext;
import de.invesdwin.norva.beanpath.spi.BeanPathUtil;
import de.invesdwin.norva.beanpath.spi.element.IActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IPropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.SimpleBeanPathVisitorSupport;

@NotThreadSafe
public class ConstantsGeneratorVisitor extends SimpleBeanPathVisitorSupport {

    private final List<IBeanPathElement> constants = new ArrayList<>();
    private final List<IBeanPathElement> getters = new ArrayList<>();
    private final List<IBeanPathElement> setters = new ArrayList<>();
    private final List<IBeanPathElement> fields = new ArrayList<>();
    private final List<IBeanPathElement> actions = new ArrayList<>();

    public ConstantsGeneratorVisitor(final BeanModelContext context) {
        super(context);
    }

    @Override
    public BeanModelContext getContext() {
        return (BeanModelContext) super.getContext();
    }

    @Override
    public void visitProperty(final IPropertyBeanPathElement e) {
        add(e);
    }

    private void add(final IBeanPathElement e) {
        constants.add(e);
        if (!e.getBeanPath().contains(BeanPathUtil.BEAN_PATH_SEPARATOR)) {
            if (e.getAccessor().hasPublicGetter()) {
                getters.add(e);
            }
            if (e.getAccessor().hasPublicSetter()) {
                setters.add(e);
            }
            if (e.getAccessor().hasPublicField()) {
                fields.add(e);
            }
            if (e.getAccessor().hasPublicAction()) {
                actions.add(e);
            }
        }
    }

    @Override
    public void visitAction(final IActionBeanPathElement e) {
        add(e);
    }

    @Override
    public void visitOther(final IBeanPathElement e) {
        add(e);
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
        if (!getters.isEmpty()) {
            final StringBuilder gettersStr = newAccessors(targetClassName, "get", true, getters);
            sb.append(gettersStr);
        }

        //setters
        if (!setters.isEmpty()) {
            final StringBuilder settersStr = newAccessors(targetClassName, "set", true, setters);
            sb.append(settersStr);
        }

        //fields
        if (!fields.isEmpty()) {
            final StringBuilder fieldsStr = newAccessors(targetClassName, "field", false, fields);
            sb.append(fieldsStr);
        }

        //actions
        if (!actions.isEmpty()) {
            final StringBuilder actionsStr = newAccessors(targetClassName, "action", false, actions);
            sb.append(actionsStr);
        }

        sb.append("}\n");
        return sb.toString();
    }

    private StringBuilder newAccessors(final String targetClassName, final String type, final boolean isGetterOrSetter,
            final List<IBeanPathElement> elements) {
        final StringBuilder sb = new StringBuilder();
        final String typedTargetClassName = targetClassName + BeanPathStrings.capitalize(type);
        sb.append("\tpublic static final ");
        sb.append(typedTargetClassName);
        sb.append(" ");
        sb.append(type.toUpperCase());
        sb.append(" = new ");
        sb.append(typedTargetClassName);
        sb.append("();");
        sb.append("\n\n");
        sb.append("\tpublic static final class ");
        sb.append(typedTargetClassName);
        sb.append(" {\n");
        sb.append("\n");
        sb.append("\t\tprivate ");
        sb.append(typedTargetClassName);
        //CHECKSTYLE:OFF
        sb.append("() {}\n");
        //CHECKSTYLE:ON
        sb.append("\n");
        for (final IBeanPathElement constant : elements) {
            final String value;
            if (isGetterOrSetter) {
                value = type + BeanPathStrings.capitalize(constant.getBeanPath());
            } else {
                value = constant.getBeanPath();
            }
            sb.append(generateConstant(constant, value, "\t\t"));
        }
        sb.append("\n");
        sb.append("\t}\n");
        return sb;
    }

}
