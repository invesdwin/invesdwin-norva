package de.invesdwin.norva.beanpath;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public interface IDeepCloneProvider {

    <T> T deepClone(T obj);

    <T> T deserialize(byte[] objectData);

    <T> T deserialize(InputStream in);

    byte[] serialize(Serializable obj);

    int serialize(Serializable obj, OutputStream out);

}
