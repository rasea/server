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
