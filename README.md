# invesdwin-norva
norva stands for **N**aked **O**bjects **R**eflection **V**isitor **A**PI

A unified visitor pattern implementation for processing Objects, Classes and javax.model via reflection. Allowing simpler creation of code generators or UI binding frameworks following the principles of the naked objects pattern.

## Maven

Releases and snapshots are deployed to this maven repository:
```
http://invesdwin.de/artifactory/invesdwin-oss
```

Dependency declaration:
```xml
<dependency>
	<groupId>de.invesdwin</groupId>
	<artifactId>invesdwin-norva</artifactId>
	<version>1.0.0-SNAPSHOT</version>
</dependency>
```


## Sample Code Generators
There are three sample annotation processors included that generate code by using this api:

### Static Facade
```java
de.invesdwin.norva.apt.staticfacade.internal.StaticFacadeDefinitionAnnotationProcessor
```
- this one can be used to extend static utility classes that are final or to combine multiple utility classes into one for simpler access by faking polymorphism
- use the `@StaticFacadeDefinition` annotation to enable this generator
- Sample:
```java
@StaticFacadeDefinition(name = "de.invesdwin.common.lang.internal.AReflectionsStaticFacade", targets = {
        org.fest.reflect.core.Reflection.class, DynamicInstrumentationReflections.class, BeanPathReflections.class,
        org.springframework.core.GenericTypeResolver.class })
public final class Reflections extends AReflectionsStaticFacade {
```

