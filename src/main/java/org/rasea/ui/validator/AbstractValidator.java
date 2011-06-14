package org.rasea.ui.validator;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public abstract class AbstractValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

	@Override
	public void initialize(final A annotation) {
	}

	@Override
	public boolean isValid(final T value, final ConstraintValidatorContext context) {
		boolean result = true;
		
		if (value != null) {
			result = isValid(value);
		}

		return result;
	}

	protected abstract boolean isValid(final T value);
}
