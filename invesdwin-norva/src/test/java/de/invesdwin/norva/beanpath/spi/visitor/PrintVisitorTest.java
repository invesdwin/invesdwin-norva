package de.invesdwin.norva.beanpath.spi.visitor;

import org.junit.Test;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContainer;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContext;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassProcessor;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;


public class PrintVisitorTest {

	@Test
	public void testPrintVisitor(){
		BeanClassContext context = new BeanClassContext(new BeanClassContainer(new BeanClassType(PrintVisitor.class)));
		new BeanClassProcessor(context, new PrintVisitor(context)).process();
	}
	
}
