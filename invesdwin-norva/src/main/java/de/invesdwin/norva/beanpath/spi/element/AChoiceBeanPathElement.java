package de.invesdwin.norva.beanpath.spi.element;

import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.ChoiceBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.FixedValueBeanPathModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.SelectionBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.utility.ChoiceBeanPathElement;

@NotThreadSafe
public abstract class AChoiceBeanPathElement extends APropertyBeanPathElement {

    private ChoiceBeanPathElement choiceElement;
    private IBeanPathPropertyModifier<List<?>> choiceModifier;
    private IBeanPathPropertyModifier<List<?>> selectionModifier;
    private boolean modifierIsRedirectedChoice;

    public AChoiceBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement) {
        super(simplePropertyElement);
    }

    @Override
    public IBeanPathPropertyModifier<Object> getModifier() {
        if (modifierIsRedirectedChoice) {
            //we have to return empty here, otherwise "all" choices would always be selected in UI, though we do not support selection
            return getChoiceElement().getModifier();
        } else {
            return super.getModifier();
        }
    }

    /**
     * This modifier supports multi-select. For single select the list is only allowed to have 1 item in it.
     */
    public IBeanPathPropertyModifier<List<?>> getSelectionModifier() {
        if (modifierIsRedirectedChoice) {
            //we have to return empty here, otherwise "all" choices would always be selected in UI, though we do not support selection
            return new FixedValueBeanPathModifier<List<?>>(getAccessor(), Collections.emptyList());
        } else {
            if (selectionModifier == null) {
                selectionModifier = new SelectionBeanPathPropertyModifier(getAccessor());
            }
            return selectionModifier;
        }
    }

    public boolean isMultiSelect() {
        return !modifierIsRedirectedChoice && isCollectionModifier();
    }

    public boolean isSingleSelect() {
        return !modifierIsRedirectedChoice && !isCollectionModifier();
    }

    public boolean isChoiceOnly() {
        return modifierIsRedirectedChoice;
    }

    public ChoiceBeanPathElement getChoiceElement() {
        return choiceElement;
    }

    public IBeanPathPropertyModifier<List<?>> getChoiceModifier() {
        if (choiceModifier == null) {
            choiceModifier = new ChoiceBeanPathPropertyModifier(getChoiceElement().getAccessor(),
                    getSelectionModifier());
        }
        return choiceModifier;
    }

    @Override
    protected void beforeFirstAccept() {
        super.beforeFirstAccept();
        this.choiceElement = getContext().getElementRegistry().getChoiceUtilityElementFor(this);
        if (this.choiceElement == null && (getAccessor().getRawType().isEnum() || getAccessor().getRawType().isBoolean()
                || isCollectionModifier())) {
            this.modifierIsRedirectedChoice = isCollectionModifier();
            //redirect to this property as choice for enums
            this.choiceElement = new ChoiceBeanPathElement(getSimplePropertyElement(), false);
        }
        org.assertj.core.api.Assertions.assertThat(choiceElement).as("No choice element found!").isNotNull();
    }

    private boolean isCollectionModifier() {
        return getAccessor().getRawType().isArray() || getAccessor().getRawType().isIterable();
    }

    @Override
    protected boolean shouldAttachToContainerTitleElement() {
        //cannot know which element of the choices should be used for the container title
        if (getChoiceElement() == null) {
            return false;
        } else if (getChoiceElement().getAccessor().getRawType().isArray()) {
            return false;
        } else if (getChoiceElement().getAccessor().getRawType().isIterable()) {
            return false;
        }
        return true;
    }

}
