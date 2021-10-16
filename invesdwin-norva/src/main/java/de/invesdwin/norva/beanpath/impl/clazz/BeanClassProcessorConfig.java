package de.invesdwin.norva.beanpath.impl.clazz;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.concurrent.Immutable;

import de.invesdwin.norva.beanpath.spi.BeanPathProcessorConfig;

@Immutable
public class BeanClassProcessorConfig extends BeanPathProcessorConfig {

    private static final ConcurrentMap<Class<?>, BeanClassProcessorConfig> DEFAULT = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class<?>, BeanClassProcessorConfig> DEFAULT_SHALLOW = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class<?>, BeanClassProcessorConfig> DEFAULT_EAGER = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class<?>, BeanClassProcessorConfig> DEFAULT_IGNOREBEANPATHENDPOINTANNOTATION = new ConcurrentHashMap<>();

    private final Class<?> type;
    private boolean defaultEager;

    public BeanClassProcessorConfig(final Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isDefaultEager() {
        return defaultEager;
    }

    public BeanClassProcessorConfig withDefaultEager(final boolean defaultEager) {
        this.defaultEager = defaultEager;
        return this;
    }

    @Override
    public BeanClassProcessorConfig withIgnoreBeanPathEndPointAnnotation(
            final boolean ignoreBeanPathEndPointAnnotation) {
        return (BeanClassProcessorConfig) super.withIgnoreBeanPathEndPointAnnotation(ignoreBeanPathEndPointAnnotation);
    }

    @Override
    public BeanClassProcessorConfig withShallowOnly() {
        return (BeanClassProcessorConfig) super.withShallowOnly();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (defaultEager ? 1231 : 1237);
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BeanClassProcessorConfig other = (BeanClassProcessorConfig) obj;
        if (defaultEager != other.defaultEager) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BeanClassProcessorConfig [type=" + type + ", shallowOnly=" + isShallowOnly()
                + ", ignoreBeanPathEndPointAnnotation=" + isIgnoreBeanPathEndPointAnnotation() + ", defaultEager="
                + defaultEager + "]";
    }

    public static BeanClassProcessorConfig getDefault(final Class<?> type) {
        return DEFAULT.computeIfAbsent(type, (c) -> new BeanClassProcessorConfig(c));
    }

    public static BeanClassProcessorConfig getDefaultShallow(final Class<?> type) {
        return DEFAULT_SHALLOW.computeIfAbsent(type, (c) -> new BeanClassProcessorConfig(c).withShallowOnly());
    }

    public static BeanClassProcessorConfig getDefaultEager(final Class<?> type) {
        return DEFAULT_EAGER.computeIfAbsent(type, (c) -> new BeanClassProcessorConfig(c).withDefaultEager(true));
    }

    public static BeanClassProcessorConfig getDefaultIgnoreBeanPathEndpointAnnotation(final Class<?> type) {
        return DEFAULT_IGNOREBEANPATHENDPOINTANNOTATION.computeIfAbsent(type,
                (c) -> new BeanClassProcessorConfig(c).withIgnoreBeanPathEndPointAnnotation(true));
    }

}
