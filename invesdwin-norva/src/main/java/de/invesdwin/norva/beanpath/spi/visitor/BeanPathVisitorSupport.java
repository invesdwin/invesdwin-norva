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
public class BeanPathVisitorSupport implements IBeanPathVisitor {

    @Override
    public void visitRoot(final RootBeanPathElement e) {
    }

    @Override
    public void visitContainerOpen(final ContainerOpenBeanPathElement e) {
    }

    @Override
    public void visitTextField(final TextFieldBeanPathElement e) {
    }

    @Override
    public void visitCheckBox(final CheckBoxBeanPathElement e) {
    }

    @Override
    public void visitComboBox(final ComboBoxBeanPathElement e) {
    }

    @Override
    public void visitTable(final TableBeanPathElement e) {
    }

    @Override
    public void visitTabbed(final TabbedBeanPathElement e) {
    }

    @Override
    public void visitButton(final ButtonBeanPathElement e) {
    }

    @Override
    public void visitUploadButton(final UploadButtonBeanPathElement e) {
    }

    @Override
    public void visitHidden(final HiddenBeanPathElement e) {
    }

    @Override
    public void visitContainerClose() {
    }

    @Override
    public void visitInvalidAction(final SimpleActionBeanPathElement e) {
    }

    @Override
    public void visitInvalidProperty(final SimplePropertyBeanPathElement e) {
    }

    @Override
    public void finish() {
    }

}
