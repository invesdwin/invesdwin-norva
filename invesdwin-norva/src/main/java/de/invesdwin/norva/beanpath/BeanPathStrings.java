package de.invesdwin.norva.beanpath;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class BeanPathStrings extends org.apache.commons.lang3.StringUtils {

    private BeanPathStrings() {}

    public static String eventuallyAddSuffix(final String s, final String suffix) {
        if (s == null) {
            return null;
        }
        if (suffix == null || s.endsWith(suffix)) {
            return s;
        } else {
            return s + suffix;
        }
    }

    public static String eventuallyAddPrefix(final String s, final String prefix) {
        if (s == null) {
            return null;
        }
        if (prefix == null || s.startsWith(prefix)) {
            return s;
        } else {
            return prefix + s;
        }
    }

    public static String eventuallyAddPrefixAndCapitalize(final String s, final String prefix) {
        if (s == null) {
            return null;
        }
        if (prefix == null || s.startsWith(prefix)) {
            return s;
        } else {
            return prefix + capitalize(s);
        }
    }

    public static String removeAnyStart(final String s, final String... starts) {
        for (final String start : starts) {
            if (s.startsWith(start)) {
                return removeStart(s, start);
            }
        }
        return s;
    }

    /**
     * Calls o.toString(). Returns null if o is null.
     */
    public static String asString(final Object o) {
        if (o == null) {
            return null;
        } else {
            return o.toString();
        }
    }

}
