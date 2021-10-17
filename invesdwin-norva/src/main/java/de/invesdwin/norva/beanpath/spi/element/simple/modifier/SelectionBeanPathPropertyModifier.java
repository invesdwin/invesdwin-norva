package de.invesdwin.norva.beanpath.spi.element.simple.modifier;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;

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

    public boolean isSelectedFromRoot(final Object root, final Object value) {
        return getValueFromRoot(root).contains(value);
    }

    public boolean isSelectedFromTarget(final Object target, final Object value) {
        return getValueFromTarget(target).contains(value);
    }

    public boolean isSingleSelection() {
        return singleSelection;
    }

    public boolean isMultiSelection() {
        return !singleSelection;
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

    public boolean toggleSelectionFromRoot(final Object root, final Object value) {
        if (isSelectedFromRoot(root, value)) {
            unselectFromRoot(root, value);
            return false;
        } else {
            selectFromRoot(root, value);
            return true;
        }
    }

    public boolean toggleSelectionFromTarget(final Object target, final Object value) {
        if (isSelectedFromTarget(target, value)) {
            unselectFromTarget(target, value);
            return false;
        } else {
            selectFromTarget(target, value);
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

    public void selectFromTarget(final Object target, final Object value) {
        final List<Object> selection = (List<Object>) getValueFromTarget(target);
        if (singleSelection) {
            selection.clear();
        } else {
            selection.remove(null); //null selection not supported
        }
        if (!selection.add(value)) {
            throw new IllegalArgumentException("true expected");
        }
        setValueFromTarget(target, selection);
    }

    public void selectFromRoot(final Object root, final Object value) {
        final List<Object> selection = (List<Object>) getValueFromRoot(root);
        if (singleSelection) {
            selection.clear();
        } else {
            selection.remove(null); //null selection not supported
        }
        if (!selection.add(value)) {
            throw new IllegalArgumentException("true expected");
        }
        setValueFromRoot(root, selection);
    }

    public void unselect(final Object value) {
        final List<Object> selection = (List<Object>) getValue();
        selection.remove(null); //null selection not supported
        if (!selection.remove(value)) {
            throw new IllegalArgumentException("true expected");
        }
        setValue(selection);
    }

    public void unselectFromTarget(final Object target, final Object value) {
        final List<Object> selection = (List<Object>) getValueFromTarget(target);
        selection.remove(null); //null selection not supported
        if (!selection.remove(value)) {
            throw new IllegalArgumentException("true expected");
        }
        setValueFromTarget(target, selection);
    }

    public void unselectFromRoot(final Object root, final Object value) {
        final List<Object> selection = (List<Object>) getValueFromRoot(root);
        selection.remove(null); //null selection not supported
        if (!selection.remove(value)) {
            throw new IllegalArgumentException("true expected");
        }
        setValueFromRoot(root, selection);
    }

    public IBeanPathActionInvoker getToggleSelectionInvoker() {
        return new IBeanPathActionInvoker() {

            @Override
            public Object invokeFromTarget(final Object... params) {
                BeanPathAssertions.checkArgument(params.length == 2);
                toggleSelectionFromTarget(params[0], params[1]);
                //no page redirect here
                return null;
            }

            @Override
            public Object invokeFromTarget(final Object target) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invokeFromTarget(final Object target, final Object param1) {
                toggleSelectionFromTarget(target, param1);
                return null;
            }

            @Override
            public Object invokeFromTarget(final Object target, final Object param1, final Object param2) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invokeFromTarget(final Object target, final Object param1, final Object param2,
                    final Object param3) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invokeFromRoot(final Object... params) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invokeFromRoot(final Object root) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invokeFromRoot(final Object root, final Object param1) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invokeFromRoot(final Object root, final Object param1, final Object param2) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invokeFromRoot(final Object root, final Object param1, final Object param2,
                    final Object param3) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invoke(final Object... params) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invoke() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invoke(final Object param1) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invoke(final Object param1, final Object param2) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object invoke(final Object param1, final Object param2, final Object param3) {
                throw new UnsupportedOperationException();
            }

            @Override
            public IBeanPathAccessor getAccessor() {
                return SelectionBeanPathPropertyModifier.this.getAccessor();
            }

            @Override
            public IBeanClassAccessor getBeanClassAccessor() {
                return SelectionBeanPathPropertyModifier.this.getBeanClassAccessor();
            }

            @Override
            public IBeanObjectAccessor getBeanObjectAccessor() {
                return SelectionBeanPathPropertyModifier.this.getBeanObjectAccessor();
            }
        };
    }

}
