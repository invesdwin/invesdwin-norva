package de.invesdwin.norva.beanpath.spi.element.utility;

import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;

public interface IUtilityBeanPathElement {

    void setAttachedToElement(final IBeanPathElement attachedToElement);

    boolean isAttachedToElement();

}
