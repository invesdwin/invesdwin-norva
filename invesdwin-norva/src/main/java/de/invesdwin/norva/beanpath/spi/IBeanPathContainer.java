package de.invesdwin.norva.beanpath.spi;

public interface IBeanPathContainer extends IBeanPathFragment, IUnwrap {

    IBeanPathAccessor getAccessor();

    IBeanPathContainer getRoot();

    IBeanPathContainer getParent();

    IBeanPathType getType();

    @Override
    String toString();

}
