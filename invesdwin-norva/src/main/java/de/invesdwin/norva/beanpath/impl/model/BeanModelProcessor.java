package de.invesdwin.norva.beanpath.impl.model;

import javax.annotation.concurrent.NotThreadSafe;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.spi.ABeanPathProcessor;
import de.invesdwin.norva.beanpath.spi.element.ContainerOpenBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimpleActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class BeanModelProcessor extends ABeanPathProcessor<BeanModelContext, BeanModelContainer> {

    @SafeVarargs
    public BeanModelProcessor(final BeanModelContext context, final IBeanPathVisitor... visitors) {
        super(context, visitors);
    }

    @Override
    protected void innerScanContainerShallow(final BeanModelContainer container, final ScanResult result) {
        if (container.getType().getTypeElement() != null) {
            for (final Element element : container.getType().getTypeElement().getEnclosedElements()) {
                if (element.getKind() == ElementKind.FIELD) {
                    result.addPropertyField(new SimplePropertyBeanPathElement(getContext(), container,
                            new BeanModelAccessor(getContext(), container, element)));
                } else if (element.getKind() == ElementKind.METHOD) {
                    if (BeanPathReflections.isPropertyMethodName(element.getSimpleName())) {
                        result.addPropertyMethod(new SimplePropertyBeanPathElement(getContext(), container,
                                new BeanModelAccessor(getContext(), container, element)));
                    } else {
                        result.addActionMethod(new SimpleActionBeanPathElement(getContext(), container,
                                new BeanModelAccessor(getContext(), container, element)));
                    }
                }
            }
        }
    }

    @Override
    protected BeanModelContainer newSubContainer(final ContainerOpenBeanPathElement containerOpenElement) {
        return new BeanModelContainer(containerOpenElement);
    }

}
