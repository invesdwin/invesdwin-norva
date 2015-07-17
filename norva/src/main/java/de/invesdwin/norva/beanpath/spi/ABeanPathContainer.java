package de.invesdwin.norva.beanpath.spi;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.lang3.builder.ToStringBuilder;

@NotThreadSafe
public abstract class ABeanPathContainer implements IBeanPathContainer {

    @Override
    public final String toString() {
        return new ToStringBuilder(this).append("beanPath", getBeanPath()).append("typePath", getTypePath()).toString();
    }

}
