package de.invesdwin.norva.marker;

import java.io.Serializable;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class SerializableVoid implements Serializable {

    /*
     * The Void class cannot be instantiated.
     */
    private SerializableVoid() {}
}
