package de.invesdwin.norva.beanpath.impl.object;

import javax.annotation.concurrent.Immutable;

@Immutable
public class ImmutableRootObjectReference implements IRootObjectReference {

    private final Object rootObject;

    public ImmutableRootObjectReference(final Object rootObject) {
        this.rootObject = rootObject;
    }

    @Override
    public Object getRootObject() {
        return rootObject;
    }

}
