package de.invesdwin.norva.beanpath;

public interface IDeepCloneProvider {

    <T> T deepClone(final T obj);

}
