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

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class Hasher implements Serializable {

	private static final long serialVersionUID = 3670007311078805400L;

	private static final String ALGORITHM = "SHA-256";

	private static Hasher instance;

	private Hasher() {
	}

	public static synchronized Hasher getInstance() {
		if (instance == null) {
			instance = new Hasher();
		}

		return instance;
	}

	public String digest(final String literal, final String salt) {
		String hash = literal;

		if (literal != null) {
			try {
				MessageDigest md = MessageDigest.getInstance(ALGORITHM);
				BASE64Encoder enc = new BASE64Encoder();

				byte[] input = (literal + (salt == null ? "" : salt)).getBytes("UTF-8");
				hash = enc.encode(md.digest(input));

			} catch (Throwable cause) {
				if (cause instanceof RuntimeException) {
					throw (RuntimeException) cause;

				} else {
					throw new RuntimeException(cause);
				}
			}
		}

		return hash;
	}

	private static String hex(final byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i)
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		return sb.toString();
	}

	public static String md5(final String message) {
		if (message == null)
			return null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			// TODO: CP1252 mesmo?
			return hex(md.digest(message.getBytes("CP1252")));
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

}
