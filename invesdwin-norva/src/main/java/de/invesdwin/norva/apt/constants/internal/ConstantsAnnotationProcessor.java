package de.invesdwin.norva.apt.constants.internal;

import java.util.Set;

import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import de.invesdwin.norva.apt.AAnnotationProcessor;
import de.invesdwin.norva.apt.constants.BeanPathRoot;
import de.invesdwin.norva.apt.constants.NoBeanPathRoot;
import de.invesdwin.norva.beanpath.impl.model.BeanModelContainer;
import de.invesdwin.norva.beanpath.impl.model.BeanModelContext;
import de.invesdwin.norva.beanpath.impl.model.BeanModelProcessor;
import de.invesdwin.norva.beanpath.impl.model.BeanModelType;
import de.invesdwin.norva.beanpath.spi.BeanPathProcessorConfig;

@SupportedAnnotationTypes({ "*" })
@NotThreadSafe
public class ConstantsAnnotationProcessor extends AAnnotationProcessor {

    @Override
    public void internalProcess(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        try {
            final Set<? extends Element> elements = roundEnv.getRootElements();
            for (final Element element : elements) {
                if (element instanceof TypeElement) {
                    final TypeElement typeElement = (TypeElement) element;
                    final BeanPathRoot beanPathRoot = getAnnotation(typeElement, BeanPathRoot.class);
                    if (beanPathRoot != null) {
                        final NoBeanPathRoot noBeanPathRoot = getAnnotation(typeElement, NoBeanPathRoot.class);
                        if (noBeanPathRoot != null) {
                            continue;
                        }
                        final BeanModelContainer rootContainer = new BeanModelContainer(
                                new BeanModelType(processingEnv, typeElement.asType(), typeElement));
                        final BeanModelContext context = new BeanModelContext(rootContainer, processingEnv);
                        new BeanModelProcessor(BeanPathProcessorConfig.DEFAULT, context,
                                new ConstantsGeneratorVisitor(context, element)).process();
                    }
                }
            }
        } catch (final Throwable t) {
            //CHECKSTYLE:OFF
            t.printStackTrace();
            //CHECKSTYLE:ON
        }
    }

}
