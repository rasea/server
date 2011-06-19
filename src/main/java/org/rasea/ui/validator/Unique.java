package org.rasea.ui.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target({ FIELD, METHOD })
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {

	FieldType type();

	Class<?>[] groups() default {};

	String message();

	Class<? extends Payload>[] payload() default {};
}
