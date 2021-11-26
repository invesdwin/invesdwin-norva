package de.invesdwin.norva.beanpath.impl.clazz;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.concurrent.NotThreadSafe;

import com.github.benmanes.caffeine.cache.Caffeine;

import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.spi.ABeanPathProcessor;
import de.invesdwin.norva.beanpath.spi.element.ContainerOpenBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimpleActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;
import de.invesdwin.norva.beanpath.spi.visitor.RecordingVisitor;

@NotThreadSafe
public final class BeanClassProcessor extends ABeanPathProcessor<BeanClassContext, BeanClassContainer> {

    private static final ConcurrentMap<BeanClassProcessorConfig, RecordingVisitor> CACHE = Caffeine.newBuilder()
            .maximumSize(100)
            .softValues()
            .<BeanClassProcessorConfig, RecordingVisitor> build()
            .asMap();

    @SafeVarargs
    private BeanClassProcessor(final BeanClassProcessorConfig config, final BeanClassContext context,
            final IBeanPathVisitor... visitors) {
        super(config, context, visitors);
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

    public static BeanClassContext process(final Class<?> type, final IBeanPathVisitor... visitors) {
        return process(BeanClassProcessorConfig.getDefault(type), visitors);
    }

    public static BeanClassContext process(final BeanClassProcessorConfig config, final IBeanPathVisitor... visitors) {
        final RecordingVisitor recording = getRecording(config);
        recording.process(visitors);
        return recording.getContext();
    }

    public static BeanClassContext getContext(final Class<?> type) {
        return getContext(BeanClassProcessorConfig.getDefault(type));
    }

    public static BeanClassContext getContext(final BeanClassProcessorConfig config) {
        final RecordingVisitor recording = getRecording(config);
        return recording.getContext();
    }

    private static RecordingVisitor getRecording(final BeanClassProcessorConfig config) {
        final RecordingVisitor recording = CACHE.computeIfAbsent(config, (c) -> {
            final BeanClassContext context = new BeanClassContext(
                    new BeanClassContainer(new BeanClassType(config.getType()))) {
                @Override
                public boolean getDefaultEager() {
                    return config.isDefaultEager();
                }
            };
            final RecordingVisitor recordingVisitor = new RecordingVisitor(context);
            new BeanClassProcessor(config, context, recordingVisitor).process();
            return recordingVisitor;
        });
        return recording;
    }

}
