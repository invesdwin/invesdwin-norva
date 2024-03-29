package de.invesdwin.norva.beanpath;

import java.io.InputStream;
import java.io.OutputStream;
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

        @Override
        public <T> T deserialize(final byte[] objectData) {
            return org.apache.commons.lang3.SerializationUtils.deserialize(objectData);
        }

        @Override
        public <T> T deserialize(final InputStream in) {
            return org.apache.commons.lang3.SerializationUtils.deserialize(in);
        }

        @Override
        public byte[] serialize(final Serializable obj) {
            return org.apache.commons.lang3.SerializationUtils.serialize(obj);
        }

        @Override
        public int serialize(final OutputStream out, final Serializable obj) {
            final IntCountingOutputStream cout = new IntCountingOutputStream(out);
            org.apache.commons.lang3.SerializationUtils.serialize(obj, cout);
            return cout.getCount();
        }
    };

    private BeanPathObjects() {
    }

    public static void setDeepCloneProvider(final IDeepCloneProvider deepCloneProvider) {
        BeanPathAssertions.checkNotNull(deepCloneProvider);
        BeanPathObjects.deepCloneProvider = deepCloneProvider;
    }

    public static String removeGenericsFromQualifiedName(final String qualifiedName) {
        final String[] split = BeanPathStrings.splitPreserveAllTokens(qualifiedName.replace(" ", ""), "<");
        String simpleName = split[0];
        if (qualifiedName.endsWith("[]")) {
            simpleName = BeanPathStrings.putSuffix(simpleName, "[]");
        }
        return simpleName;
    }

    public static String simplifyQualifiedName(final String qualifiedName) {
        final StringBuilder simpleName = new StringBuilder();
        final String[] splitLayer = BeanPathStrings.splitPreserveAllTokens(qualifiedName.replace(" ", ""), "<");
        for (final String layer : splitLayer) {
            if (BeanPathStrings.endsWithAny(qualifiedName, new String[] { ">", ">[]" }) && simpleName.length() > 0) {
                simpleName.append("<");
            }
            final String[] splitComma = BeanPathStrings.splitPreserveAllTokens(layer, ",");
            boolean firstComma = true;
            for (final String s : splitComma) {
                if (!firstComma) {
                    simpleName.append(", ");
                }
                firstComma = false;
                final int lastDotPos = s.lastIndexOf(".");
                if (lastDotPos != -1) {
                    simpleName.append(s.substring(lastDotPos + 1));
                } else {
                    simpleName.append(s);
                }
            }
        }
        if (qualifiedName.endsWith("[]")) {
            return BeanPathStrings.putSuffix(simpleName.toString(), "[]");
        } else {
            return simpleName.toString();
        }
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
        if (obj instanceof Cloneable) {
            final Method cloneMethod = BeanPathReflections.findMethod(obj.getClass(), "clone");
            if (cloneMethod != null) {
                BeanPathReflections.makeAccessible(cloneMethod);
                return (T) BeanPathReflections.invokeMethod(cloneMethod, obj);
            }
        }
        if (obj instanceof Serializable) {
            return deepClone(obj);
        } else {
            throw new UnsupportedOperationException("Object [" + obj + "] of type [" + obj.getClass().getName()
                    + "] is neither cloneable, nor serializable!");
        }
    }

    public static <T> T deepClone(final T obj) {
        return deepCloneProvider.deepClone(obj);
    }

    public static <T> T deserialize(final byte[] objectData) {
        return deepCloneProvider.deserialize(objectData);
    }

    public static <T> T deserialize(final InputStream in) {
        return deepCloneProvider.deserialize(in);
    }

    public static byte[] serialize(final Serializable obj) {
        return deepCloneProvider.serialize(obj);
    }

    public static int serialize(final OutputStream out, final Serializable obj) {
        return deepCloneProvider.serialize(out, obj);
    }

}
