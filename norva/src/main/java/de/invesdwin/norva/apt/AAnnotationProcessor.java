package de.invesdwin.norva.apt;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * All implementations need the annotation:
 * 
 * <p>
 * {@literal @}SupportedAnnotationTypes({ "*" })
 * </p>
 * 
 * 
 * @author subes
 * 
 */
@NotThreadSafe
public abstract class AAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        internalProcess(annotations, roundEnv);
        return false;
    }

    protected abstract void internalProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv);

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    protected <T extends Annotation> T getAnnotation(final TypeElement typeElement, final Class<T> annotationType) {
        final T annotation = getAnnotationElementRecursive(typeElement, annotationType, new HashSet<String>());
        if (annotation != null) {
            return annotation;
        }
        final List<? extends TypeMirror> supertypes = processingEnv.getTypeUtils().directSupertypes(
                processingEnv.getTypeUtils().getDeclaredType(typeElement));
        return getAnnotationTypeRecursive(supertypes, annotationType);
    }

    private <T extends Annotation> T getAnnotationTypeRecursive(final List<? extends TypeMirror> supertypes,
            final Class<T> annotationType) {
        for (final TypeMirror supertype : supertypes) {
            final Element element = processingEnv.getTypeUtils().asElement(supertype);
            T annotation = getAnnotationElementRecursive(element, annotationType, new HashSet<String>());
            if (annotation != null) {
                return annotation;
            }
            annotation = getAnnotationTypeRecursive(processingEnv.getTypeUtils().directSupertypes(supertype),
                    annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        return (T) null;
    }

    private <T extends Annotation> T getAnnotationElementRecursive(final Element element,
            final Class<T> annotationType, final Set<String> stackOverflowFilter) {
        final T annotation = element.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        } else {
            final List<? extends AnnotationMirror> annotationMirrors = processingEnv.getElementUtils()
                    .getAllAnnotationMirrors(element);
            for (final AnnotationMirror annotationMirror : annotationMirrors) {
                final Element asElement = annotationMirror.getAnnotationType().asElement();
                if (stackOverflowFilter.add(asElement.toString())) {
                    final T innerAnnotation = getAnnotationElementRecursive(asElement, annotationType,
                            stackOverflowFilter);
                    if (innerAnnotation != null) {
                        return innerAnnotation;
                    }
                }
            }
        }
        return (T) null;
    }

}
