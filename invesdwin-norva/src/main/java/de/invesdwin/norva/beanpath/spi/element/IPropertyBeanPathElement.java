package de.invesdwin.norva.beanpath.spi.element;

import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

public interface IPropertyBeanPathElement extends IBeanPathElement {

    IBeanPathPropertyModifier<Object> getModifier();

    boolean isModifierAvailable();

    String getFormatString();

}
