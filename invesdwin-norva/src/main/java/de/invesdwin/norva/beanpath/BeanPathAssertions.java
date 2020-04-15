package de.invesdwin.norva.beanpath;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class BeanPathAssertions {

    private BeanPathAssertions() {
    }

    public static void checkArgument(final boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException("true expected");
        }
    }

    public static void checkArgument(final boolean expression, final String errorMessageTemplate,
            final Object... errorMessageArgs) {
        if (!expression) {
            //CHECKSTYLE:OFF
            throw new IllegalArgumentException(String.format(errorMessageTemplate, errorMessageArgs));
            //CHECKSTYLE:ON
        }
    }

    public static void checkState(final boolean expression) {
        if (!expression) {
            throw new IllegalStateException("true expected");
        }
    }

    public static void checkState(final boolean expression, final String errorMessageTemplate,
            final Object... errorMessageArgs) {
        if (!expression) {
            //CHECKSTYLE:OFF
            throw new IllegalStateException(String.format(errorMessageTemplate, errorMessageArgs));
            //CHECKSTYLE:ON
        }
    }

    public static void checkNotNull(final Object value) {
        if (value == null) {
            throw new NullPointerException("non null expected");
        }
    }

    public static void checkNotNull(final Object value, final String errorMessageTemplate,
            final Object... errorMessageArgs) {
        if (value == null) {
            //CHECKSTYLE:OFF
            throw new IllegalStateException(String.format(errorMessageTemplate, errorMessageArgs));
            //CHECKSTYLE:ON
        }
    }

}
