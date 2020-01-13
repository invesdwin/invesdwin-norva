package de.invesdwin.norva.marker;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class SerializableVoid implements ISerializableValueObject {

    /*
     * The Void class cannot be instantiated.
     */
    private SerializableVoid() {}
}
