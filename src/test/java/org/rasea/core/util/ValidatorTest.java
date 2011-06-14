package org.rasea.core.util;

import static org.rasea.core.util.Validator.isValidEmail;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidatorTest {

	@Test
	public void testValidEmail() {
		assertTrue(isValidEmail("abc@domain.com"));
		assertTrue(isValidEmail("abc.def@domain.com"));
		assertTrue(isValidEmail("abc@domain.com.br"));
		assertTrue(isValidEmail("abc.def@domain.com.br"));
	}

	@Test
	public void testInvalidEmail() {
		assertFalse(isValidEmail("abc.def.com"));
		assertFalse(isValidEmail("abc@def"));
		assertFalse(isValidEmail("abc def@domain"));
		assertFalse(isValidEmail("abc@def@ghi.com"));
	}

}
