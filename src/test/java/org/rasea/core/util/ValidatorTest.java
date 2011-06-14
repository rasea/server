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
		assertTrue(validator.isValidEmailFormat(null));
	}

	@Test
	public void emptyEmail() {
		assertTrue(validator.isValidEmailFormat(""));
	}
	
	@Test
	public void validUsername() {
		assertTrue(validator.isValidUsernameFormat("abc"));
		assertTrue(validator.isValidUsernameFormat("abcdef"));
		assertTrue(validator.isValidUsernameFormat("AbcDef"));
		assertTrue(validator.isValidUsernameFormat("abc123"));
	}

	@Test
	public void invalidUsername() {
		assertFalse(validator.isValidUsernameFormat("abc def"));
		assertFalse(validator.isValidUsernameFormat("abc@def"));
	}

	@Test
	public void nullUsername() {
		assertTrue(validator.isValidUsernameFormat(null));
	}

	@Test
	public void emptyUsername() {
		assertTrue(validator.isValidUsernameFormat(""));
	}
	
}
