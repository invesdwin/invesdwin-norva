package de.invesdwin.norva.beanpath.impl.object;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class MutableRootObjectReference implements IRootObjectReference {

    private Object rootObject;

    public MutableRootObjectReference(final Object rootObject) {
        this.rootObject = rootObject;
    }

    @Override
    public Object getRootObject() {
        return rootObject;
    }

    public void setRootObject(final Object rootObject) {
        this.rootObject = rootObject;
    }

}
