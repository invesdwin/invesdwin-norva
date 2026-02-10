package de.invesdwin.norva.beanpath;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassProcessor;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassProcessorConfig;
import de.invesdwin.norva.beanpath.spi.element.IPropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.SimpleBeanPathVisitorSupport;

@NotThreadSafe
public class SettingsBackup {

    private final Map<String, Object> backup = new HashMap<>();

    public Object put(final String key, final Object value) {
        return backup.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        return (T) backup.get(key);
    }

    public void backupPropertiesShallow(final Object obj) {
        BeanClassProcessor.process(BeanClassProcessorConfig.getDefault(obj.getClass()).setShallowOnly(),
                new SimpleBeanPathVisitorSupport() {
                    @Override
                    public void visitProperty(final IPropertyBeanPathElement e) {
                        if (e.getAccessor().hasPublicSetterOrField()) {
                            final Object value = e.getModifier().getValueFromRoot(obj);
                            backup.put(obj.getClass().getSimpleName() + "_" + e.getBeanPath(), value);
                        }
                    }
                });
    }

    public void restorePropertiesShallow(final Object obj) {
        BeanClassProcessor.process(BeanClassProcessorConfig.getDefault(obj.getClass()).setShallowOnly(),
                new SimpleBeanPathVisitorSupport() {
                    @Override
                    public void visitProperty(final IPropertyBeanPathElement e) {
                        if (e.getAccessor().hasPublicSetterOrField()) {
                            final Object value = backup.get(obj.getClass().getSimpleName() + "_" + e.getBeanPath());
                            e.getModifier().setValueFromRoot(obj, value);
                        }
                    }
                });
    }

    public void backupProperties(final Object obj) {
        BeanClassProcessor.process(BeanClassProcessorConfig.getDefault(obj.getClass()),
                new SimpleBeanPathVisitorSupport() {
                    @Override
                    public void visitProperty(final IPropertyBeanPathElement e) {
                        if (e.getAccessor().hasPublicSetterOrField()) {
                            final Object value = e.getModifier().getValueFromRoot(obj);
                            backup.put(obj.getClass().getSimpleName() + "_" + e.getBeanPath(), value);
                        }
                    }
                });
    }

    public void restoreProperties(final Object obj) {
        BeanClassProcessor.process(BeanClassProcessorConfig.getDefault(obj.getClass()),
                new SimpleBeanPathVisitorSupport() {
                    @Override
                    public void visitProperty(final IPropertyBeanPathElement e) {
                        if (e.getAccessor().hasPublicSetterOrField()) {
                            final Object value = backup.get(obj.getClass().getSimpleName() + "_" + e.getBeanPath());
                            e.getModifier().setValueFromRoot(obj, value);
                        }
                    }
                });
    }

    public void clear() {
        backup.clear();
    }

}
