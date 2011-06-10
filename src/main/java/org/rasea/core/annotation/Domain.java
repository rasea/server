package org.rasea.core.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * TODO: anotação específica para o SimpleDB: nome do domínio (~column family)
 */
@Inherited
@Retention(RUNTIME)
@Target({ TYPE })
public @interface Domain {

	String value() default "";

}
