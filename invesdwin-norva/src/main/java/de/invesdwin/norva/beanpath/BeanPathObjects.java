package de.invesdwin.norva.beanpath;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class BeanPathObjects {

    private static IDeepCloneProvider deepCloneProvider = new IDeepCloneProvider() {

        @SuppressWarnings("unchecked")
        @Override
        public <T> T deepClone(final T obj) {
            //use java serialization as default deep clone provider
            return (T) org.apache.commons.lang3.SerializationUtils.clone((Serializable) obj);
        }
    };

    private BeanPathObjects() {}

    public static void setDeepCloneProvider(final IDeepCloneProvider deepCloneProvider) {
        org.assertj.core.api.Assertions.assertThat(deepCloneProvider).isNotNull();
        BeanPathObjects.deepCloneProvider = deepCloneProvider;
    }

    public static String removeGenericsFromQualifiedName(final String qualifiedName) {
        final String[] split = BeanPathStrings.split(qualifiedName.replace(" ", ""), "<");
        String simpleName = split[0];
        if (qualifiedName.endsWith("[]")) {
            simpleName = BeanPathStrings.eventuallyAddSuffix(simpleName, "[]");
        }
        return simpleName;
    }

    public static String simplifyQualifiedName(final String qualifiedName) {
        String simpleName = "";
        final String[] splitLayer = BeanPathStrings.split(qualifiedName.replace(" ", ""), "<");
        for (final String layer : splitLayer) {
            if (BeanPathStrings.endsWithAny(qualifiedName, new String[] { ">", ">[]" }) && simpleName.length() > 0) {
                simpleName += "<";
            }
            final String[] splitComma = BeanPathStrings.split(layer, ",");
            boolean firstComma = true;
            for (final String s : splitComma) {
                if (!firstComma) {
                    simpleName += ", ";
                }
                firstComma = false;
                final int lastDotPos = s.lastIndexOf(".");
                if (lastDotPos != -1) {
                    simpleName += s.substring(lastDotPos + 1);
                } else {
                    simpleName += s;
                }
            }
        }
        if (qualifiedName.endsWith("[]")) {
            simpleName = BeanPathStrings.eventuallyAddSuffix(simpleName, "[]");
        }
        return simpleName;
    }

    /**
     * Converts something like "beanPathModelClass" to "Bean Path Model Class".
     */
    public static String toVisibleName(final String name) {
        final String capitalizedName = BeanPathStrings.capitalize(name);
        final StringBuilder visibleName = new StringBuilder();
        char prevChar = 'X';
        for (int i = 0; i < capitalizedName.length(); i++) {
            final char curChar = capitalizedName.charAt(i);
            if (Character.isUpperCase(curChar) && Character.isLowerCase(prevChar)) {
                visibleName.append(" ");
            }
            visibleName.append(curChar);
            prevChar = curChar;
        }
        return visibleName.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(final T obj) {
        if (obj == null) {
            return null;
        }
        final Method cloneMethod = BeanPathReflections.findMethod(obj.getClass(), "clone");
        if (cloneMethod != null) {
            return (T) BeanPathReflections.invokeMethod(cloneMethod, obj);
        } else if (obj instanceof Serializable) {
            return (T) deepCloneProvider.deepClone((Serializable) obj);
        } else {
            throw new UnsupportedOperationException("Object [" + obj + "] is neither cloneable, nor serializable!");
        }

    }

}
