package de.invesdwin.norva.beanpath.spi.visitor;

import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;
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

public interface IBeanPathVisitor {

    ABeanPathContext getContext();

    void visitRoot(RootBeanPathElement e);

    void visitContainerOpen(ContainerOpenBeanPathElement e);

    void visitTextField(TextFieldBeanPathElement e);

    void visitCheckBox(CheckBoxBeanPathElement e);

    void visitComboBox(ComboBoxBeanPathElement e);

    void visitTable(TableBeanPathElement e);

    void visitTabbed(TabbedBeanPathElement e);

    void visitButton(ButtonBeanPathElement e);

    void visitUploadButton(UploadButtonBeanPathElement e);

    void visitHidden(HiddenBeanPathElement e);

    void visitContainerClose();

    void visitInvalidAction(SimpleActionBeanPathElement e);

    void visitInvalidProperty(SimplePropertyBeanPathElement e);

    void finish();

}
