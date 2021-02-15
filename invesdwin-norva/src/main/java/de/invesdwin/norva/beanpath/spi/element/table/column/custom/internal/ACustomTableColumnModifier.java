package de.invesdwin.norva.beanpath.spi.element.table.column.custom.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public abstract class ACustomTableColumnModifier<V> implements IBeanPathPropertyModifier<V> {

    @Override
    public IBeanPathAccessor getAccessor() {
        throw newUnsupportedOperationException();
    }

    @Override
    public IBeanClassAccessor getBeanClassAccessor() {
        throw newUnsupportedOperationException();
    }

    @Override
    public IBeanObjectAccessor getBeanObjectAccessor() {
        throw newUnsupportedOperationException();
    }

    @Override
    public void setValue(final V value) {
        throw newUnsupportedOperationException();
    }

    private UnsupportedOperationException newUnsupportedOperationException() {
        return new UnsupportedOperationException(
                "There is no default uniquely defined object for this property: " + getAccessor());
    }

    @Override
    public V getValue() {
        throw newUnsupportedOperationException();
    }

    @Override
    public void setValueFromRoot(final Object root, final Object value) {
        throw newUnsupportedOperationException();
    }

    @Override
    public V getValueFromRoot(final Object root) {
        throw newUnsupportedOperationException();
    }

}
