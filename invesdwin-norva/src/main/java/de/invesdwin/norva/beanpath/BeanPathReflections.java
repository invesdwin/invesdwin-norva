package de.invesdwin.norva.beanpath;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.concurrent.Immutable;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import de.invesdwin.norva.marker.IDate;
import de.invesdwin.norva.marker.IDecimal;
import de.invesdwin.norva.marker.SerializableVoid;

@Immutable
public final class BeanPathReflections extends org.springframework.util.ReflectionUtils {

    public static final String PROPERTY_IS_METHOD_PREFIX = "is";
    public static final String PROPERTY_GET_METHOD_PREFIX = "get";
    public static final String PROPERTY_SET_METHOD_PREFIX = "set";
    public static final String[] PROPERTY_METHOD_PREFIXES = { PROPERTY_IS_METHOD_PREFIX, PROPERTY_GET_METHOD_PREFIX,
            PROPERTY_SET_METHOD_PREFIX };

    public static final Class<?>[] TYPES_INTEGRAL_NUMBER = { byte.class, Byte.class, short.class, Short.class,
            int.class, Integer.class, long.class, Long.class, BigInteger.class };
    public static final Class<?>[] TYPES_DECIMAL_NUMBER = { float.class, Float.class, double.class, Double.class,
            BigDecimal.class, IDecimal.class };
    public static final Class<?>[] TYPES_DATE = { Date.class, Calendar.class, IDate.class };
    public static final Class<?>[] TYPES_STRING = { CharSequence.class };

    private BeanPathReflections() {
    }

    public static boolean isVoid(final Class<?> type) {
        return type == Void.class || type == void.class || type == SerializableVoid.class;
    }

    public static boolean isNumber(final Class<?> type) {
        return Number.class.isAssignableFrom(type) || isIntegralNumber(type) || isDecimalNumber(type);
    }

