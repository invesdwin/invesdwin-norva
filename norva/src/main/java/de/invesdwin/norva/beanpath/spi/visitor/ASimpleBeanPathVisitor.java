package de.invesdwin.norva.beanpath.spi.visitor;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;
import de.invesdwin.norva.beanpath.spi.element.AActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.APropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.ButtonBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.CheckBoxBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.ComboBoxBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.ContainerOpenBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.HiddenBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.ITableColumnBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IValidatableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.RootBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.TabbedBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.TabbedColumnBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.TableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.TextFieldBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.UploadButtonBeanPathElement;

@NotThreadSafe
public abstract class ASimpleBeanPathVisitor extends ABeanPathVisitor {

    private boolean ignoreNextContainerClose;

    public ASimpleBeanPathVisitor(final ABeanPathContext context) {
        super(context);
    }

    @Override
    public void visitRoot(final RootBeanPathElement e) {
        visitBaseUtilityElements(e);
    }

    @Override
    public void visitTextField(final TextFieldBeanPathElement e) {
        visitProperty(e);
        visitSubElementsOpen();
        visitBaseUtilityElements(e);
        visitSubElementsClose();
    }

    @Override
    public void visitHidden(final HiddenBeanPathElement e) {
        visitOther(e);
        visitSubElementsOpen();
        visitBaseUtilityElements(e);
        visitSubElementsClose();
    }

    private void visitBaseUtilityElements(final IBeanPathElement e) {
        if (e.getInterceptedElement() != null) {
            visitOther(e.getInterceptedElement());
        }
        if (e.getDisableElement() != null) {
            visitAction(e.getDisableElement());
        }
        if (e.getHideElement() != null) {
            visitAction(e.getHideElement());
        }
        if (e.getTitleElement() != null) {
            visitAction(e.getTitleElement());
        }
        if (e.getContainerTitleElement() != null) {
            visitAction(e.getContainerTitleElement());
        }
        if (e.getTooltipElement() != null) {
            visitAction(e.getTooltipElement());
        }
        if (e instanceof IValidatableBeanPathElement) {
            final IValidatableBeanPathElement cE = (IValidatableBeanPathElement) e;
            if (cE.getValidateElement() != null) {
                visitAction(cE.getValidateElement());
            }
        }
    }

    @Override
    public void visitCheckBox(final CheckBoxBeanPathElement e) {
        visitProperty(e);
        visitSubElementsOpen();
        visitBaseUtilityElements(e);
        visitSubElementsClose();
    }

    @Override
    public void visitComboBox(final ComboBoxBeanPathElement e) {
        visitProperty(e);
        visitSubElementsOpen();
        visitBaseUtilityElements(e);
        if (e.getChoiceElement() != null && e.getChoiceElement().shouldBeAddedToElementRegistry()) {
            visitProperty(e.getChoiceElement());
        }
        visitSubElementsClose();
    }

    @Override
    public void visitTable(final TableBeanPathElement e) {
        visitProperty(e);
        visitSubElementsOpen();
        visitBaseUtilityElements(e);
        if (e.getChoiceElement() != null && e.getChoiceElement().shouldBeAddedToElementRegistry()) {
            visitProperty(e.getChoiceElement());
        }
        if (e.getColumnOrderElement() != null) {
            visitAction(e.getColumnOrderElement());
        }
        for (final ITableColumnBeanPathElement column : e.getRawColumns()) {
            if (column instanceof APropertyBeanPathElement) {
                visitProperty((APropertyBeanPathElement) column);
            } else if (column instanceof AActionBeanPathElement) {
                visitAction((AActionBeanPathElement) column);
            } else {
                throw new IllegalArgumentException("Unknown " + ITableColumnBeanPathElement.class.getSimpleName()
                        + ": " + column);
            }
            visitSubElementsOpen();
            visitBaseUtilityElements(column);
            visitSubElementsClose();
        }
        visitSubElementsClose();
    }

    @Override
    public void visitTabbed(final TabbedBeanPathElement e) {
        visitProperty(e);
        visitSubElementsOpen();
        visitBaseUtilityElements(e);
        if (e.getColumnOrderElement() != null) {
            visitAction(e.getColumnOrderElement());
        }
        for (final TabbedColumnBeanPathElement column : e.getRawColumns()) {
            visitProperty(column);
            visitSubElementsOpen();
            visitBaseUtilityElements(column);
            visitSubElementsClose();
        }
        visitSubElementsClose();
    }

    @Override
    public void visitButton(final ButtonBeanPathElement e) {
        visitAction(e);
        visitSubElementsOpen();
        visitBaseUtilityElements(e);
        visitSubElementsClose();
    }

    @Override
    public void visitUploadButton(final UploadButtonBeanPathElement e) {
        visitAction(e);
        visitSubElementsOpen();
        visitBaseUtilityElements(e);
        visitSubElementsClose();
    }

    @Override
    public void visitContainerOpen(final ContainerOpenBeanPathElement e) {
        if (e.shouldBeAddedToElementRegistry()) {
            visitProperty(e);
            visitSubElementsOpen();
            visitBaseUtilityElements(e);

        } else {
            ignoreNextContainerClose = true;
        }
    }

    @Override
    public void visitContainerClose() {
        if (!ignoreNextContainerClose) {
            visitSubElementsClose();
        } else {
            ignoreNextContainerClose = false;
        }
    }

    public abstract void visitOther(IBeanPathElement e);

    public abstract void visitProperty(APropertyBeanPathElement e);

    public abstract void visitAction(AActionBeanPathElement e);

    protected abstract void visitSubElementsOpen();

    protected abstract void visitSubElementsClose();

}
