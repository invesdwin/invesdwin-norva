package de.invesdwin.norva.apt.constants.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import javax.annotation.concurrent.NotThreadSafe;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import de.invesdwin.norva.beanpath.impl.model.BeanModelContext;
import de.invesdwin.norva.beanpath.spi.BeanPathUtil;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.IActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IPropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimpleActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.SimpleBeanPathVisitorSupport;

@NotThreadSafe
public class ConstantsGeneratorVisitor extends SimpleBeanPathVisitorSupport {

    private final BeanModelContext context;
    private final Element originatingElement;
    private final List<IBeanPathElement> constants = new ArrayList<>();
    private final List<IBeanPathElement> getters = new ArrayList<>();
    private final List<IBeanPathElement> setters = new ArrayList<>();
    private final List<IBeanPathElement> fields = new ArrayList<>();
    private final List<IBeanPathElement> actions = new ArrayList<>();
    private final List<IBeanPathElement> methods = new ArrayList<>();

    public ConstantsGeneratorVisitor(final BeanModelContext context, final Element originatingElement) {
        this.context = context;
        this.originatingElement = originatingElement;
    }

    @Override
    public void visitProperty(final IPropertyBeanPathElement e) {
        add(e);
    }

    private void add(final IBeanPathElement e) {
        constants.add(e);
        addAccessor(e);
    }

    @Override
    public void visitInvalidProperty(final SimplePropertyBeanPathElement e) {
        addMethod(e);
    }

    private void addMethod(final IBeanPathElement e) {
        if (!e.getBeanPath().contains(BeanPathUtil.BEAN_PATH_SEPARATOR)) {
            methods.add(e);
        }
    }

    @Override
    public void visitInvalidAction(final SimpleActionBeanPathElement e) {
        addMethod(e);
    }

    private void addAccessor(final IBeanPathElement e) {
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

    private CharSequence generateConstant(final IBeanPathElement e, final String key, final String value,
            final String indent) {
        final StringBuilder sb = new StringBuilder();
        sb.append(indent);
        sb.append("/** ");
        sb.append(e.getClass().getSimpleName().toString());
        sb.append(": ");
        sb.append(org.apache.commons.text.StringEscapeUtils.escapeHtml4(e.getTypePath()));
        sb.append(" */\n");
        sb.append(indent);
        sb.append("public static final String ");
        sb.append(key);
        sb.append(" = \"");
        sb.append(value);
        sb.append("\";\n");
        return sb;
    }

    @Override
    public void finish() {
        final Collection<IBeanPathElement> elements = context.getElementRegistry().getElements();
        if (elements.isEmpty()) {
            return;
        }

        final TypeElement rootTypeElement = context.getRootContainer().getType().getTypeElement();
        final String originatingClassName = String.valueOf(rootTypeElement.getSimpleName());
        final String targetClassName = originatingClassName + "Constants";
        final String packageName = String.valueOf(rootTypeElement.getQualifiedName())
                .replace("." + originatingClassName, "");
        final String content = generateContent(elements, targetClassName, packageName);
        final FileObject fileObject;
        try {
            fileObject = context.getEnv()
                    .getFiler()
                    .createResource(StandardLocation.SOURCE_OUTPUT, packageName, targetClassName + ".java",
                            originatingElement);
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

    protected String generateContent(final Collection<IBeanPathElement> elements, final String targetClassName,
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
            final String key = constant.getBeanPath().replace(BeanPathUtil.BEAN_PATH_SEPARATOR, "_");
            final String value = constant.getBeanPath();
            sb.append(generateConstant(constant, key, value, "\t"));
        }
        sb.append("\n");

        //getters
        if (!getters.isEmpty()) {
            final Function<IBeanPathElement, String> nameF = t -> t.getAccessor().getPublicGetterName();
            final StringBuilder gettersStr = newAccessors("get", true, getters, nameF, nameF);
            sb.append(gettersStr);
        }

        //setters
        if (!setters.isEmpty()) {
            final Function<IBeanPathElement, String> nameF = t -> t.getAccessor().getPublicSetterName();
            final StringBuilder settersStr = newAccessors("set", true, setters, nameF, nameF);
            sb.append(settersStr);
        }

        //fields
        if (!fields.isEmpty()) {
            final Function<IBeanPathElement, String> nameF = t -> t.getAccessor().getPublicFieldName();
            final StringBuilder fieldsStr = newAccessors("field", false, fields, nameF, nameF);
            sb.append(fieldsStr);
        }

        //actions
        if (!actions.isEmpty()) {
            final Function<IBeanPathElement, String> nameF = t -> t.getAccessor().getPublicActionName();
            final StringBuilder actionsStr = newAccessors("action", false, actions, nameF, nameF);
            sb.append(actionsStr);
        }

        //invalid actions
        if (!methods.isEmpty()) {
            final Function<IBeanPathElement, String> nameF = t -> {
                final IBeanPathAccessor a = t.getAccessor();
                if (a.hasPublicAction()) {
                    return a.getPublicActionName();
                } else if (a.hasPublicGetter()) {
                    return a.getPublicGetterName();
                } else if (a.hasPublicSetter()) {
                    return a.getPublicSetterName();
                } else if (a.hasPublicField()) {
                    return a.getPublicFieldName();
                } else {
                    return a.getRawName();
                }
            };
            final StringBuilder actionsStr = newAccessors("method", false, methods, nameF, nameF);
            sb.append(actionsStr);
        }

        sb.append("}\n");
        return sb.toString();
    }

    private StringBuilder newAccessors(final String type, final boolean isGetterOrSetter,
            final List<IBeanPathElement> elements, final Function<IBeanPathElement, String> keyF,
            final Function<IBeanPathElement, String> valueF) {
        final StringBuilder sb = new StringBuilder();
        final String typedClassName = type.toUpperCase();
        sb.append("\tpublic static final class ");
        sb.append(typedClassName);
        sb.append(" {\n");
        sb.append("\n");
        sb.append("\t\tprivate ");
        sb.append(typedClassName);
        //CHECKSTYLE:OFF
        sb.append("() {}\n");
        //CHECKSTYLE:ON
        sb.append("\n");
        for (final IBeanPathElement constant : elements) {
            final String key = keyF.apply(constant);
            final String value = valueF.apply(constant);
            sb.append(generateConstant(constant, key, value, "\t\t"));
        }
        sb.append("\n");
        sb.append("\t}\n");
        return sb;
    }

}
