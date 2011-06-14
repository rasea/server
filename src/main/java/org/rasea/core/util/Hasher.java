package org.rasea.core.util;

import java.io.Serializable;
import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

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
}
