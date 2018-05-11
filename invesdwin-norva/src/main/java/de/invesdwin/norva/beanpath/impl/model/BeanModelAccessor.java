package de.invesdwin.norva.beanpath.impl.model;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.util.List;

import javax.annotation.concurrent.Immutable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import de.invesdwin.norva.beanpath.BeanPathObjects;
import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.BeanPathStrings;
import de.invesdwin.norva.beanpath.spi.ABeanPathAccessor;

@Immutable
public class BeanModelAccessor extends ABeanPathAccessor {

    private final BeanModelContext context;
    private final BeanModelContainer container;
    private final Element rawElement;
    private final ExecutableElement rawMethodElement;
    private final Element rawFieldElement;
    private final Element publicFieldElement;
    private final ExecutableElement publicGetterMethodElement;
    private final ExecutableElement publicSetterMethodElement;
    private final ExecutableElement publicActionMethodElement;
    private final BeanModelType rawType;
    private final BeanModelType type;

    public BeanModelAccessor(final BeanModelContext context, final BeanModelContainer container,
            final Element element) {
        this.context = context;
        this.container = container;
        this.rawElement = element;
        this.rawMethodElement = determineRawMethodElement();
        this.rawFieldElement = determineRawFieldElement();
        this.rawType = determineRawType();
        this.type = determineType();
        this.publicFieldElement = determinePublicFieldElement();
        this.publicGetterMethodElement = determinePublicGetterMethodElement();
        this.publicSetterMethodElement = determinePublicSetterMethodElement();
        this.publicActionMethodElement = determinePublicActionMethodElement();
    }

    private Element determineRawFieldElement() {
        if (rawElement.getKind() == ElementKind.FIELD) {
            return rawElement;
        }
        return null;
    }

    private ExecutableElement determineRawMethodElement() {
        if (rawElement.getKind() == ElementKind.METHOD) {
            return (ExecutableElement) rawElement;
        }
        return null;
    }

    private ExecutableElement determinePublicActionMethodElement() {
        if (!BeanPathStrings.startsWithAny(getRawName(), BeanPathReflections.PROPERTY_METHOD_PREFIXES)) {
            final Element actionMethod = rawMethodElement;
            if (actionMethod != null && BeanPathReflections.isPublic(actionMethod)) {
                return (ExecutableElement) actionMethod;
            }
        }
        return null;
    }

    private ExecutableElement determinePublicGetterMethodElement() {
        if (BeanPathStrings.startsWithAny(getRawName(), BeanPathReflections.PROPERTY_METHOD_PREFIXES)) {
            ExecutableElement getterMethod = BeanPathReflections.findMethod(container.getType().getTypeElement(),
                    BeanPathReflections.PROPERTY_GET_METHOD_PREFIX + BeanPathStrings.capitalize(getBeanPathFragment()));
            if (getterMethod == null) {
                getterMethod = BeanPathReflections.findMethod(container.getType().getTypeElement(),
                        BeanPathReflections.PROPERTY_IS_METHOD_PREFIX
                                + BeanPathStrings.capitalize(getBeanPathFragment()));
            }
            if (getterMethod != null && BeanPathReflections.isPublic(getterMethod)) {
                return getterMethod;
            }
        }
        return null;
    }

    private ExecutableElement determinePublicSetterMethodElement() {
        if (BeanPathStrings.startsWithAny(getRawName(), BeanPathReflections.PROPERTY_METHOD_PREFIXES)) {
            final String setterMethodName = BeanPathReflections.PROPERTY_SET_METHOD_PREFIX
                    + BeanPathStrings.capitalize(getBeanPathFragment());
            ExecutableElement setterMethod = BeanPathReflections.findMethod(container.getType().getTypeElement(),
                    setterMethodName, getRawType().getTypeMirror());
            if (setterMethod == null) {
                //fallback to find by name in case the setter has a superclass as type argument or even Object maybe
                setterMethod = BeanPathReflections.findMethodByName(container.getType().getTypeElement(),
                        setterMethodName, 1);
            }
            if (setterMethod != null && BeanPathReflections.isPublic(setterMethod)) {
                return setterMethod;
            }
        }
        return null;
    }

