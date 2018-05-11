package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathObjects;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
@SuppressWarnings("unchecked")
public class IterableBeanPathPropertyModifier implements IBeanPathPropertyModifier<List<?>> {

    private final BeanPathPropertyModifier delegate;
    private final IBeanPathPropertyModifier<List<?>> invalidChoiceModifier;

    public IterableBeanPathPropertyModifier(final IBeanPathAccessor accessor,
            final IBeanPathPropertyModifier<List<?>> invalidChoiceModifier) {
        com.google.common.base.Preconditions.checkArgument(!accessor.getRawType().isArray());
        com.google.common.base.Preconditions.checkArgument(accessor.getRawType().isIterable());
        this.delegate = new BeanPathPropertyModifier(accessor);
        this.invalidChoiceModifier = invalidChoiceModifier;
    }

    @Override
    public IBeanObjectAccessor getAccessor() {
        return delegate.getAccessor();
    }

    @Override
    public void setValue(final List<?> value) {
        final Iterable<Object> oldValue = (Iterable<Object>) delegate.getValue();
        delegate.setValue(toTargetCollection(oldValue, value));
    }

    @Override
    public List<?> getValue() {
        final Iterable<Object> value = (Iterable<Object>) delegate.getValue();
        return toList(value, getInvalidChoices());
    }

    @Override
    public void setValueFromRoot(final Object root, final List<?> value) {
        final Iterable<Object> oldValue = (Iterable<Object>) delegate.getValueFromRoot(root);
        delegate.setValueFromRoot(root, toTargetCollection(oldValue, value));
    }

    @Override
    public List<?> getValueFromRoot(final Object root) {
        final Iterable<Object> value = (Iterable<Object>) delegate.getValueFromRoot(root);
        return toList(value, getInvalidChoicesFromRoot(root));
    }

    @Override
    public void setValueFromTarget(final Object target, final List<?> value) {
        final Iterable<Object> oldValue = (Iterable<Object>) delegate.getValueFromTarget(target);
        delegate.setValueFromTarget(target, toTargetCollection(oldValue, value));
    }

    @Override
    public List<?> getValueFromTarget(final Object target) {
        final Iterable<Object> value = (Iterable<Object>) delegate.getValueFromTarget(target);
        return toList(value, getInvalidChoicesFromTarget(target));
    }

    private List<?> toList(final Iterable<Object> value, final List<?> invalidChoices) {
        if (value == null) {
            if (invalidChoices != null && !invalidChoices.isEmpty()) {
                return new ArrayList<Object>(invalidChoices);
            } else {
                return new ArrayList<Object>();
            }
        }
        //do not reuse exising list, instead create a new copy to allow modification of it
        final List<Object> list = com.google.common.collect.Lists.newArrayList(value);
        if (invalidChoices != null) {
            final List<Object> filtedInvalidChoices = new ArrayList<Object>();
            for (final Object invalidChoice : invalidChoices) {
                if (invalidChoiceModifier != null && !list.contains(invalidChoice)
                        && (invalidChoice != null || invalidChoiceModifier.getAccessor().isNullable())) {
                    filtedInvalidChoices.add(invalidChoice);
                }
            }
            list.addAll(0, filtedInvalidChoices);
        }
        return list;
    }

    private List<?> getInvalidChoicesFromRoot(final Object root) {
        if (invalidChoiceModifier != null) {
            return invalidChoiceModifier.getValueFromRoot(root);
        } else {
            return null;
        }
    }

    private List<?> getInvalidChoices() {
        if (invalidChoiceModifier != null) {
            return invalidChoiceModifier.getValue();
        } else {
            return null;
        }
    }

    private List<?> getInvalidChoicesFromTarget(final Object target) {
        if (invalidChoiceModifier != null) {
            return invalidChoiceModifier.getValueFromTarget(target);
        } else {
            return null;
        }
    }

    private Collection<Object> toTargetCollection(final Iterable<Object> oldValue, final List<?> newValue) {
        Collection<Object> targetCollection;
        if (newValue == null) {
            return null;
        } else if (oldValue instanceof Collection) {
            //reuse existing collection if possible, because instantiating a new collection is not always possible
            try {
                targetCollection = (Collection<Object>) BeanPathObjects.clone(oldValue);
            } catch (final Throwable e) {
                try {
                    targetCollection = (Collection<Object>) oldValue.getClass().newInstance();
                } catch (final Throwable e1) {
                    targetCollection = newTargetCollectionFromType();
                }
            }
        } else {
            targetCollection = newTargetCollectionFromType();
        }
        try {
            targetCollection.clear();
        } catch (final Throwable e) {
            targetCollection = newTargetCollectionFromType();
        }
        try {
            targetCollection.addAll(newValue);
        } catch (final Throwable e) {
            targetCollection = newTargetCollectionFromType();
            targetCollection.addAll(newValue);
        }
        return targetCollection;
    }

    private Collection<Object> newTargetCollectionFromType() {
        try {
            return (Collection<Object>) getCollectionTypeToBeCreated().newInstance();
        } catch (final InstantiationException e) {
            throw new RuntimeException(e);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> getCollectionTypeToBeCreated() {
        final Class<?> type = getAccessor().getRawType().getType();
        if (type.equals(List.class) || type.equals(Collection.class) || type.equals(Iterable.class)) {
            return ArrayList.class;
        } else if (type.equals(Set.class)) {
            return LinkedHashSet.class;
        } else {
            return type;
        }
    }
}