    public static boolean isIntegralNumber(final Class<?> type) {
        for (final Class<?> integralType : TYPES_INTEGRAL_NUMBER) {
            if (integralType.isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDecimalNumber(final Class<?> type) {
        if (isIntegralNumber(type)) {
            return false;
        }
        for (final Class<?> integralType : TYPES_DECIMAL_NUMBER) {
            if (integralType.isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBoolean(final Class<?> type) {
        return type == boolean.class || type == Boolean.class;
    }

    public static boolean isDate(final Class<?> type) {
        for (final Class<?> dateType : TYPES_DATE) {
            if (dateType.isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isString(final Class<?> type) {
        for (final Class<?> dateType : TYPES_STRING) {
            if (dateType.isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPublic(final Field field) {
        return Modifier.isPublic(field.getModifiers());
    }

    public static boolean isPublic(final Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    public static boolean isStatic(final Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    public static boolean isStatic(final Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    public static boolean isAbstract(final Class<?> type) {
        return Modifier.isAbstract(type.getModifiers());
    }

    public static <T extends Annotation> T getAnnotation(final Method method, final Class<T> annotationType) {
        return getAnnotationRecursive(method.getAnnotations(), annotationType);
    }

    public static <T extends Annotation> T getAnnotation(final Field field, final Class<T> annotationType) {
        return getAnnotationRecursive(field.getAnnotations(), annotationType);
    }

    public static <T extends Annotation> T getAnnotation(final Object object, final Class<T> annotationType) {
        return getAnnotation(object.getClass(), annotationType);
    }

    public static <T extends Annotation> T getAnnotation(final Class<?> clazz, final Class<T> annotationType) {
        Class<?> superClazz = clazz;
        while (superClazz != null) {
            final T annotation = getAnnotationRecursive(superClazz.getAnnotations(), annotationType);
            if (annotation != null) {
                return annotation;
            } else {
                superClazz = superClazz.getSuperclass();
            }
        }
        return null;
    }

    private static <T extends Annotation> T getAnnotationRecursive(final Annotation[] annotations,
            final Class<T> annotationType) {
        return getAnnotationRecursive(annotations, annotationType, new HashSet<String>());
    }

    @SuppressWarnings("unchecked")
    private static <T extends Annotation> T getAnnotationRecursive(final Annotation[] annotations,
            final Class<T> annotationType, final Set<String> stackOverflowFilter) {
        for (final Annotation annotation : annotations) {
            if (annotationType.isAssignableFrom(annotation.annotationType())) {
                return (T) annotation;
            } else {
                if (stackOverflowFilter.add(annotation.annotationType().toString())) {
                    final Annotation[] childAnnotations = annotation.annotationType().getAnnotations();
                    final T recursiveAnnotation = getAnnotationRecursive(childAnnotations, annotationType,
                            stackOverflowFilter);
                    if (recursiveAnnotation != null) {
                        return recursiveAnnotation;
                    }
                }
            }
        }
        return null;
    }

    public static boolean isJavaType(final String qualifiedName) {
        return BeanPathStrings.startsWithAny(qualifiedName, "java.", "javax.");
    }

    public static <A extends Annotation> A getAnnotation(final ProcessingEnvironment env, final Element element,
            final Class<A> annotationType) {
        return getAnnotationRecursive(env, element, annotationType, new HashSet<String>());
    }

    private static <A extends Annotation> A getAnnotationRecursive(final ProcessingEnvironment env,
            final Element element, final Class<A> annotationType, final Set<String> stackOverflowFilter) {
        if (element == null) {
            return null;
        }
        final A annotation = element.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        } else {
            final List<? extends AnnotationMirror> annotationMirrors = env.getElementUtils()
                    .getAllAnnotationMirrors(element);
            for (final AnnotationMirror annotationMirror : annotationMirrors) {
                final Element asElement = annotationMirror.getAnnotationType().asElement();
                if (stackOverflowFilter.add(asElement.toString())) {
                    final A recursiveAnnotation = getAnnotationRecursive(env, asElement, annotationType,
                            stackOverflowFilter);
                    if (recursiveAnnotation != null) {
                        return recursiveAnnotation;
                    }
                }
            }
        }
        return null;
    }

    public static Method findMethodByName(final Class<?> type, final String methodName) {
        return findMethodByName(type, methodName, null);
    }

    public static Method findMethodByName(final Class<?> type, final String methodName, final Integer countParams) {
        for (final Method method : getAllDeclaredMethods(type)) {
            if (method.getName().equals(methodName)
                    && (countParams == null || method.getParameterTypes().length == countParams)) {
                return method;
            }
        }
        return null;
    }

    public static boolean isPublic(final Element element) {
        return element.getModifiers().contains(javax.lang.model.element.Modifier.PUBLIC);
    }

    public static boolean isStatic(final Element element) {
        return element.getModifiers().contains(javax.lang.model.element.Modifier.STATIC);
    }

    public static Element findField(final TypeElement typeElement, final String name) {
        for (final Element element : typeElement.getEnclosedElements()) {
            if (element.getKind() == ElementKind.FIELD && element.getSimpleName().toString().equals(name)) {
                return element;
            }
        }
        return null;
    }

    public static ExecutableElement findMethod(final TypeElement typeElement, final String name,
            final TypeMirror... expectedParameters) {
        for (final Element element : typeElement.getEnclosedElements()) {
            if (element.getKind() == ElementKind.METHOD && element.getSimpleName().toString().equals(name)) {
                final ExecutableElement methodElement = (ExecutableElement) element;
                if (methodElement.getParameters().size() == expectedParameters.length) {
                    boolean paramsMatching = true;
                    for (int i = 0; i < expectedParameters.length; i++) {
                        if (!expectedParameters[i].equals(methodElement.getParameters().get(i).asType())) {
                            paramsMatching = false;
                            break;
                        }
                    }
                    if (paramsMatching) {
                        return methodElement;
                    }
                }
            }
        }
        return null;
    }

    public static ExecutableElement findMethodByName(final TypeElement typeElement, final String name) {
        return findMethodByName(typeElement, name, null);
    }

    public static ExecutableElement findMethodByName(final TypeElement typeElement, final String name,
            final Integer countParams) {
        for (final Element element : typeElement.getEnclosedElements()) {
            if (element.getKind() == ElementKind.METHOD && element.getSimpleName().toString().equals(name)) {
                final ExecutableElement methodElement = (ExecutableElement) element;
                if (countParams == null || methodElement.getParameters().size() == countParams) {
                    return methodElement;
                }
            }
        }
        return null;
    }

    /**
     * Handles anonymous inner classes properly.
     */
    public static String findSimpleName(final Class<?> type) {
        if (type == null) {
            return null;
        }
        final String simpleName = type.getSimpleName();
        if (BeanPathStrings.isBlank(simpleName)) {
            return findSimpleName(type.getSuperclass());
        } else {
            return simpleName;
        }
    }

    public static boolean isPropertyMethodName(final CharSequence methodName) {
        for (final String prefix : PROPERTY_METHOD_PREFIXES) {
            if (BeanPathStrings.startsWith(methodName, prefix) && methodName.length() > prefix.length()) {
                final char nextChar = methodName.charAt(prefix.length());
                if (Character.isUpperCase(nextChar)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getPropertyBeanPathFragment(final String methodName) {
        for (final String prefix : PROPERTY_METHOD_PREFIXES) {
            if (BeanPathStrings.startsWith(methodName, prefix) && methodName.length() > prefix.length()) {
                final char nextChar = methodName.charAt(prefix.length());
                if (Character.isUpperCase(nextChar)) {
                    return Introspector.decapitalize(BeanPathStrings.removeStart(methodName, prefix));
                }
            }
        }
        return methodName;
    }

}
