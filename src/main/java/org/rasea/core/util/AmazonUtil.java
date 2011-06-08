package org.rasea.core.util;

import java.io.Serializable;

public class AmazonUtil implements Serializable {

	private static final long serialVersionUID = -986995975452932253L;

	public String getAccessKey() {
		return System.getenv(getAccessKeyVariableName());
	}

	public String getAccessKeyVariableName() {
		return "AWS_ACCESS_KEY";
	}

	public String getSecretKey() {
		return System.getenv(getSecretKeyVariableName());
	}

	public String getSecretKeyVariableName() {
		return "AWS_SECRET_KEY";
	}
}
