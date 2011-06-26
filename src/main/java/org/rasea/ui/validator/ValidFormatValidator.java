/*
 * Rasea Server
 * 
 * Copyright (c) 2008, Rasea <http://rasea.org>. All rights reserved.
 *
 * Rasea Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see <http://gnu.org/licenses>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
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
