package de.invesdwin.norva.beanpath.spi;

public interface IBeanPathContainer extends IBeanPathFragment {

    IBeanPathAccessor getAccessor();

    IBeanPathContainer getRoot();

    IBeanPathContainer getParent();

    IBeanPathType getType();

    @Override
    String toString();

}
