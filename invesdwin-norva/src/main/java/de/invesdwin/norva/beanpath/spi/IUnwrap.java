package de.invesdwin.norva.beanpath.spi;

public interface IUnwrap {

    <T> T unwrap(Class<T> type);

    /**
     * WARNING: don't use this method where lots of calls are made since this can be costly. Instead use direct casting
     * or store the unwrapped reference additionally in a variable/field.
     */
    static <T> T unwrap(final IUnwrap obj, final Class<T> type) {
        if (obj == null) {
            return null;
        }
        return obj.unwrap(type);
    }

}