You can see a few sample usages of this static facade pattern in the [invesdwin-util](https://github.com/subes/invesdwin-util) project.

### Constants
```java
de.invesdwin.norva.apt.constants.internal.ConstantsAnnotationProcessor
```
- this one generates XyzConstants with bean path constants like "some.path.inner" for complex beans
- use the `@BeanPathRoot` to enable this generator, you can use `@NoBeanPathRoot` to exclude classes again that extend a `@BeanPathRoot` annotated base class
- Sample:
```java
@BeanPathRoot
public abstract class AValueObject implements Serializable {
```

### Build Version
```java
de.invesdwin.norva.apt.buildversion.internal.BuildVersionDefinitionAnnotationProcessor
```
- this one generates a class with a timestamp denoting the time of the build
- use the `@BuildVersionDefinition` to enable this generator
- Sample:
```java
@BuildVersionDefinition(name = "de.invesdwin.common.system.internal.ABuildVersion")
public class BuildVersion extends ABuildVersion {
```

## Bean Paths and Naked Objects

Bean Path Elements can be either properties (text fields, tables, combo boxes, etc) or actions (buttons, links, etc). 

A bean path consists of elements separated by `.`, e.g. `some.path.doSomething`.
Here `some` is the bean path root container, having an accessor `SomeType getPath()` property method that returns a type that acts as a child container that has a `void doSomething()` action method.

Using this framework, you can easily understand bean paths and handle static and dynamic information contained in them.
They can be used to define models for generated UIs via the naked objects pattern. This framework does not do the UI generation part, instead it focuses on the reflection and basic functionality of a naked objects model and the processing of it. The actual naked objects framework can be built on top of this API, just like it is easy to create other code generators using this.

The element classes of this API provide methods for easily understanding a few annotations and utility methods and the hierarchy of when which one should override another. Also when processing objects you are able to utilize property modifiers and action invokers to ease interaction with the model.

### Bean Annotations

This framework handles the following annotations:

* `@ColumnOrder`: to define an order for properties and actions or effectively table columns, can also be used to hide columns that are not named in this annotation
* `@Disabled`: can be used to make an element disabled
* `@Hidden`: can be used to hide an element
* `@Intercept`: can be used to override bean paths of children, effectively changing the tree
* `@Tabbed`: can be used to create tabbed panels
* `@Title`: can be used to set a title text for this element
* `@Tooltip`: can be used to set a tooltip text for this element

The framework also understands `@NotNull` from the BeanValidation annotations and `@Column(nullable=false)` from the JPA annotations to determine if `null` is a valid value in choices for comboboxes.

### Utility Methods

For bean path elements you can also add utility methods for various dynamic decisions:

* `List<?> getXyzChoice()`: this can be used to define the choices a combo box has
* `List<String> columnOrder()`: this can be used to change the column order of table columns dynamically
* `String title()`: with this you can define a title text for a container
* `String disableXyz()`: with this you can dynamically disable elements, the return type can also be a boolean, when it is a string it denotes the reason why it is disabled (can be shown as a tooltip in the UI)
* `String hideXyz()`: just as the disable utility method, only that it hides elements
* `String xyzTitle()`: can be used to define a dynamic title for elements. The `get` prefix (`getXyzTitle()`) is only needed for properties, on actions you do not need it.
* `String xyzTooltip()`: just like the title utility method, only for tooltips. The `get` prefix (`getXyzTooltip()`) is only needed for properties, on actions you do not need it.
* `boolean validateXyz(Object newValue)`: can be used to write complex validations for input, e.g. when BeanValidation annotations are not enough
* `void removeFromXyz(Object removedValue)`: can be used as a column in a table that should remove an element in the model

### Bean Processors

The bean processors allow you to run through properties and actions of beans from various sources and execute the same visitors on them. So you build your code once and only need to switch the processor to execute it in a different environment. No need to learn different APIs, since the norva-API abstracts away the details, but makes them available for you when really needed (just cast the classes to their respective implementation to gain full access).

Here a sample to process a java class:

```java
    //create processing context
    final BeanClassContext context = new BeanClassContext(
            new BeanClassContainer(new BeanClassType(SomeClass.class)));
    //print out bean path info via PrintVisitor; or implement your own ASimpleBeanPathVisitor or ABeanPathVisitor
    new BeanClassProcessor(context, new PrintVisitor(context)).process();
    //lookup element
    final APropertyBeanPathElement beanPathElement = context.getElementRegistry()
            .getElement("some.bean.path.propertyElement");
    //gain more access
    final BeanClassAccessor accessor = (BeanClassAccessor) beanPathElement.getAccessor();
    final Method method = accessor.getPublicGetterMethod();
    final BeanClassType type = (BeanClassType) beanPathElement.getAccessor().getRawType();
    final Class<?> methodReturnType = type.getType();
```
    
The same sample processing a java object:

```java
    //create processing context
    final BeanObjectContext context = new BeanObjectContext(
            new BeanObjectContainer(new BeanObjectType(new SomeObject())));
    //print out bean path info via PrintVisitor; or implement your own ASimpleBeanPathVisitor or ABeanPathVisitor
    new BeanObjectProcessor(context, new PrintVisitor(context)).process();
    //lookup element
    final APropertyBeanPathElement beanPathElement = context.getElementRegistry()
            .getElement("some.bean.path.propertyElement");
    //gain more access
    final BeanObjectAccessor accessor = (BeanObjectAccessor) beanPathElement.getAccessor();
    final Method method = accessor.getBeanClassAccessor().getPublicGetterMethod();
    final BeanClassType type = (BeanClassType) beanPathElement.getAccessor().getRawType();
    final Class<?> methodReturnType = type.getType();
    //modify values (only supported when processing objects)
    Object value = beanPathElement.getModifier().getValue();
    beanPathElement.getModifier().setValue(new SomeValue());
```

And again the same sample processing a javax.model.Element:

```java
    public class SampleProcessor extends javax.annotation.processing.AbstractProcessor {

	    @Override
	    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
	        try {
	            final Set<? extends Element> elements = roundEnv.getRootElements();
	            for (final Element element : elements) {
	                if (element instanceof TypeElement) {
	                    final TypeElement typeElement = (TypeElement) element;
	                    //create processing context
	                    final BeanModelContainer rootContainer = new BeanModelContainer(
	                            new BeanModelType(processingEnv, typeElement.asType(), typeElement));
	                    final BeanModelContext context = new BeanModelContext(rootContainer, processingEnv);
	                    //print out bean path info via PrintVisitor; or implement your own ASimpleBeanPathVisitor or ABeanPathVisitor
	                    new BeanModelProcessor(context, new ConstantsGeneratorVisitor(context)).process();
	                    //lookup element
	                    final APropertyBeanPathElement beanPathElement = context.getElementRegistry()
	                            .getElement("some.bean.path.propertyElement");
	                    //gain more access
	                    final BeanModelAccessor accessor = (BeanModelAccessor) beanPathElement.getAccessor();
	                    final Element method = accessor.getPublicGetterMethodElement();
	                    final BeanModelType type = (BeanModelType) beanPathElement.getAccessor().getRawType();
	                    final TypeElement methodReturnType = type.getTypeElement();
	                }
	            }
	        } catch (final Throwable t) {
	            t.printStackTrace();
	        }
	        return false;
	    }

    }
```
