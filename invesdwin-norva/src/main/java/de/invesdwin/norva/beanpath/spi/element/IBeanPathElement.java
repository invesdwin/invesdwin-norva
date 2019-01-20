package de.invesdwin.norva.beanpath.spi.element;

import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathContainer;
import de.invesdwin.norva.beanpath.spi.IBeanPathFragment;
import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.TitleBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.utility.ContainerTitleBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.DisableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.HideBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.TitleBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.TooltipBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

public interface IBeanPathElement extends IBeanPathFragment {

    ABeanPathContext getContext();

    IBeanPathContainer getContainer();

    IBeanPathAccessor getAccessor();

    boolean accept(IBeanPathVisitor... visitors);

    boolean shouldBeAddedToElementRegistry();

    boolean isProperty();

    boolean isAction();

    boolean isForced();

    IBeanPathElement getParentElement();

    @Override
    String toString();

    InterceptedBeanPathElement getInterceptedElement();

    HideBeanPathElement getHideElement();

    DisableBeanPathElement getDisableElement();

    ContainerTitleBeanPathElement getContainerTitleElement();

    TitleBeanPathElement getTitleElement();

    TitleBeanPathPropertyModifier getTitleModifier();

    TooltipBeanPathElement getTooltipElement();

    /**
     * True if the container is not null and no disableElement or Disabled annotation disables this on the path.
     */
    boolean isEnabled(Object target);

    /**
     * True if this is not a hiddenElement (hidden annotation) and no hideElement hides this on the path.
     */
    boolean isVisible(Object target);

    /**
     * gets either the hideElement text, disableElement text or the tooltip annotation value.
     */
    String getTooltip(Object target);

    String getTitle();

    /**
     * get the title, first the property title element, then the property title annotation, then title action element,
     * then type title annotation is used.
     */
    String getTitle(Object target);

    String getVisibleName();

}
