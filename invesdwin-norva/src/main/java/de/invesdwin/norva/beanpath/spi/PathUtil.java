package de.invesdwin.norva.beanpath.spi;

import javax.annotation.concurrent.Immutable;

import de.invesdwin.norva.beanpath.BeanPathStrings;
import de.invesdwin.norva.beanpath.annotation.BeanPathRedirect;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;

@Immutable
public final class PathUtil {

    public static final String TYPE_PATH_SEPARATOR = " -> ";
    public static final String BEAN_PATH_SEPARATOR = ".";

    private PathUtil() {}

    public static String newBeanPath(final IBeanPathContainer container, final IBeanPathAccessor accessor) {
        return newPath(container.getBeanPath(), BEAN_PATH_SEPARATOR, accessor.getBeanPathFragment());
    }

    public static boolean isShallowBeanPath(final String beanPath) {
        return !beanPath.contains(BEAN_PATH_SEPARATOR);
    }

    public static String newTypePath(final IBeanPathContainer container, final IBeanPathAccessor accessor) {
        return newPath(container.getTypePath(), TYPE_PATH_SEPARATOR, accessor.getTypePathFragment());
    }

    private static String newPath(final String pathPrefix, final String separator, final String newPathFragment) {
        if (BeanPathStrings.isBlank(pathPrefix)) {
            return newPathFragment;
        } else {
            return pathPrefix + separator + newPathFragment;
        }
    }

    public static String newBeanPath(final IBeanPathElement redirector, final BeanPathRedirect redirect) {
        return newPath(redirector.getContainer().getBeanPath(), BEAN_PATH_SEPARATOR, redirect.value());
    }
}
