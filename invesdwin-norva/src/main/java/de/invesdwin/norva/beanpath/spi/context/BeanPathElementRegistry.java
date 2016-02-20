package de.invesdwin.norva.beanpath.spi.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import com.google.common.base.Function;

import de.invesdwin.norva.beanpath.BeanPathStrings;
import de.invesdwin.norva.beanpath.annotation.BeanPathRedirect;
import de.invesdwin.norva.beanpath.spi.PathUtil;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.ChoiceBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.ColumnOrderBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.ContainerTitleBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.DisableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.HideBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.IUtilityBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.RemoveFromBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.TitleBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.TooltipBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.ValidateBeanPathElement;

@NotThreadSafe
public class BeanPathElementRegistry {

    private final Map<String, IBeanPathElement> beanPath_element = new HashMap<String, IBeanPathElement>();
    private final Map<String, IBeanPathElement> beanPath_redirect = new HashMap<String, IBeanPathElement>();

    public void addElement(final IBeanPathElement e) {
        final IBeanPathElement oldElement = beanPath_element.put(e.getBeanPath(), e);
        if (oldElement instanceof IUtilityBeanPathElement) {
            final IUtilityBeanPathElement cOldElement = (IUtilityBeanPathElement) oldElement;
            org.assertj.core.api.Assertions.assertThat(cOldElement.isAttachedToElement()).isTrue();
        } else {
            org.assertj.core.api.Assertions.assertThat(oldElement)
            .as("Element %s [%s] cannot be added because another one already exists with that bean path.",
                    e.getClass().getSimpleName(), e.getBeanPath())
                    .isNull();
        }
    }

