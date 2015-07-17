package de.invesdwin.norva.beanpath.spi.element;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;
import de.invesdwin.norva.beanpath.spi.element.utility.ValidateBeanPathElement;

public interface IValidatableBeanPathElement {

    ValidateBeanPathElement getValidateElement();

    BeanClassType getValidatableType();

}
