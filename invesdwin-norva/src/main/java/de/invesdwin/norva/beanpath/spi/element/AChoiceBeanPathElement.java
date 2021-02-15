package de.invesdwin.norva.beanpath.spi.element;

import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.ChoiceBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.FixedValueBeanPathModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.SelectionBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.table.TableRemoveFromButtonColumnBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.TableSelectionButtonColumnBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.ChoiceBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.ColumnOrderBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.RemoveFromBeanPathElement;

@NotThreadSafe
public abstract class AChoiceBeanPathElement extends APropertyBeanPathElement {

    private ChoiceBeanPathElement choiceElement;
    private IBeanPathPropertyModifier<List<?>> choiceModifier;
    private IBeanPathPropertyModifier<List<?>> selectionModifier;
    private boolean modifierIsRedirectedChoice;
    private ColumnOrderBeanPathElement columnOrderElement;
    private TableRemoveFromButtonColumnBeanPathElement removeFromButtonColumn;
    private TableSelectionButtonColumnBeanPathElement selectionButtonColumn;
    private boolean allowEnum;

    public AChoiceBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement) {
        super(simplePropertyElement);
    }

    /**
     * This modifier supports multi-select. For single select the list is only allowed to have 1 item in it.
     */
    public IBeanPathPropertyModifier<List<?>> getSelectionModifier() {
        if (isChoiceOnly()) {
            //we have to return empty here, otherwise "all" choices would always be selected in UI, though we do not support selection
            return new FixedValueBeanPathModifier<List<?>>(getAccessor(), Collections.emptyList());
        } else {
            if (selectionModifier == null) {
                selectionModifier = new SelectionBeanPathPropertyModifier(getAccessor());
            }
            return selectionModifier;
        }
    }

    public ColumnOrderBeanPathElement getColumnOrderElement() {
        return columnOrderElement;
    }

    public TableRemoveFromButtonColumnBeanPathElement getRemoveFromButtonColumn() {
        return removeFromButtonColumn;
    }

    public TableSelectionButtonColumnBeanPathElement getSelectionButtonColumn() {
        return selectionButtonColumn;
    }

    public boolean isMultiSelection() {
        return !isChoiceOnly() && isCollectionModifier();
    }

    public boolean isSingleSelection() {
        return !isChoiceOnly() && !isCollectionModifier();
    }

    public boolean isChoiceOnly() {
        return modifierIsRedirectedChoice && !isEnumChoice();
    }

    public ChoiceBeanPathElement getChoiceElement() {
        return choiceElement;
    }

    public IBeanPathPropertyModifier<List<?>> getChoiceModifier() {
        if (choiceModifier == null) {
            choiceModifier = new ChoiceBeanPathPropertyModifier(getChoiceElement().getAccessor(),
                    getSelectionModifier(), allowEnum);
        }
        return choiceModifier;
    }

    @Override
    protected void beforeFirstAccept() {
        super.beforeFirstAccept();
        this.choiceElement = getContext().getElementRegistry().getChoiceUtilityElementFor(this);
        if (this.choiceElement == null
                && (isEnumChoice() || getAccessor().getRawType().isBoolean() || isCollectionModifier())) {
            this.modifierIsRedirectedChoice = isCollectionModifier();
            //redirect to this property as choice for enums
            this.choiceElement = new ChoiceBeanPathElement(getSimplePropertyElement(), false);
            this.allowEnum = true;
        }
        verifyChoiceElementFound();

        this.columnOrderElement = getContext().getElementRegistry().getColumnOrderUtilityElementFor(this);

        final RemoveFromBeanPathElement removeFromElement = getContext().getElementRegistry()
                .getRemoveFromUtilityElementFor(this);
        if (removeFromElement != null) {
            this.removeFromButtonColumn = new TableRemoveFromButtonColumnBeanPathElement(removeFromElement);
        }
        if (removeFromButtonColumn != null) {
            if (removeFromButtonColumn.accept()) {
                removeFromButtonColumn.setTableElement(ChoiceAsTableBeanPathElement.maybeWrap(this));
            } else {
                removeFromButtonColumn = null;
            }
        }

        if (isSingleSelection() || isMultiSelection()) {
            selectionButtonColumn = new TableSelectionButtonColumnBeanPathElement(
                    ChoiceAsTableBeanPathElement.maybeWrap(this));
        }
    }

    protected void verifyChoiceElementFound() {
        BeanPathAssertions.checkNotNull(choiceElement, "No choice element found!");
    }

    private boolean isEnumChoice() {
        return getAccessor().getType().isEnum();
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
