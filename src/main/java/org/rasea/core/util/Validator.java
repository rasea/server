package org.rasea.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	private static final String EMAIL_EXPRESSION = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

	private Validator() {
		super();
	}

	public static boolean isValidEmail(final String emailAddress) {
		final CharSequence inputStr = emailAddress;
		final Pattern pattern = Pattern.compile(EMAIL_EXPRESSION, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();
	}

}
