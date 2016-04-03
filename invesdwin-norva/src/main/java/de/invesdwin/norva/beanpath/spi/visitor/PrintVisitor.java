package de.invesdwin.norva.beanpath.spi.visitor;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathStrings;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContainer;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContext;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassProcessor;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;
import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;
import de.invesdwin.norva.beanpath.spi.element.IActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IPropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.RootBeanPathElement;

@NotThreadSafe
public class PrintVisitor extends ASimpleBeanPathVisitor {

    private int indentCount = 1;

    public PrintVisitor(final ABeanPathContext context) {
        super(context);
    }

    @Override
    public void visitRoot(final RootBeanPathElement e) {
        //CHECKSTYLE:OFF
        System.out.println("Processing " + e.getContainer().getType().getQualifiedName());
        //CHECKSTYLE:ON
        super.visitRoot(e);
    }

    @Override
    public void visitSubElementsOpen() {
        indentCount++;
    }

    @Override
    public void visitOther(final IBeanPathElement e) {
        print(e);
    }

    @Override
    public void visitProperty(final IPropertyBeanPathElement e) {
        print(e);
    }

    @Override
    public void visitAction(final IActionBeanPathElement e) {
        print(e);
    }

    @Override
    public void visitSubElementsClose() {
        indentCount--;
    }

    private void print(final IBeanPathElement e) {
        //CHECKSTYLE:OFF
        System.out.println(indent() + e.getClass().getSimpleName() + ": " + e.getBeanPath() + " | " + e.getTypePath());
        //CHECKSTYLE:ON
    }

    @Override
    public void finish() {
        //CHECKSTYLE:OFF
        System.out.println(indent() + "finish");
        //CHECKSTYLE:ON
    }

    private String indent() {
        return BeanPathStrings.repeat(" ", indentCount * 2);
    }

    public static void print(final Class<?> clazz) {
        final BeanClassContext context = new BeanClassContext(new BeanClassContainer(new BeanClassType(clazz)));
        new BeanClassProcessor(context, new PrintVisitor(context)).process();
    }

}
