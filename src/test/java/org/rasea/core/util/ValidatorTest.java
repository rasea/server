package org.rasea.core.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidatorTest {

	private Validator validator = Validator.getInstance();

	@Test
	public void validEmail() {
		assertTrue(validator.isValidEmailFormat("abc@domain.com"));
		assertTrue(validator.isValidEmailFormat("abc.def@domain.com"));
		assertTrue(validator.isValidEmailFormat("abc@domain.com.br"));
		assertTrue(validator.isValidEmailFormat("abc.def@domain.com.br"));
	}

	@Test
	public void invalidEmail() {
		assertFalse(validator.isValidEmailFormat("abc.def.com"));
		assertFalse(validator.isValidEmailFormat("abc@def"));
		assertFalse(validator.isValidEmailFormat("abc def@domain"));
		assertFalse(validator.isValidEmailFormat("abc@def@ghi.com"));
		assertFalse(validator.isValidEmailFormat("abc"));
	}

	@Test
	public void nullEmail() {
	}

	@Test
	public void emptyEmail() {
	}
}
