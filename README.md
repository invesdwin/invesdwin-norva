# invesdwin-norva
norva stands for **N**aked **O**bjects **R**eflection **V**isitor **A**PI

A unified visitor pattern implementation for processing Objects, Classes and javax.model via reflection. Allowing simpler creation of code generators or UI binding frameworks following the principles of the naked objects pattern.

## Maven Repository

Releases and snapshots are deployed to this maven repository:
```
http://invesdwin.de:8081/artifactory/invesdwin-oss
```

## Sample Code Generators
There are three sample annotation processors included that generate code by using this api:

### Static Facade
```
de.invesdwin.norva.apt.staticfacade.internal.StaticFacadeDefinitionAnnotationProcessor
```
- this one can be used to extend static utility classes that are final or to combine multiple utility classes into one for simpler access by faking polymorphism
- use the `@StaticFacadeDefinition` annotation to enable this generator

### Constants
```
de.invesdwin.norva.apt.constants.internal.ConstantsAnnotationProcessor
```
- this one generates XyzConstants with bean path constants like "some.path.inner" for complex beans
- use the `@BeanPathRoot` to enable this generator

### Build Version
```
de.invesdwin.norva.apt.buildversion.internal.BuildVersionDefinitionAnnotationProcessor
```
- this one generates a class with a timestamp denoting the time of the build
- use the `@BuildVersionDefinition` to enable this generator
