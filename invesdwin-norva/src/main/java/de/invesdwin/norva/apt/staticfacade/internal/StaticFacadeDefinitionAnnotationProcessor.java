package de.invesdwin.norva.apt.staticfacade.internal;

import java.util.Set;

import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import de.invesdwin.norva.apt.AAnnotationProcessor;
import de.invesdwin.norva.apt.staticfacade.StaticFacadeDefinition;

@NotThreadSafe
@SupportedAnnotationTypes({ "de.invesdwin.norva.apt.staticfacade.StaticFacadeDefinition" })
public class StaticFacadeDefinitionAnnotationProcessor extends AAnnotationProcessor {

    @Override
    public void internalProcess(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        final Set<? extends Element> elements = roundEnv.getRootElements();
        for (final Element element : elements) {
            if (element instanceof TypeElement) {
                final TypeElement typeElement = (TypeElement) element;
                final StaticFacadeDefinition staticFacadeDefinition = getAnnotation(typeElement,
                        StaticFacadeDefinition.class);
                if (staticFacadeDefinition != null) {
                    new StaticFacadeGenerator(processingEnv, staticFacadeDefinition, element).run();
                }
            }
        }
    }

}
