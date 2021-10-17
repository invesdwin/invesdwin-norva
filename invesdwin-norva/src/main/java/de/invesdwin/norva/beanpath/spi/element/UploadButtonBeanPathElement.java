package de.invesdwin.norva.beanpath.spi.element;

import java.io.File;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;
import de.invesdwin.norva.beanpath.spi.element.simple.SimpleActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.accessor.ActionInvokerBeanClassAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.accessor.ActionInvokerBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.ChoiceBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.utility.ValidateBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class UploadButtonBeanPathElement extends AActionBeanPathElement implements IValidatableBeanPathElement {

    private ChoiceBeanPathPropertyModifier choiceModifier;
    private ValidateBeanPathElement validateElement;

    public UploadButtonBeanPathElement(final SimpleActionBeanPathElement simpleActionElement) {
        super(simpleActionElement);
    }

    @Override
    protected void beforeFirstAccept() {
        super.beforeFirstAccept();
        this.validateElement = getContext().getElementRegistry().getValidateUtilityElementFor(this);
    }

    @Override
    public ValidateBeanPathElement getValidateElement() {
        return validateElement;
    }

    @Override
    public BeanClassType getValidatableType() {
        return getInvoker().getBeanClassAccessor().getRawType();
    }

    @Override
    protected void innerAccept(final IBeanPathVisitor visitor) {
        visitor.visitUploadButton(this);
    }

    public boolean isMultiUpload() {
        return getAccessor().getRawType().isArray() || getAccessor().getRawType().isIterable();
    }

    public void setUploadedFiles(final List<File> files) {
        if (!isMultiUpload()) {
            if (files.size() > 0) {
                BeanPathAssertions.checkState(files.size() == 1, "MultiUpload is not supported!");
                setUploadedFile(files.get(0));
            } else {
                setUploadedFile(null);
            }
        } else {
            if (choiceModifier == null) {
                choiceModifier = new ChoiceBeanPathPropertyModifier(new ActionInvokerBeanObjectAccessor(getInvoker()),
                        null, false);
            }
            choiceModifier.setValue(files);
        }
    }

    public void setUploadedFilesFromTarget(final Object target, final List<File> files) {
        if (!isMultiUpload()) {
            if (files.size() > 0) {
                BeanPathAssertions.checkState(files.size() == 1, "MultiUpload is not supported!");
                setUploadedFileFromTarget(target, files.get(0));
            } else {
                setUploadedFileFromTarget(target, null);
            }
        } else {
            if (choiceModifier == null) {
                choiceModifier = new ChoiceBeanPathPropertyModifier(new ActionInvokerBeanObjectAccessor(getInvoker()),
                        null, false);
            }
            choiceModifier.setValueFromTarget(target, files);
        }
    }

    public void setUploadedFilesFromRoot(final Object root, final List<File> files) {
        if (!isMultiUpload()) {
            if (files.size() > 0) {
                BeanPathAssertions.checkState(files.size() == 1, "MultiUpload is not supported!");
                setUploadedFileFromRoot(root, files.get(0));
            } else {
                setUploadedFileFromRoot(root, null);
            }
        } else {
            if (choiceModifier == null) {
                choiceModifier = new ChoiceBeanPathPropertyModifier(new ActionInvokerBeanClassAccessor(getInvoker()),
                        null, false);
            }
            choiceModifier.setValueFromRoot(root, files);
        }
    }

    public void setUploadedFile(final File file) {
        getInvoker().invoke(file);
    }

    public void setUploadedFileFromTarget(final Object target, final File file) {
        getInvoker().invokeFromTarget(target, file);
    }

    public void setUploadedFileFromRoot(final Object root, final File file) {
        getInvoker().invokeFromRoot(root, file);
    }

}
