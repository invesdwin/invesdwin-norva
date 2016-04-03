package de.invesdwin.norva.beanpath.spi.element.simple.modifier;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.ArrayBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.BeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.DelegateBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.IterableBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.ListWrapperBeanPathPropertyModifier;

@NotThreadSafe
public class SelectionBeanPathPropertyModifier extends DelegateBeanPathPropertyModifier<List<?>> {

    private final boolean singleSelection;

    public SelectionBeanPathPropertyModifier(final IBeanPathAccessor accessor) {
        super(createDelegate(accessor));
        this.singleSelection = !accessor.getRawType().isArray() && !accessor.getRawType().isIterable();
    }

    private static IBeanPathPropertyModifier<List<?>> createDelegate(final IBeanPathAccessor accessor) {
        if (accessor.getRawType().isArray()) {
            return new ArrayBeanPathPropertyModifier(accessor, null);
        } else if (accessor.getRawType().isIterable()) {
            return new IterableBeanPathPropertyModifier(accessor, null);
        } else {
            return new ListWrapperBeanPathPropertyModifier(new BeanPathPropertyModifier(accessor));
        }
    }

    public boolean isSelected(final Object value) {
        return getValue().contains(value);
    }

    /**
     * Returns the new selection state.
     */
    public boolean toggleSelection(final Object value) {
        if (isSelected(value)) {
            unselect(value);
            return false;
        } else {
            select(value);
            return true;
        }
    }

    public void select(final Object value) {
        final List<Object> selection = (List<Object>) getValue();
        if (singleSelection) {
            selection.clear();
        } else {
            selection.remove(null); //null selection not supported
        }
        if (!selection.add(value)) {
            throw new IllegalArgumentException("true expected");
        }
        setValue(selection);
    }

    public void unselect(final Object value) {
        final List<Object> selection = (List<Object>) getValue();
        selection.remove(null); //null selection not supported
        if (!selection.remove(value)) {
            throw new IllegalArgumentException("true expected");
        }
        setValue(selection);
    }

    public IBeanPathActionInvoker getToggleSelectionInvoker() {
        return new IBeanPathActionInvoker() {

            @Override
            public Object invokeFromTarget(final Object target, final Object... params) {
                final Object value = target;
                toggleSelection(value);
                //no page redirect here
                return null;
            }

            @Override
            public Object invokeFromRoot(final Object root, final Object... params) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invoke(final Object... params) {
                throw new UnsupportedOperationException();
            }

            @Override
            public IBeanObjectAccessor getAccessor() {
                return SelectionBeanPathPropertyModifier.this.getAccessor();
            }
        };
    }

}