    private Element determinePublicFieldElement() {
        final Element field = BeanPathReflections.findField(container.getType().getTypeElement(),
                getBeanPathFragment());
        if (field != null && BeanPathReflections.isPublic(field)) {
            return field;
        }
        return null;
    }

    private BeanModelType determineType() {
        final TypeMirror typeMirror;
        final TypeMirror rawTypeMirror = rawType.getTypeMirror();
        if (rawTypeMirror instanceof ArrayType) {
            final ArrayType cRawTypeMirror = (ArrayType) rawTypeMirror;
            TypeMirror componentTypeMirror;
            try {
                componentTypeMirror = cRawTypeMirror.getComponentType();
            } catch (final Throwable t) {
                //java.lang.ClassCastException: com.sun.tools.javac.code.Type$ClassType cannot be cast to com.sun.tools.javac.code.Type$ArrayType
                componentTypeMirror = rawTypeMirror;
            }
            typeMirror = componentTypeMirror;
        } else if (rawType.isIterable()) {
            final DeclaredType rawDeclaredType = (DeclaredType) rawTypeMirror;
            final List<? extends TypeMirror> typeArguments = rawDeclaredType.getTypeArguments();
            if (typeArguments.size() == 1) {
                typeMirror = typeArguments.get(0);
            } else {
                //fallback to rawType, since this is not a generic iterable collection
                typeMirror = rawTypeMirror;
            }
        } else {
            //fallback to rawType, since this is not an array or generic
            typeMirror = rawTypeMirror;
        }
        return new BeanModelType(context.getEnv(), typeMirror, determineTypeElement(typeMirror));
    }

    private BeanModelType determineRawType() {
        final TypeMirror rawTypeMirror = determineRawTypeMirror();
        final TypeElement rawTypeElement = determineTypeElement(rawTypeMirror);
        return new BeanModelType(context.getEnv(), rawTypeMirror, rawTypeElement);
    }

    private TypeMirror determineRawTypeMirror() {
        switch (rawElement.getKind()) {
        case FIELD:
            return rawFieldElement.asType();
        case METHOD:
            if (rawMethodElement.getParameters().size() == 1
                    && !BeanPathStrings.startsWithAny(String.valueOf(rawMethodElement.getSimpleName()),
                            BeanPathReflections.PROPERTY_GET_METHOD_PREFIX,
                            BeanPathReflections.PROPERTY_IS_METHOD_PREFIX)) {
                return rawMethodElement.getParameters().get(0).asType();
            } else {
                return rawMethodElement.getReturnType();
            }
        default:
            throw new IllegalArgumentException(
                    "Unknown " + ElementKind.class.getSimpleName() + ": " + rawElement.getKind());
        }
    }

    private TypeElement determineTypeElement(final TypeMirror typeMirror) {
        final Element asElement = context.getEnv().getTypeUtils().asElement(typeMirror);
        if (asElement instanceof TypeElement) {
            return (TypeElement) asElement;
        } else {
            return null;
        }
    }

    @Override
    public BeanModelContext getContext() {
        return context;
    }

    @Override
    public BeanModelContainer getContainer() {
        return container;
    }

    public Element getRawElement() {
        return rawElement;
    }

    public Element getRawFieldElement() {
        return rawFieldElement;
    }

    public Element getRawMethodElement() {
        return rawMethodElement;
    }

    public Element getPublicGetterMethodElement() {
        return publicGetterMethodElement;
    }

    public Element getPublicSetterMethodElement() {
        return publicSetterMethodElement;
    }

    public Element getPublicActionMethodElement() {
        return publicActionMethodElement;
    }

