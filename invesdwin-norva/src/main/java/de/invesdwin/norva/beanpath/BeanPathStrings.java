package de.invesdwin.norva.beanpath;

import javax.annotation.concurrent.Immutable;

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
            return asStringNotNull(o);
        }
    }

    public static String asStringNotNull(final Object o) {
        if (o != null && o.getClass().isArray()) {
            if (o instanceof boolean[]) {
                return java.util.Arrays.toString((boolean[]) o);
            } else if (o instanceof byte[]) {
                return java.util.Arrays.toString((byte[]) o);
            } else if (o instanceof char[]) {
                return java.util.Arrays.toString((char[]) o);
            } else if (o instanceof double[]) {
                return java.util.Arrays.toString((double[]) o);
            } else if (o instanceof float[]) {
                return java.util.Arrays.toString((float[]) o);
            } else if (o instanceof int[]) {
                return java.util.Arrays.toString((int[]) o);
            } else if (o instanceof long[]) {
                return java.util.Arrays.toString((long[]) o);
            } else if (o instanceof short[]) {
                return java.util.Arrays.toString((short[]) o);
            } else if (o instanceof Object[]) {
                return java.util.Arrays.toString((Object[]) o);
            }
        }
        return o.toString();
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
        if (isEmpty(sequence) || org.apache.commons.lang3.ArrayUtils.isEmpty(searchStrings)) {
            return false;
        }
        for (final CharSequence searchString : searchStrings) {
            if (endsWithIgnoreCase(sequence, searchString)) {
                return true;
            }
        }
        return false;
    }

    public static int lastIndexOfAnyIgnoreCase(final CharSequence str, final CharSequence... searchStrs) {
        if (str == null || searchStrs == null) {
            return INDEX_NOT_FOUND;
        }
        int ret = INDEX_NOT_FOUND;
        int tmp = 0;
        for (final CharSequence search : searchStrs) {
            if (search == null) {
                continue;
            }
            tmp = lastIndexOfIgnoreCase(str, search, str.length());
            if (tmp > ret) {
                ret = tmp;
            }
        }
        return ret;
    }

    /**
     * Gets the substring before the first occurrence of a separator, including the separator.
     * <p>
     * A {@code null} string input will return {@code null}. An empty ("") string input will return the empty string.
     *
     * <p>
     * If nothing is found, the empty string is returned.
     * </p>
     *
     * @param str
     *            the String to get a substring from, may be null
     * @param separator
     *            the character to search.
     * @return the substring before the first occurrence of the separator, including the separator. {@code null} if null
     *         String input
     */
    public static String substringBeforeInclusive(final String str, final String separator) {
        if (isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.isEmpty()) {
            return EMPTY;
        }
        final int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos + separator.length());
    }

    /**
     * Gets the after before the first occurrence of a separator, including the separator.
     * <p>
     * A {@code null} string input will return {@code null}. An empty ("") string input will return the empty string.
     *
     * <p>
     * If nothing is found, the empty string is returned.
     * </p>
     *
     * @param str
     *            the String to get a substring from, may be null
     * @param separator
     *            the character to search.
     * @return the substring after the first occurrence of the separator, including the separator. {@code null} if null
     *         String input
     */
    public static String substringAfterInclusive(final String str, final String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        final int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str.substring(pos);
    }
}
