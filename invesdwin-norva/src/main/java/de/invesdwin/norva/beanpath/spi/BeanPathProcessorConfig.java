package de.invesdwin.norva.beanpath.spi;

import javax.annotation.concurrent.Immutable;

@Immutable
public class BeanPathProcessorConfig {

    private static class ReadOnlyBeanPathConfig extends BeanPathProcessorConfig {
        @Override
        public BeanPathProcessorConfig withIgnoreBeanPathEndPointAnnotation(
                final boolean ignoreBeanPathEndPointAnnotation) {
            throw new UnsupportedOperationException();
        }

        @Override
        public BeanPathProcessorConfig withShallowOnly() {
            throw new UnsupportedOperationException();
        }
    }

    public static final BeanPathProcessorConfig DEFAULT = new ReadOnlyBeanPathConfig();
    public static final BeanPathProcessorConfig DEFAULT_SHALLOW = new ReadOnlyBeanPathConfig() {
        {
            shallowOnly = true;
        }
    };

    protected boolean shallowOnly;
    protected boolean ignoreBeanPathEndPointAnnotation;

    public BeanPathProcessorConfig withShallowOnly() {
        this.shallowOnly = true;
        return this;
    }

    public boolean isShallowOnly() {
        return shallowOnly;
    }

    public boolean isIgnoreBeanPathEndPointAnnotation() {
        return ignoreBeanPathEndPointAnnotation;
    }

    public BeanPathProcessorConfig withIgnoreBeanPathEndPointAnnotation(
            final boolean ignoreBeanPathEndPointAnnotation) {
        this.ignoreBeanPathEndPointAnnotation = ignoreBeanPathEndPointAnnotation;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (ignoreBeanPathEndPointAnnotation ? 1231 : 1237);
        result = prime * result + (shallowOnly ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BeanPathProcessorConfig other = (BeanPathProcessorConfig) obj;
        if (ignoreBeanPathEndPointAnnotation != other.ignoreBeanPathEndPointAnnotation) {
            return false;
        }
        if (shallowOnly != other.shallowOnly) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BeanPathProcessorConfig [shallowOnly=" + shallowOnly + ", ignoreBeanPathEndPointAnnotation="
                + ignoreBeanPathEndPointAnnotation + "]";
    }

}
