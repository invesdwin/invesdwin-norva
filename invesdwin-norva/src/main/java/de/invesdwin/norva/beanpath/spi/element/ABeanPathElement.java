package de.invesdwin.norva.beanpath.spi.element;

import java.lang.annotation.AnnotationTypeMismatchException;
import java.util.Comparator;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.lang3.builder.ToStringBuilder;

import de.invesdwin.norva.beanpath.BeanPathObjects;
import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.BeanPathStrings;
import de.invesdwin.norva.beanpath.annotation.Disabled;
import de.invesdwin.norva.beanpath.annotation.Hidden;
import de.invesdwin.norva.beanpath.annotation.BeanPathRedirect;
import de.invesdwin.norva.beanpath.annotation.Title;
import de.invesdwin.norva.beanpath.annotation.Tooltip;
import de.invesdwin.norva.beanpath.impl.model.BeanModelContext;
import de.invesdwin.norva.beanpath.impl.object.BeanObjectAccessor;
import de.invesdwin.norva.beanpath.impl.object.BeanObjectContainer;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathContainer;
import de.invesdwin.norva.beanpath.spi.PathUtil;
import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;
import de.invesdwin.norva.beanpath.spi.element.utility.ContainerTitleBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.DisableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.HideBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.TitleBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.TooltipBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public abstract class ABeanPathElement implements IBeanPathElement {

    /**
     * This will sort bean path elements ascending by bean path.
     */
    public static final Comparator<IBeanPathElement> COMPARATOR = new Comparator<IBeanPathElement>() {
        @Override
        public int compare(final IBeanPathElement o1, final IBeanPathElement o2) {
            return o1.getBeanPath().compareTo(o2.getBeanPath());
        }
    };

    private final ABeanPathContext context;
    private final IBeanPathContainer container;
    private final IBeanPathAccessor accessor;
    private final String beanPath;
    private final BeanPathRedirect redirect;
    private InterceptedBeanPathElement interceptedElement;
    private boolean firstAccept = true;

    private HideBeanPathElement hideElement;
    private DisableBeanPathElement disableElement;
    private ContainerTitleBeanPathElement containerTitleElement;
    private TitleBeanPathElement titleElement;
    private TooltipBeanPathElement tooltipElement;

    public ABeanPathElement(final ABeanPathContext context, final IBeanPathContainer container,
            final IBeanPathAccessor accessor) {
        this.context = context;
        this.container = container;
        this.accessor = accessor;
        this.beanPath = PathUtil.newBeanPath(container, accessor);
        //add interceptor
        this.redirect = accessor.getAnnotation(BeanPathRedirect.class);
        init();
    }

    protected void init() {
        if (redirect != null && shouldBeAddedToElementRegistry()) {
            try {
                context.getElementRegistry().addRedirector(this, redirect);
            } catch (final AnnotationTypeMismatchException e) {
                //do not abort annotation processing on annotation processing here or we end up with irreparable use of constants...
                if (!(context instanceof BeanModelContext)) {
                    throw e;
                }
            }
        }
    }

    @Override
    public HideBeanPathElement getHideElement() {
        return hideElement;
    }

    @Override
    public DisableBeanPathElement getDisableElement() {
        return disableElement;
    }

    @Override
    public ContainerTitleBeanPathElement getContainerTitleElement() {
        return containerTitleElement;
    }

    @Override
    public TitleBeanPathElement getTitleElement() {
        return titleElement;
    }

    @Override
    public TooltipBeanPathElement getTooltipElement() {
        return tooltipElement;
    }

    @Override
    public InterceptedBeanPathElement getInterceptedElement() {
        return interceptedElement;
    }

    @Override
    public final ABeanPathContext getContext() {
        return context;
    }

    @Override
    public IBeanPathAccessor getAccessor() {
        return accessor;
    }

    @Override
    public IBeanPathContainer getContainer() {
        return container;
    }

    @Override
    public final String getBeanPath() {
        return beanPath;
    }

    @Override
    public final String getTypePath() {
        return PathUtil.newTypePath(getContainer(), getAccessor());
    }

    @SafeVarargs
    @Override
    public final boolean accept(final IBeanPathVisitor... visitors) {
        try {
            final ABeanPathElement interceptor = getContext().getElementRegistry().getInterceptor(getBeanPath());
            if (interceptor != null) {
                //accept the interceptor instead of this element
                interceptor.interceptedElement = new InterceptedBeanPathElement(this);
                return interceptor.accept(visitors);
            } else {
                if (redirect != null && getInterceptedElement() == null) {
                    /*
                     * ignore if this is an interceptor that has not been attached yet, it might suffice to have this
                     * interceptor in the element registry
                     */
                    return false;
                } else {
                    //this is either an interceptor that was attached or a normal element
                    //redirect to hidden visitor if necessary
                    Hidden hidden = getAccessor().getAnnotation(Hidden.class);
                    if (this instanceof HiddenBeanPathElement) {
                        if (hidden != null && hidden.skip()) {
                            return false;
                        } else {
                            hidden = null;
                        }
                    }
                    if (hidden != null) {
                        if (!hidden.skip()) {
                            org.assertj.core.api.Assertions.assertThat(new HiddenBeanPathElement(this).accept(visitors))
                                    .isTrue();
                        }
                        return false;
                    } else {
                        //add normal element, interceptions are automatically used
                        if (shouldBeAddedToElementRegistry()) {
                            getContext().getElementRegistry().addElement(this);
                        }
                        //invoke normal visitors
                        if (firstAccept) {
                            beforeFirstAccept();
                        }
                        for (final IBeanPathVisitor visitor : visitors) {
                            innerAccept(visitor);
                        }
                        if (firstAccept) {
                            firstAccept = false;
                            afterFirstAccept(visitors);
                        }
                        return true;
                    }
                }
            }
        } catch (final Throwable t) {
            throw new RuntimeException("On: " + getBeanPath(), t);
        }
    }

    /**
     * Can be overriden to do additional things before first accept.
     */
    protected void beforeFirstAccept() {
        if (shouldBeAddedToElementRegistry()) {
            this.hideElement = getContext().getElementRegistry().getHideUtilityElementFor(this);
            this.disableElement = getContext().getElementRegistry().getDisableUtilityElementFor(this);
            if (shouldAttachToContainerTitleElement()) {
                this.containerTitleElement = getContext().getElementRegistry().getContainerTitleUtilityElementFor(this);
            }
            this.titleElement = getContext().getElementRegistry().getTitleUtilityElementFor(this);
            this.tooltipElement = getContext().getElementRegistry().getTooltipUtilityElementFor(this);
        }
    }

    /**
     * Can be overriden to do additional things after first accept.
     */
    protected void afterFirstAccept(final IBeanPathVisitor... visitors) {}

    protected boolean shouldAttachToContainerTitleElement() {
        return true;
    }

    /**
     * can be overriden to change this for specific cases
     */
    @Override
    public boolean shouldBeAddedToElementRegistry() {
        return true;
    }

    protected abstract void innerAccept(final IBeanPathVisitor visitor);

    @Override
    public final String toString() {
        return new ToStringBuilder(this).append("beanPath", getBeanPath()).append("typePath", getTypePath()).toString();
    }

    @Override
    public String getTitle() {
        if (getAccessor() instanceof BeanObjectAccessor) {
            final BeanObjectAccessor objAccessor = (BeanObjectAccessor) getAccessor();
            return getTitle(objAccessor.getContainer().getObject());
        } else {
            return getTitle(null);
        }
    }

    /**
     * Passing null here results in static title.
     */
    @Override
    public final String getTitle(final Object target) {
        //1. title property on property
        if (getTitleElement() != null && getTitleElement().isInvokerAvailable() && target != null) {
            final Object returnValue = getTitleElement().getInvoker().invokeFromTarget(target);
            final String title = BeanPathStrings.asString(returnValue);
            if (BeanPathStrings.isNotBlank(title)) {
                return title;
            }
        }
        //2. title annotation on property
        final Title titlePropertyAnnotation = getAccessor().getAnnotation(Title.class);
        if (titlePropertyAnnotation != null) {
            org.assertj.core.api.Assertions.assertThat(BeanPathStrings.isNotBlank(titlePropertyAnnotation.value()))
                    .as("@%s value on property [%s] must not be blank!", Title.class.getSimpleName(), getBeanPath())
                    .isTrue();
            return titlePropertyAnnotation.value();
        }
        //3. title action on type
        if (getContainerTitleElement() != null && getContainerTitleElement().isInvokerAvailable() && target != null) {
            final Object returnValue = getContainerTitleElement().getInvoker().invokeFromTarget(target);
            final String title = BeanPathStrings.asString(returnValue);
            if (BeanPathStrings.isNotBlank(title)) {
                return title;
            }
        }
        //4. title annotation on type
        final Title titleTypeAnnotation = getAccessor().getType().getAnnotation(Title.class);
        if (titleTypeAnnotation != null) {
            org.assertj.core.api.Assertions.assertThat(BeanPathStrings.isNotBlank(titleTypeAnnotation.value()))
                    .as("@%s value on type [%s:%s] must not be blank!", Title.class.getSimpleName(), getBeanPath(),
                            getAccessor().getType().getSimpleName())
                    .isTrue();
            return titleTypeAnnotation.value();
        }
        final String beanPathFragment = getAccessor().getBeanPathFragment();
        if (BeanPathStrings.isBlank(beanPathFragment)) {
            return BeanPathObjects.toVisibleName(getContainer().getType().getSimpleName());
        } else {
            return BeanPathObjects.toVisibleName(beanPathFragment);
        }
    }

    @Override
    public boolean isVisible(final Object target) {
        if (target == null) {
            //hiddenElement overrides this method properly, otherwise we cannot invoke hideElement without a target
            return true;
        }
        //since we only invoke methods here and are not interested in annotation, simply check BeanObjectContainer before the loop
        if (getContainer() instanceof BeanObjectContainer) {
            IBeanPathElement parent = this;
            Object parentTarget = target;
            while (parent != null) {
                final HideBeanPathElement hideAction = parent.getHideElement();
                if (hideAction != null) {
                    final Object hideResult = hideAction.getInvoker().invokeFromTarget(parentTarget);
                    if (hideResult != null) {
                        if (BeanPathReflections.isBoolean(hideResult.getClass())) {
                            return !(Boolean) hideResult;
                        } else {
                            return false;
                        }
                    }
                }
                parent = parent.getParentElement();
                if (parent != null && getContainer() instanceof BeanObjectContainer) {
                    final BeanObjectContainer parentContainer = (BeanObjectContainer) parent.getContainer();
                    parentTarget = parentContainer.getObject();
                } else {
                    parentTarget = null;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isEnabled(final Object target) {
        if (target == null) {
            //null is always disabled
            return false;
        }
        //no setter is disabled
        if (isStaticallyDisabled()) {
            return false;
        }
        if (!isVisible(target)) {
            return false;
        }
        IBeanPathElement parent = this;
        Object parentTarget = target;
        while (parent != null) {
            if (parent.getAccessor().getAnnotation(Disabled.class) != null) {
                return false;
            }
            final DisableBeanPathElement disableAction = parent.getDisableElement();
            if (disableAction != null && disableAction.isInvokerAvailable()) {
                final Object disableResult = disableAction.getInvoker().invokeFromTarget(parentTarget);
                if (disableResult != null) {
                    if (BeanPathReflections.isBoolean(disableResult.getClass())) {
                        final Boolean disabled = (Boolean) disableResult;
                        return !disabled;
                    } else {
                        return false;
                    }
                }
            }
            parent = parent.getParentElement();
            //check bean object container here so that annotations in hierarchy get processed properly
            if (parent != null && getContainer() instanceof BeanObjectContainer) {
                final BeanObjectContainer parentContainer = (BeanObjectContainer) parent.getContainer();
                parentTarget = parentContainer.getObject();
            } else {
                parentTarget = null;
            }
        }
        return true;
    }

    protected boolean isStaticallyDisabled() {
        return false;
    }

    @Override
    public String getTooltip(final Object target) {
        try {
            IBeanPathElement parent = this;
            Object parentTarget = target;
            while (parent != null) {
                final String tooltip = getDisabledMessageFromParent(parent, parentTarget);
                if (tooltip != null) {
                    return tooltip;
                }
                parent = parent.getParentElement();
                if (parent != null && getContainer() instanceof BeanObjectContainer) {
                    final BeanObjectContainer parentContainer = (BeanObjectContainer) parent.getContainer();
                    parentTarget = parentContainer.getObject();
                } else {
                    parentTarget = null;
                }
            }
            //5. check tooltip property
            if (getTooltipElement() != null && getTooltipElement().isInvokerAvailable()) {
                final Object returnValue = getTooltipElement().getInvoker().invoke();
                final String tooltip = BeanPathStrings.asString(returnValue);
                if (BeanPathStrings.isNotBlank(tooltip)) {
                    return tooltip;
                }
            }
            //6. check tooltip annotation
            final Tooltip tooltipAnnotation = getAccessor().getAnnotation(Tooltip.class);
            if (tooltipAnnotation != null) {
                org.assertj.core.api.Assertions.assertThat(BeanPathStrings.isNotBlank(tooltipAnnotation.value()))
                        .as("@%s value should not be null!", Tooltip.class.getSimpleName())
                        .isTrue();
                return tooltipAnnotation.value();
            }
            return null;
        } catch (final Throwable t) {
            throw new RuntimeException("On: " + getBeanPath(), t);
        }
    }

    private String getDisabledMessageFromParent(final IBeanPathElement parent, final Object parentTarget) {
        //1. check disabled annotation in parents
        final Disabled disabledAnnotation = parent.getAccessor().getAnnotation(Disabled.class);
        if (disabledAnnotation != null && BeanPathStrings.isNotBlank(disabledAnnotation.value())) {
            return disabledAnnotation.value();
        }
        //2. check disabled action in parents
        final DisableBeanPathElement disableAction = parent.getDisableElement();
        if (disableAction != null && disableAction.isInvokerAvailable()) {
            final Object disableResult = disableAction.getInvoker().invokeFromTarget(parentTarget);
            if (disableResult != null) {
                if (!BeanPathReflections.isBoolean(disableResult.getClass())
                        && BeanPathStrings.isNotBlank(disableResult.toString())) {
                    return disableResult.toString();
                }
            }
        }
        //4. check hide action in parents
        final HideBeanPathElement hideAction = parent.getHideElement();
        if (hideAction != null && hideAction.isInvokerAvailable()) {
            final Object hideResult = hideAction.getInvoker().invokeFromTarget(parentTarget);
            if (hideResult != null) {
                if (!BeanPathReflections.isBoolean(hideResult.getClass())
                        && BeanPathStrings.isNotBlank(hideResult.toString())) {
                    return hideResult.toString();
                }
            }
        }
        return null;
    }

    @Override
    public IBeanPathElement getParentElement() {
        return getContext().getElementRegistry().getElement(getContainer().getBeanPath());
    }

}
