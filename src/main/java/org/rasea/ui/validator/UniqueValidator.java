package org.rasea.ui.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.rasea.core.service.AccountService;

import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Strings;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

	private Unique annotation;

	@Override
	public void initialize(final Unique annotation) {
		this.annotation = annotation;
	}

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		if (!Strings.isEmpty(value)) {
			AccountService service = Beans.getReference(AccountService.class);

			switch (annotation.type()) {
				case EMAIL:
					return !service.containsEmail(value);

				case USERNAME:
					return !service.containsUsername(value);
			}

		}

		return true;
	}
}