    public Element getPublicFieldElement() {
        return publicFieldElement;
    }

    @Override
    public String getRawName() {
        return String.valueOf(rawElement.getSimpleName());
    }

    @Override
    public String getBeanPathFragment() {
        switch (rawElement.getKind()) {
        case FIELD:
            return Introspector.decapitalize(getRawName());
        case METHOD:
            return Introspector.decapitalize(BeanPathStrings.removeAnyStartIfNotEqual(getRawName(),
                    BeanPathReflections.PROPERTY_METHOD_PREFIXES));
        default:
            throw new IllegalArgumentException(
                    "Unknown " + ElementKind.class.getSimpleName() + ": " + rawElement.getKind());
        }
    }

    @Override
    public String getTypePathFragment() {
        return BeanPathObjects.simplifyQualifiedName(getRawType().getTypeMirror().toString());
    }

    @Override
    public boolean isPublic() {
        return BeanPathReflections.isPublic(rawElement);
    }

    @Override
    public boolean isStatic() {
        return BeanPathReflections.isStatic(rawElement);
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        if (publicSetterMethodElement != null) {
            final T annotation = BeanPathReflections.getAnnotation(context.getEnv(), publicSetterMethodElement,
                    annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        if (publicSetterMethodElement != null) {
            final T annotation = BeanPathReflections.getAnnotation(context.getEnv(), publicSetterMethodElement,
                    annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        if (publicSetterMethodElement != null) {
            final T annotation = BeanPathReflections.getAnnotation(context.getEnv(), publicSetterMethodElement,
                    annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        if (rawFieldElement != null) {
            final T annotation = BeanPathReflections.getAnnotation(context.getEnv(), rawFieldElement, annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        if (rawMethodElement != null) {
            //fallback to raw method element, since this is either a field or a method!
            return BeanPathReflections.getAnnotation(context.getEnv(), rawMethodElement, annotationType);
        } else {
            return (T) null;
        }
    }

    @Override
    public Integer getPublicActionParameterCount() {
        switch (rawElement.getKind()) {
        case METHOD:
            if (publicActionMethodElement != null) {
                return publicActionMethodElement.getParameters().size();
            }
            //fallthrough to field
        case FIELD:
            return null;
        default:
            throw new IllegalArgumentException(
                    "Unknown " + ElementKind.class.getSimpleName() + ": " + rawElement.getKind());
        }
    }

    @Override
    public Integer getPublicGetterParameterCount() {
        switch (rawElement.getKind()) {
        case METHOD:
            if (publicGetterMethodElement != null) {
                return publicGetterMethodElement.getParameters().size();
            }
            //fallthrough to field
        case FIELD:
            if (publicFieldElement != null) {
                return 0;
            } else {
                return null;
            }
        default:
            throw new IllegalArgumentException(
                    "Unknown " + ElementKind.class.getSimpleName() + ": " + rawElement.getKind());
        }
    }

    @Override
    public Integer getPublicSetterParameterCount() {
        switch (rawElement.getKind()) {
        case METHOD:
            if (publicSetterMethodElement != null) {
                return publicSetterMethodElement.getParameters().size();
            }
            //fallthrough to field
        case FIELD:
            if (publicFieldElement != null) {
                return 1;
            } else {
                return null;
            }
        default:
            throw new IllegalArgumentException(
                    "Unknown " + ElementKind.class.getSimpleName() + ": " + rawElement.getKind());
        }
    }

    @Override
    public BeanModelType getRawType() {
        return rawType;
    }

    @Override
    public BeanModelType getType() {
        return type;
    }

    @Override
    public boolean hasPublicGetterOrField() {
        return publicGetterMethodElement != null || publicFieldElement != null;
    }

    @Override
    public boolean hasPublicSetterOrField() {
        return publicSetterMethodElement != null || publicFieldElement != null;
    }

    @Override
    public boolean hasPublicAction() {
        return publicActionMethodElement != null;
    }

}
