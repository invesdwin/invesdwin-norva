package de.invesdwin.norva.beanpath.spi.visitor;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathStrings;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassProcessor;
import de.invesdwin.norva.beanpath.spi.element.IActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IPropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.RootBeanPathElement;

@NotThreadSafe
public class PrintVisitor extends ASimpleBeanPathVisitor {

    private int indentCount = 1;
    private boolean printInvalid = false;

    public PrintVisitor withPrintInvalid() {
        return withPrintInvalid(true);
    }

    public PrintVisitor withPrintInvalid(final boolean printInvalid) {
        this.printInvalid = printInvalid;
        return this;
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
        print(e, "Other");
    }

    @Override
    public void visitProperty(final IPropertyBeanPathElement e) {
        print(e, "Property");
    }

    @Override
    public void visitAction(final IActionBeanPathElement e) {
        print(e, "Action");
    }

    @Override
    protected void visitInvalid(final IBeanPathElement e) {
        if (printInvalid) {
            print(e, "Invalid");
        }
    }

    @Override
    public void visitSubElementsClose() {
        indentCount--;
    }

    protected void print(final IBeanPathElement e, final String type) {
        //CHECKSTYLE:OFF
        System.out.println(indent() + type + ": " + e.getClass().getSimpleName() + ": " + e.getBeanPath() + " | "
                + e.getTypePath());
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
        BeanClassProcessor.process(clazz, new PrintVisitor());
    }

}
