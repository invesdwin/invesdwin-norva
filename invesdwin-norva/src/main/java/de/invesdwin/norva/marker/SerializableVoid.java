package de.invesdwin.norva.marker;

import java.io.Serializable;

import javax.annotation.concurrent.Immutable;

/**
 * Not a ISerializableValueObject because it will never be serialized anyway.
 * 
 * This class can be used in interfaces where a serializable generic is required but none will be used. Often it is
 * better to redesign the interface, but if that is not possible, this type might provide a workaround.
 */
@Immutable
public final class SerializableVoid implements Serializable {

    /*
     * The Void class cannot be instantiated.
     */
    private SerializableVoid() {}
}
