package org.rasea.core.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * TODO: anotação específica para o SimpleDB: nome do item (~row key)
 */
@Inherited
@Retention(RUNTIME)
@Target({ FIELD })
public @interface ItemName {

}
