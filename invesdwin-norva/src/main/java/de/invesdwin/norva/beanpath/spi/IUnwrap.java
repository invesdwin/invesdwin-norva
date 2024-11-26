package de.invesdwin.norva.beanpath.spi;

public interface IUnwrap {

    <T> T unwrap(Class<T> type);

    static <T> T unwrap(final IUnwrap obj, final Class<T> type) {
        if (obj == null) {
            return null;
        }
        return obj.unwrap(type);
    }

}
