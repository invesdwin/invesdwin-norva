package de.invesdwin.norva.beanpath.spi.element.utility;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.BeanPathStrings;
import de.invesdwin.norva.beanpath.annotation.BeanPathRedirect;
import de.invesdwin.norva.beanpath.spi.BeanPathUtil;
import de.invesdwin.norva.beanpath.spi.element.AActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimpleActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class ColumnOrderBeanPathElement extends AActionBeanPathElement implements IUtilityBeanPathElement {

    public static final String COLUMN_ORDER_BEAN_PATH_FRAGMENT = "columnOrder";
    public static final String COLUMN_ORDER_SUFFIX = "ColumnOrder";
    private IBeanPathElement attachedToElement;
    private IBeanPathPropertyModifier<List<?>> columnOrderModifier;

    public ColumnOrderBeanPathElement(final SimpleActionBeanPathElement simpleActionElement) {
        super(simpleActionElement);
    }

    @Override
    protected BeanPathRedirect postProcessRedirect(final BeanPathRedirect annotation) {
        final BeanPathRedirect parent = super.postProcessRedirect(annotation);
        return new BeanPathRedirect() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return BeanPathRedirect.class;
            }

            @Override
            public String value() {
                if (getAccessor().getBeanPathFragment().equals(COLUMN_ORDER_BEAN_PATH_FRAGMENT)) {
                    return BeanPathUtil.maybeAddUtilityFragment(parent.value(), COLUMN_ORDER_BEAN_PATH_FRAGMENT);
                } else if (getAccessor().getBeanPathFragment().endsWith(COLUMN_ORDER_SUFFIX)) {
                    return BeanPathUtil.maybeAddUtilitySuffix(parent.value(), COLUMN_ORDER_SUFFIX);
                } else {
                    throw newInvalidUtilityException();
                }
            }
        };
    }

    @Override
    public void setAttachedToElement(final IBeanPathElement attachedToElement) {
        BeanPathAssertions.checkState(this.attachedToElement == null);
        this.attachedToElement = attachedToElement;
    }

    @Override
    public boolean isAttachedToElement() {
        return attachedToElement != null;
    }

    @Override
    protected void innerAccept(final IBeanPathVisitor visitor) {
        throw new UnsupportedOperationException();
    }

    private IBeanPathPropertyModifier<List<?>> getColumnOrderModifier() {
        if (columnOrderModifier == null) {
            if (getAccessor().getBeanPathFragment().equals(COLUMN_ORDER_BEAN_PATH_FRAGMENT)) {
                columnOrderModifier = new de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.ChoiceBeanPathPropertyModifier(
                        new de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.ActionInvokerBeanObjectAccessor(
                                getInvoker()),
                        null, false);
            } else if (getAccessor().getBeanPathFragment().endsWith(COLUMN_ORDER_SUFFIX)) {
                columnOrderModifier = new de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.ChoiceBeanPathPropertyModifier(
                        getSimpleActionElement().getAccessor(), null, false);
            } else {
                throw newInvalidUtilityException();
            }
        }
        return columnOrderModifier;
    }

    private IllegalStateException newInvalidUtilityException() {
        return new IllegalStateException(
                "Expecting bean path fragment [" + getAccessor().getBeanPathFragment() + "] to either be ["
                        + COLUMN_ORDER_BEAN_PATH_FRAGMENT + "] or to have a suffix of [" + COLUMN_ORDER_SUFFIX + "]");
    }

    public List<String> getColumnOrder() {
        final List<String> columnOrder = new ArrayList<String>();
        for (final Object column : getColumnOrderModifier().getValue()) {
            columnOrder.add(BeanPathStrings.asString(column));
        }
        return columnOrder;
    }

}
