package de.invesdwin.norva.beanpath.spi;

import javax.annotation.concurrent.Immutable;

import de.invesdwin.norva.beanpath.BeanPathStrings;
import de.invesdwin.norva.beanpath.annotation.BeanPathRedirect;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;

@Immutable
public final class BeanPathUtil {

    public static final String TYPE_PATH_SEPARATOR = " -> ";
    public static final String BEAN_PATH_SEPARATOR = ".";

    private BeanPathUtil() {}

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

    public static String maybeAddUtilitySuffix(final String beanPath, final String suffix) {
        return BeanPathStrings.eventuallyAddSuffix(beanPath, BeanPathStrings.capitalize(suffix));
    }

    public static String maybeAddUtilityPrefix(final String beanPath, final String prefix) {
        if (beanPath.contains(BEAN_PATH_SEPARATOR)) {
            final String startPath = BeanPathStrings.substringBeforeLast(beanPath, BEAN_PATH_SEPARATOR);
            final String endPath = BeanPathStrings.substringAfterLast(beanPath, BEAN_PATH_SEPARATOR);
            return startPath + BEAN_PATH_SEPARATOR + BeanPathStrings.eventuallyAddPrefixAndCapitalize(endPath, prefix);
        } else {
            return BeanPathStrings.eventuallyAddPrefixAndCapitalize(beanPath, prefix);
        }
    }

    public static String maybeAddUtilityFragment(final String beanPath, final String fragment) {
        return BeanPathStrings.eventuallyAddSuffix(beanPath, BEAN_PATH_SEPARATOR + fragment);
    }

    public static boolean startsWithAnyBeanPath(final boolean absoluteBeanPath, final String beanPath,
            final String... beanPathPrefixes) {
        if (absoluteBeanPath) {
            final String adjBeanPath = beanPath + BEAN_PATH_SEPARATOR;
            final String[] adjBeanPathPrefixes = new String[beanPathPrefixes.length];
            for (int i = 0; i < beanPathPrefixes.length; i++) {
                adjBeanPathPrefixes[i] = beanPathPrefixes[i] + BEAN_PATH_SEPARATOR;
            }
            return BeanPathStrings.startsWithAny(adjBeanPath, adjBeanPathPrefixes);
        } else {
            return BeanPathStrings.startsWithAny(beanPath, beanPathPrefixes);
        }
    }
}
