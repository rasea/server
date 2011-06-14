package org.rasea.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	private static final String EMAIL_EXPRESSION = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

	private static Validator instance;

	private Validator() {
	}

	public static synchronized Validator getInstance() {
		if (instance == null) {
			instance = new Validator();
		}

		return instance;
	}

	public boolean isValidEmailFormat(final String email) {
		final CharSequence inputStr = email;
		final Pattern pattern = Pattern.compile(EMAIL_EXPRESSION, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(inputStr);

		return matcher.matches();
	}

	public boolean isValidUsernameFormat(final String username) {
		return true;
	}

}
