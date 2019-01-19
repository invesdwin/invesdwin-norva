package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.SimpleActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;

@NotThreadSafe
public abstract class AActionBeanPathElement extends ABeanPathElement implements IActionBeanPathElement {

    private final SimpleActionBeanPathElement simpleActionElement;

    public AActionBeanPathElement(final SimpleActionBeanPathElement simpleActionElement) {
        super(simpleActionElement.getContext(), simpleActionElement.getContainer(), simpleActionElement.getAccessor());
        this.simpleActionElement = simpleActionElement;
    }

    @Override
    public IBeanPathActionInvoker getInvoker() {
        return getSimpleActionElement().getInvoker();
    }

    @Override
    public boolean isInvokerAvailable() {
        return getSimpleActionElement().isInvokerAvailable();
    }

    @Override
    public boolean isProperty() {
        return getSimpleActionElement().isProperty();
    }

    @Override
    public boolean isAction() {
        return getSimpleActionElement().isAction();
    }

    public SimpleActionBeanPathElement getSimpleActionElement() {
        return simpleActionElement;
    }

    @Override
    public boolean isModalCloser() {
        return getSimpleActionElement().isModalCloser();
    }

    @Override
    public boolean isModalOpener() {
        return getSimpleActionElement().isModalOpener();
    }

}
