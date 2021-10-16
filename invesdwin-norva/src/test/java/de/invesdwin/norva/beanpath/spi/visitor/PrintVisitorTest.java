package de.invesdwin.norva.beanpath.spi.visitor;

import javax.annotation.concurrent.NotThreadSafe;

import org.junit.Test;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassProcessor;

@NotThreadSafe
public class PrintVisitorTest {

    @Test
    public void testPrintVisitor() {
        BeanClassProcessor.process(PrintVisitor.class, new PrintVisitor());
    }

}
