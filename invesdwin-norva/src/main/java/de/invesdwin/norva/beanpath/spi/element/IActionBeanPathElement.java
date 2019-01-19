package de.invesdwin.norva.beanpath.spi.element;

import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;

public interface IActionBeanPathElement extends IBeanPathElement {

    IBeanPathActionInvoker getInvoker();

    boolean isInvokerAvailable();

    boolean isModalCloser();

    boolean isModalOpener();

}
