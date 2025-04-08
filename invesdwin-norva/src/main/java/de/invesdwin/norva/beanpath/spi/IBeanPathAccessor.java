package de.invesdwin.norva.beanpath.spi;

import java.lang.annotation.Annotation;

import de.invesdwin.norva.beanpath.spi.annotation.IBeanPathAnnotationProvider;
import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;

/**
 * This represents a field or a method that gives access to a class or object.
 */
public interface IBeanPathAccessor extends IUnwrap, IBeanPathAnnotationProvider {

    ABeanPathContext getContext();

    IBeanPathContainer getContainer();

    String getRawName();

    /**
     * For the reason why sometimes the bean path name starts with an uppercase letter, see:
     * http://stackoverflow.com/questions/8969112/confused-about-naming-of-javabean-properties-with-respect-to-getters-
     * and-setter
     */
    String getBeanPathFragment();

    String getTypePathFragment();

    boolean isPublic();

    boolean isStatic();

    boolean isNullable();

    /**
     * Checks the action method, getter method, setter method and field of this accessor in that order to find the given
     * annotation.
     */
    @Override
    <T extends Annotation> T getAnnotation(Class<T> annotationType);

    Integer getPublicSetterParameterCount();

    Integer getPublicGetterParameterCount();

    Integer getPublicActionParameterCount();

    @Override
    String toString();

    /**
     * The raw type e.g. List&lt;SomeBean&gt; for List&lt;SomeBean&gt;
     */
    IBeanPathType getRawType();

    /**
     * The bean type e.g. SomeBean for List&lt;SomeBean&gt;
     */
    IBeanPathType getType();

    boolean hasPublicGetterOrField();

    boolean hasPublicGetter();

    boolean hasPublicSetterOrField();

    boolean hasPublicSetter();

    boolean hasPublicField();

    boolean hasPublicAction();

    String getPublicGetterName();

    String getPublicSetterName();

    String getPublicFieldName();

    String getPublicActionName();

}
