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

import org.apache.commons.lang3.StringEscapeUtils;

import de.invesdwin.norva.beanpath.impl.model.BeanModelContext;
import de.invesdwin.norva.beanpath.spi.PathUtil;
import de.invesdwin.norva.beanpath.spi.element.AActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.APropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.SimpleBeanPathVisitorSupport;

@NotThreadSafe
public class ConstantsGeneratorVisitor extends SimpleBeanPathVisitorSupport {

    private final List<StringBuilder> constants = new ArrayList<StringBuilder>();

    public ConstantsGeneratorVisitor(final BeanModelContext context) {
        super(context);
    }

    @Override
    public BeanModelContext getContext() {
        return (BeanModelContext) super.getContext();
    }

    @Override
    public void visitProperty(final APropertyBeanPathElement e) {
        generateConstant(e);
    }

    @Override
    public void visitAction(final AActionBeanPathElement e) {
        generateConstant(e);
    }

    @Override
    public void visitOther(final IBeanPathElement e) {
        generateConstant(e);
    }

    private void generateConstant(final IBeanPathElement e) {
        final StringBuilder sb = new StringBuilder();
        final String beanPath = e.getBeanPath();
        sb.append("\t/** ");
        sb.append(e.getClass().getSimpleName().toString());
        sb.append(": ");
        sb.append(StringEscapeUtils.escapeHtml4(e.getTypePath()));
        sb.append(" */\n");
        sb.append("\tpublic static final String ");
        sb.append(beanPath.replace(PathUtil.BEAN_PATH_SEPARATOR, "_"));
        sb.append(" = \"");
        sb.append(beanPath);
        sb.append("\";\n");
        constants.add(sb);
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
        final String packageName = String.valueOf(rootTypeElement.getQualifiedName()).replace(
                "." + originatingClassName, "");
        final String content = generateContent(elements, targetClassName, packageName);
        FileObject fileObject;
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
        for (final StringBuilder constant : constants) {
            sb.append(constant);
        }
        sb.append("\n");
        sb.append("}\n");
        return sb.toString();
    }

}
