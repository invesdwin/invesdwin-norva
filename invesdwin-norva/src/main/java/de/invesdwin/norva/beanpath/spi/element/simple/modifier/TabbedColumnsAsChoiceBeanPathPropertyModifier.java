package de.invesdwin.norva.beanpath.spi.element.simple.modifier;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContext;
import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.BeanObjectContext;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.TabbedBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.TabbedColumnBeanPathElement;

@NotThreadSafe
public class TabbedColumnsAsChoiceBeanPathPropertyModifier implements IBeanPathPropertyModifier<List<?>> {

    private final TabbedBeanPathElement element;
    private final IBeanPathAccessor accessor;
    private final IBeanObjectAccessor beanObjectAccessor;
    private final IBeanClassAccessor beanClassAccessor;

    public TabbedColumnsAsChoiceBeanPathPropertyModifier(final TabbedBeanPathElement element) {
        this.element = element;
        this.accessor = element.getAccessor();
        if (accessor instanceof IBeanClassAccessor) {
            this.beanObjectAccessor = null;
            this.beanClassAccessor = (IBeanClassAccessor) accessor;
        } else if (accessor instanceof IBeanObjectAccessor) {
            this.beanObjectAccessor = (IBeanObjectAccessor) accessor;
            this.beanClassAccessor = beanObjectAccessor.getBeanClassAccessor();
        } else {
            this.beanObjectAccessor = null;
            this.beanClassAccessor = null;
        }
    }

    @Override
    public IBeanPathAccessor getAccessor() {
        return accessor;
    }

    @Override
    public IBeanObjectAccessor getBeanObjectAccessor() {
        BeanPathAssertions.checkState(beanObjectAccessor != null, "%s.getObjectAccessor() is only available in a %s",
                IBeanPathPropertyModifier.class.getSimpleName(), BeanObjectContext.class.getSimpleName());
        return beanObjectAccessor;
    }

    @Override
    public IBeanClassAccessor getBeanClassAccessor() {
        BeanPathAssertions.checkState(beanClassAccessor != null, "%s.getClassAccessor() is only available in a %s",
                IBeanPathPropertyModifier.class.getSimpleName(), BeanClassContext.class.getSimpleName());
        return beanClassAccessor;
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
        for (final TabbedColumnBeanPathElement column : element.getColumnsFromRoot(root)) {
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
        for (final TabbedColumnBeanPathElement column : element.getColumnsFromTarget(target)) {
            final BeanClassAccessor accessor = column.getContainer().getAccessor().unwrap(BeanClassAccessor.class);
            final Object columnTarget = accessor.getValueFromTarget(target);
            list.add(column.getModifier().getValueFromTarget(columnTarget));
        }
        return list;
    }

}
