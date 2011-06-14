package org.rasea.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	private static final String EMAIL_EXPRESSION = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	private static final String USER_EXPRESSION = "^[\\w]+$";

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
		if (email == null || email.length() == 0)
			return true;
		
		final CharSequence inputStr = email;
		final Pattern pattern = Pattern.compile(EMAIL_EXPRESSION, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(inputStr);

		return matcher.matches();
	}

	public boolean isValidUsernameFormat(final String username) {
		if (username == null || username.length() == 0)
			return true;
		
		final CharSequence inputStr = username;
		final Pattern pattern = Pattern.compile(USER_EXPRESSION, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(inputStr);
		
		return matcher.matches();
	}

}
