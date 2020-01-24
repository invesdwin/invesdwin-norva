package de.invesdwin.norva.beanpath;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.ArrayUtils;

@Immutable
public final class BeanPathStrings extends org.apache.commons.lang3.StringUtils {

    private BeanPathStrings() {}

    public static String putSuffix(final String s, final String suffix) {
        if (s == null) {
            return null;
        }
        if (suffix == null || s.endsWith(suffix)) {
            return s;
        } else {
            return s + suffix;
        }
    }

    public static String putSuffixIgnoreCase(final String s, final String suffix) {
        if (s == null) {
            return null;
        }
        if (suffix == null || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase(s, suffix)) {
            return s;
        } else {
            return s + suffix;
        }
    }

    public static String putPrefix(final String s, final String prefix) {
        if (s == null) {
            return null;
        }
        if (prefix == null || s.startsWith(prefix)) {
            return s;
        } else {
            return prefix + s;
        }
    }

    public static String putPrefixIgnoreCase(final String s, final String prefix) {
        if (s == null) {
            return null;
        }
        if (prefix == null || org.apache.commons.lang3.StringUtils.startsWithIgnoreCase(s, prefix)) {
            return s;
        } else {
            return prefix + s;
        }
    }

    public static String putPrefixAndCapitalize(final String s, final String prefix) {
        if (s == null) {
            return null;
        }
        if (prefix == null || s.startsWith(prefix)) {
            return s;
        } else {
            return prefix + capitalize(s);
        }
    }

    public static String putPrefixAndCapitalizeIgnoreCase(final String s, final String prefix) {
        if (s == null) {
            return null;
        }
        if (prefix == null || org.apache.commons.lang3.StringUtils.startsWithIgnoreCase(s, prefix)) {
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

    public static String removeAnyStartIgnoreCase(final String s, final String... starts) {
        for (final String start : starts) {
            if (startsWithIgnoreCase(s, start)) {
                return removeStartIgnoreCase(s, start);
            }
        }
        return s;
    }

    public static String removeAnyEndIgnoreCase(final String s, final String... ends) {
        for (final String end : ends) {
            if (endsWithIgnoreCase(s, end)) {
                return removeEndIgnoreCase(s, end);
            }
        }
        return s;
    }

    /**
     * Prevents the result from becoming empty when the string equals the start string.
     */
    public static String removeAnyStartIfNotEqual(final String s, final String... starts) {
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

    public static boolean startsWithAny(final String s, final String... possiblePrefixes) {
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

    public static boolean startsWithAnyIgnoreCase(final String s, final String... possiblePrefixes) {
        if (s == null) {
            return false;
        }
        for (final String prefix : possiblePrefixes) {
            if (prefix != null && org.apache.commons.lang3.StringUtils.startsWithIgnoreCase(s, prefix)) {
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

    public static boolean equalsAnyIgnoreCase(final String string, final String... searchStrings) {
        if (string == null) {
            return false;
        }
        for (final String search : searchStrings) {
            if (string.equalsIgnoreCase(search)) {
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
        final int newLength = s.length() - countCharacters;
        if (newLength >= 0) {
            s.setLength(newLength);
        }
        return s;
    }

    public static String removeEnd(final String s, final int countCharacters) {
        final int newLength = s.length() - countCharacters;
        if (newLength >= 0) {
            return s.substring(0, newLength);
        } else {
            return s;
        }
    }

    public static String replaceEnd(final String s, final String end, final String replaceWith) {
        final String endRemoved = removeEnd(s, end);
        if (endRemoved.length() != s.length()) {
            return endRemoved + replaceWith;
        } else {
            return s;
        }
    }

    public static boolean endsWithAnyIgnoreCase(final CharSequence sequence, final CharSequence... searchStrings) {
        if (isEmpty(sequence) || ArrayUtils.isEmpty(searchStrings)) {
            return false;
        }
        for (final CharSequence searchString : searchStrings) {
            if (endsWithIgnoreCase(sequence, searchString)) {
                return true;
            }
        }
        return false;
    }

}
