package de.invesdwin.norva.beanpath.impl.object;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.spi.ABeanPathProcessor;
import de.invesdwin.norva.beanpath.spi.element.ContainerOpenBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimpleActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class BeanObjectProcessor extends ABeanPathProcessor<BeanObjectContext, BeanObjectContainer> {

    @SafeVarargs
    public BeanObjectProcessor(final BeanObjectContext context, final IBeanPathVisitor... visitors) {
        super(context, visitors);
    }

    @Override
    protected void innerScanContainerShallow(final BeanObjectContainer container, final ScanResult result) {
        for (final Field field : container.getType().getType().getFields()) {
            result.addPropertyField(new SimplePropertyBeanPathElement(getContext(), container,
                    new BeanObjectAccessor(getContext(), container, field)));
        }
        for (final Method method : container.getType().getType().getMethods()) {
            if (BeanPathReflections.isPropertyMethodName(method.getName())) {
                result.addPropertyMethod(new SimplePropertyBeanPathElement(getContext(), container,
                        new BeanObjectAccessor(getContext(), container, method)));
            } else {
                result.addActionMethod(new SimpleActionBeanPathElement(getContext(), container,
                        new BeanObjectAccessor(getContext(), container, method)));
            }

        }
    }

    @Override
    protected BeanObjectContainer newSubContainer(final ContainerOpenBeanPathElement containerOpenElement) {
        return new BeanObjectContainer(containerOpenElement);
    }

}
