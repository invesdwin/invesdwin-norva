package de.invesdwin.norva.apt.staticfacade;

public @interface StaticFacadeDefinition {

    String name();

    Class<?>[] targets();

    String[] filterMethodSignatureExpressions() default {};

    /**
     * These are the signature like "com.google.common.primitives.Chars#compare(char, char)" which are generated as the
     * {@see} comment of the static facade.
     */
    String[] filterSeeMethodSignatures() default {};

}
