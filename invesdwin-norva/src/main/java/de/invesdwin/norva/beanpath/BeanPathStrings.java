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
     * Prevents the result from becoming empty when the string equals the start string.
     */
    public static String removeAnyStartIfNotEqual(final String s, final String[] starts) {
        for (final String start : starts) {
            if (s.startsWith(start) && !s.equals(start)) {
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

    public static boolean startsWithAny(final String s, final String[] possiblePrefixes) {
        if (s == null) {
            return false;
        }
        for (final String prefix : possiblePrefixes) {
            if (prefix != null && s.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalsAny(final String string, final String... searchStrings) {
        if (string == null) {
            return false;
        }
        for (final String search : searchStrings) {
            if (string.equals(search)) {
                return true;
            }
        }
        return false;
    }

    public static StringBuilder removeEnd(final StringBuilder s, final String end) {
        final int startIndex = s.length() - end.length();
        if (startIndex >= 0 && s.substring(startIndex).equals(end)) {
            return removeEnd(s, end.length());
        } else {
            return s;
        }
    }

    public static StringBuilder removeEnd(final StringBuilder s, final int countCharacters) {
        if (!isEmpty(s)) {
            s.setLength(s.length() - countCharacters);
        }
        return s;
    }

    public static String removeEnd(final String s, final int countCharacters) {
        return s.substring(0, s.length() - countCharacters);
    }

    public static String replaceEnd(final String s, final String end, final String replaceWith) {
        final String endRemoved = removeEnd(s, end);
        if (endRemoved.length() != s.length()) {
            return endRemoved + replaceWith;
        } else {
            return s;
        }
    }

}
