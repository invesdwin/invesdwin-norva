package de.invesdwin.norva.beanpath.impl.clazz;

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
public class BeanClassProcessor extends ABeanPathProcessor<BeanClassContext, BeanClassContainer> {

    @SafeVarargs
    public BeanClassProcessor(final BeanClassContext context, final IBeanPathVisitor... visitors) {
        super(context, visitors);
    }

    @Override
    protected void innerScanContainerShallow(final BeanClassContainer container, final ScanResult result) {
        for (final Field field : container.getType().getType().getFields()) {
            result.addPropertyField(new SimplePropertyBeanPathElement(getContext(), container,
                    new BeanClassAccessor(getContext(), container, field)));
        }
        for (final Method method : container.getType().getType().getMethods()) {
            //check for getXyz but ignore get
            if (BeanPathReflections.isPropertyMethodName(method.getName())) {
                result.addPropertyMethod(new SimplePropertyBeanPathElement(getContext(), container,
                        new BeanClassAccessor(getContext(), container, method)));
            } else {
                result.addActionMethod(new SimpleActionBeanPathElement(getContext(), container,
                        new BeanClassAccessor(getContext(), container, method)));
            }

        }
    }

    @Override
    protected BeanClassContainer newSubContainer(final ContainerOpenBeanPathElement containerOpenElement) {
        return new BeanClassContainer(containerOpenElement);
    }

}
