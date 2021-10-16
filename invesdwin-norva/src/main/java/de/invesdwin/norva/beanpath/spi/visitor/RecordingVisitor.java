package de.invesdwin.norva.beanpath.spi.visitor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContext;
import de.invesdwin.norva.beanpath.spi.element.ButtonBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.CheckBoxBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.ComboBoxBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.ContainerOpenBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.HiddenBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.RootBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.TabbedBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.TextFieldBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.UploadButtonBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimpleActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.TableBeanPathElement;

@NotThreadSafe
public class RecordingVisitor implements IBeanPathVisitor {

    private final List<IRecordedEvent> events = new ArrayList<>();
    private final BeanClassContext context;

    public RecordingVisitor(final BeanClassContext context) {
        this.context = context;
    }

    public BeanClassContext getContext() {
        return context;
    }

    @Override
    public void visitRoot(final RootBeanPathElement e) {
        events.add(visitor -> visitor.visitRoot(e));
    }

    @Override
    public void visitContainerOpen(final ContainerOpenBeanPathElement e) {
        events.add(visitor -> visitor.visitContainerOpen(e));
    }

    @Override
    public void visitTextField(final TextFieldBeanPathElement e) {
        events.add(visitor -> visitor.visitTextField(e));
    }

    @Override
    public void visitCheckBox(final CheckBoxBeanPathElement e) {
        events.add(visitor -> visitor.visitCheckBox(e));
    }

    @Override
    public void visitComboBox(final ComboBoxBeanPathElement e) {
        events.add(visitor -> visitor.visitComboBox(e));
    }

    @Override
    public void visitTable(final TableBeanPathElement e) {
        events.add(visitor -> visitor.visitTable(e));
    }

    @Override
    public void visitTabbed(final TabbedBeanPathElement e) {
        events.add(visitor -> visitor.visitTabbed(e));
    }

    @Override
    public void visitButton(final ButtonBeanPathElement e) {
        events.add(visitor -> visitor.visitButton(e));
    }

    @Override
    public void visitUploadButton(final UploadButtonBeanPathElement e) {
        events.add(visitor -> visitor.visitUploadButton(e));
    }

    @Override
    public void visitHidden(final HiddenBeanPathElement e) {
        events.add(visitor -> visitor.visitHidden(e));
    }

    @Override
    public void visitContainerClose() {
        events.add(visitor -> visitor.visitContainerClose());
    }

    @Override
    public void visitInvalidAction(final SimpleActionBeanPathElement e) {
        events.add(visitor -> visitor.visitInvalidAction(e));
    }

    @Override
    public void visitInvalidProperty(final SimplePropertyBeanPathElement e) {
        events.add(visitor -> visitor.visitInvalidProperty(e));
    }

    @Override
    public void finish() {
        events.add(visitor -> visitor.finish());
    }

    public void process(final IBeanPathVisitor... visitors) {
        for (int i = 0; i < events.size(); i++) {
            events.get(i).process(visitors);
        }
    }

    public void process(final IBeanPathVisitor visitor) {
        for (int i = 0; i < events.size(); i++) {
            events.get(i).process(visitor);
        }
    }

    private interface IRecordedEvent {
        void process(IBeanPathVisitor visitor);

        default void process(final IBeanPathVisitor... visitors) {
            for (int i = 0; i < visitors.length; i++) {
                final IBeanPathVisitor visitor = visitors[i];
                process(visitor);
            }
        }
    }

}
