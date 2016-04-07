package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.element.TabbedBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.TabbedColumnBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class TabbedColumnsAsChoiceBeanPathPropertyModifier implements IBeanPathPropertyModifier<List<?>> {

    private final TabbedBeanPathElement element;

    public TabbedColumnsAsChoiceBeanPathPropertyModifier(final TabbedBeanPathElement element) {
        this.element = element;
    }

    @Override
    public IBeanObjectAccessor getAccessor() {
        return (IBeanObjectAccessor) element.getAccessor();
    }

    @Override
    public void setValue(final List<?> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<?> getValue() {
        final List<Object> list = new ArrayList<Object>();
        for (final TabbedColumnBeanPathElement column : element.getColumns()) {
            list.add(column.getModifier().getValue());
        }
        return list;
    }

    @Override
    public void setValueFromRoot(final Object root, final List<?> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<?> getValueFromRoot(final Object root) {
        final List<Object> list = new ArrayList<Object>();
        for (final TabbedColumnBeanPathElement column : element.getColumns()) {
            list.add(column.getModifier().getValueFromRoot(root));
        }
        return list;
    }

    @Override
    public void setValueFromTarget(final Object target, final List<?> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<?> getValueFromTarget(final Object target) {
        final List<Object> list = new ArrayList<Object>();
        for (final TabbedColumnBeanPathElement column : element.getColumns()) {
            list.add(column.getModifier().getValueFromTarget(target));
        }
        return list;
    }

}
