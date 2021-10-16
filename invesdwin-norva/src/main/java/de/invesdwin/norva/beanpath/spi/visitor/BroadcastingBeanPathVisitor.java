package de.invesdwin.norva.beanpath.spi.visitor;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.ButtonBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.CheckBoxBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.ComboBoxBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.ContainerOpenBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.HiddenBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.RootBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.TabbedBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.TextFieldBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.UploadButtonBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimpleActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.TableBeanPathElement;

@NotThreadSafe
public class BroadcastingBeanPathVisitor implements IBeanPathVisitor {

    private final IBeanPathVisitor[] visitors;

    public BroadcastingBeanPathVisitor(final IBeanPathVisitor[] visitors) {
        this.visitors = visitors;
    }

    @Override
    public void visitRoot(final RootBeanPathElement e) {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitRoot(e);
        }
    }

    @Override
    public void visitContainerOpen(final ContainerOpenBeanPathElement e) {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitContainerOpen(e);
        }
    }

    @Override
    public void visitTextField(final TextFieldBeanPathElement e) {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitTextField(e);
        }
    }

    @Override
    public void visitCheckBox(final CheckBoxBeanPathElement e) {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitCheckBox(e);
        }
    }

    @Override
    public void visitComboBox(final ComboBoxBeanPathElement e) {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitComboBox(e);
        }
    }

    @Override
    public void visitTable(final TableBeanPathElement e) {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitTable(e);
        }
    }

    @Override
    public void visitTabbed(final TabbedBeanPathElement e) {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitTabbed(e);
        }
    }

    @Override
    public void visitButton(final ButtonBeanPathElement e) {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitButton(e);
        }
    }

    @Override
    public void visitUploadButton(final UploadButtonBeanPathElement e) {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitUploadButton(e);
        }
    }

    @Override
    public void visitHidden(final HiddenBeanPathElement e) {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitHidden(e);
        }
    }

    @Override
    public void visitContainerClose() {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitContainerClose();
        }
    }

    @Override
    public void visitInvalidAction(final SimpleActionBeanPathElement e) {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitInvalidAction(e);
        }
    }

    @Override
    public void visitInvalidProperty(final SimplePropertyBeanPathElement e) {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.visitInvalidProperty(e);
        }
    }

    @Override
    public void finish() {
        for (int i = 0; i < visitors.length; i++) {
            final IBeanPathVisitor visitor = visitors[i];
            visitor.finish();
        }
    }

}
