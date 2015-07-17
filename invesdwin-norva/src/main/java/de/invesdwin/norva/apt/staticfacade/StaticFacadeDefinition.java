package de.invesdwin.norva.apt.staticfacade;

public @interface StaticFacadeDefinition {

    String name();

    Class<?>[] targets();

    String[] filterMethodSignatureExpressions() default {};

}
