package de.invesdwin.norva.beanpath.spi.visitor;

import javax.annotation.concurrent.NotThreadSafe;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContainer;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContext;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassProcessor;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;

@RunWith(JUnitPlatform.class)
@NotThreadSafe
public class PrintVisitorTest {

    @Test
    public void testPrintVisitor() {
        final BeanClassContext context = new BeanClassContext(
                new BeanClassContainer(new BeanClassType(PrintVisitor.class)));
        new BeanClassProcessor(context, new PrintVisitor(context)).process();
    }

}