    public void addRedirector(final IBeanPathElement redirector, final BeanPathRedirect redirect) {
        final String beanPath = PathUtil.newBeanPath(redirector, redirect);
        //the first interceptor that is found is used, all others are ignored
        if (beanPath_redirect.get(beanPath) == null) {
            org.assertj.core.api.Assertions.assertThat(beanPath_redirect.put(beanPath, redirector)).isNull();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends IBeanPathElement> T getElement(final String beanPath) {
        final IBeanPathElement element = beanPath_element.get(beanPath);
        if (element != null) {
            return (T) element;
        } else {
            //try interceptor if the actual element does not exist to be able to find additional utility elements
            return getInterceptor(beanPath);
        }
    }

    /**
     * This is used internally by elements to redirect calls to the appropriate interceptor.
     */
    @SuppressWarnings("unchecked")
    public <T extends IBeanPathElement> T getInterceptor(final String beanPath) {
        return (T) beanPath_redirect.get(beanPath);
    }

    public Collection<IBeanPathElement> getElements() {
        return beanPath_element.values();
    }

    public HideBeanPathElement getHideUtilityElementFor(final IBeanPathElement element) {
        return getUtilityElementFor(element, new Function<IBeanPathElement, String>() {
            @Override
            public String apply(final IBeanPathElement input) {
                return addUtilityPrefix(input.getBeanPath(), HideBeanPathElement.HIDE_PREFIX);
            }
        });
    }

    public DisableBeanPathElement getDisableUtilityElementFor(final IBeanPathElement element) {
        return getUtilityElementFor(element, new Function<IBeanPathElement, String>() {
            @Override
            public String apply(final IBeanPathElement input) {
                return addUtilityPrefix(input.getBeanPath(), DisableBeanPathElement.DISABLE_PREFIX);
            }
        });
    }

    public ValidateBeanPathElement getValidateUtilityElementFor(final IBeanPathElement element) {
        return getUtilityElementFor(element, new Function<IBeanPathElement, String>() {
            @Override
            public String apply(final IBeanPathElement input) {
                return addUtilityPrefix(input.getBeanPath(), ValidateBeanPathElement.VALIDATE_PREFIX);
            }
        });
    }

    public ChoiceBeanPathElement getChoiceUtilityElementFor(final IBeanPathElement element) {
        return getUtilityElementFor(element, new Function<IBeanPathElement, String>() {
            @Override
            public String apply(final IBeanPathElement input) {
                return input.getBeanPath() + ChoiceBeanPathElement.CHOICE_SUFFIX;
            }
        });
    }

    public RemoveFromBeanPathElement getRemoveFromUtilityElementFor(final IBeanPathElement element) {
        return getUtilityElementFor(element, new Function<IBeanPathElement, String>() {
            @Override
            public String apply(final IBeanPathElement input) {
                return addUtilityPrefix(input.getBeanPath(), RemoveFromBeanPathElement.REMOVE_FROM_PREFIX);
            }
        });
    }

    public ContainerTitleBeanPathElement getContainerTitleUtilityElementFor(final IBeanPathElement element) {
        return getUtilityElementFor(element, new Function<IBeanPathElement, String>() {
            @Override
            public String apply(final IBeanPathElement input) {
                return addUtilityBeanPathFragment(input.getBeanPath(),
                        ContainerTitleBeanPathElement.CONTAINER_TITLE_BEAN_PATH_FRAGMENT);
            }
        });
    }

    public ColumnOrderBeanPathElement getColumnOrderUtilityElementFor(final IBeanPathElement element) {
        return getUtilityElementFor(element, new Function<IBeanPathElement, String>() {
            @Override
            public String apply(final IBeanPathElement input) {
                return addUtilityBeanPathFragment(input.getBeanPath(),
                        ColumnOrderBeanPathElement.COLUMN_ORDER_BEAN_PATH_FRAGMENT);
            }
        });
    }

    public TitleBeanPathElement getTitleUtilityElementFor(final IBeanPathElement element) {
        return getUtilityElementFor(element, new Function<IBeanPathElement, String>() {
            @Override
            public String apply(final IBeanPathElement input) {
                return input.getBeanPath() + TitleBeanPathElement.TITLE_SUFFIX;
            }
        });
    }

    public TooltipBeanPathElement getTooltipUtilityElementFor(final IBeanPathElement element) {
        return getUtilityElementFor(element, new Function<IBeanPathElement, String>() {
            @Override
            public String apply(final IBeanPathElement input) {
                return input.getBeanPath() + TooltipBeanPathElement.TOOLTIP_SUFFIX;
            }
        });
    }

    @SuppressWarnings("unchecked")
    private <T extends IUtilityBeanPathElement> T getUtilityElementFor(final IBeanPathElement element,
            final Function<IBeanPathElement, String> utilityBeanPathFunction) {
        final IBeanPathElement utilityElement = getElement(utilityBeanPathFunction.apply(element));
        //check if the interceptor itself might have the utility; or just use the one on the normal element if it exists
        if (utilityElement instanceof IUtilityBeanPathElement) {
            final IUtilityBeanPathElement cUtilityElement = (IUtilityBeanPathElement) utilityElement;
            cUtilityElement.setAttachedToElement(element);
            return (T) cUtilityElement;
        } else if (utilityElement == null && element.getInterceptedElement() != null) {
            //fallback to intercepted elements utility element
            return getUtilityElementFor(element.getInterceptedElement(), utilityBeanPathFunction);
        } else {
            return (T) null;
        }
    }

    private String addUtilityBeanPathFragment(final String beanPath, final String utilityBeanPathFragment) {
        if (BeanPathStrings.isNotBlank(beanPath)) {
            return beanPath + PathUtil.BEAN_PATH_SEPARATOR + utilityBeanPathFragment;
        } else {
            return utilityBeanPathFragment;
        }
    }

    private String addUtilityPrefix(final String beanPath, final String utilityPrefix) {
        if (beanPath.contains(PathUtil.BEAN_PATH_SEPARATOR)) {
            final String substringAfterLast = BeanPathStrings.substringAfterLast(beanPath, PathUtil.BEAN_PATH_SEPARATOR);
            final String substringBeforeLast = BeanPathStrings.substringBeforeLast(beanPath,
                    PathUtil.BEAN_PATH_SEPARATOR);
            return substringBeforeLast + PathUtil.BEAN_PATH_SEPARATOR + utilityPrefix
                    + BeanPathStrings.capitalize(substringAfterLast);
        } else {
            return utilityPrefix + BeanPathStrings.capitalize(beanPath);
        }
    }

}
