package org.rasea.ui.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.rasea.core.util.Validator;

import br.gov.frameworkdemoiselle.util.Strings;

public class ValidFormatValidator implements ConstraintValidator<ValidFormat, String> {

	private ValidFormat annotation;

	@Override
	public void initialize(final ValidFormat annotation) {
		this.annotation = annotation;
	}

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		if (!Strings.isEmpty(value)) {
			switch (annotation.type()) {
				case EMAIL:
					return Validator.getInstance().isValidEmailFormat(value);

				case USERNAME:
					return Validator.getInstance().isValidUsernameFormat(value);
			}
		}

		return true;
	}
}
