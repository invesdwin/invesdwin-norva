package de.invesdwin.norva.beanpath;

import java.io.InputStream;
import java.io.Serializable;

public interface IDeepCloneProvider {

    <T> T deepClone(final T obj);

    <T> T deserialize(final byte[] objectData);

    <T> T deserialize(final InputStream in);

    byte[] serialize(final Serializable obj);

}
