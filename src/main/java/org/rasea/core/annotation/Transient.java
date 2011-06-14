package org.rasea.core.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to indicate fields not to be considered during persistence.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { METHOD, FIELD } )
public @interface Transient {
	
}