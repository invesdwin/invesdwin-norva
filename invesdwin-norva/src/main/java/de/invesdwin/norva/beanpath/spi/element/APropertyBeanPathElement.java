package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.utility.ValidateBeanPathElement;

@NotThreadSafe
public abstract class APropertyBeanPathElement extends ABeanPathElement
        implements IPropertyBeanPathElement, IValidatableBeanPathElement {

    private final SimplePropertyBeanPathElement simplePropertyElement;
    private ValidateBeanPathElement validateElement;

    public APropertyBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement) {
        super(simplePropertyElement.getContext(), simplePropertyElement.getContainer(),
                simplePropertyElement.getAccessor());
        this.simplePropertyElement = simplePropertyElement;
    }

    public SimplePropertyBeanPathElement getSimplePropertyElement() {
        return simplePropertyElement;
    }

    @Override
    public ValidateBeanPathElement getValidateElement() {
        return validateElement;
    }

    @Override
    public BeanClassType getValidatableType() {
        return getModifier().getAccessor().getRawType();
    }

    @Override
    public boolean isProperty() {
        return getSimplePropertyElement().isProperty();
    }

    @Override
    public boolean isAction() {
        return getSimplePropertyElement().isAction();
    }

    @Override
    public IBeanPathPropertyModifier<Object> getModifier() {
        return getSimplePropertyElement().getModifier();
    }

    @Override
    public boolean isModifierAvailable() {
        return getSimplePropertyElement().isModifierAvailable();
    }

    @Override
    protected boolean isStaticallyDisabled() {
        return !getAccessor().hasPublicSetterOrField();
    }

    @Override
    protected void beforeFirstAccept() {
        super.beforeFirstAccept();
        if (shouldBeAddedToElementRegistry()) {
            //skip ContainerOpenElement for tables
            this.validateElement = getContext().getElementRegistry().getValidateUtilityElementFor(this);
        }
    }

}
