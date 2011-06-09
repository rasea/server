package org.rasea.core.util;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

/**
 * Retrieves Amazon WS Access Key ID and Secret Access Key from system variables.
 * <br>
 * The given security credentials are needed to invoke additional services at AWS, such as SimpleDB.
 * 
 * @see http://aws.amazon.com/security-credentials
 */
@ApplicationScoped
public class AmazonAccountUtil implements Serializable {

	private static final long serialVersionUID = -986995975452932253L;

	/**
	 * System variable name for AWS Access Key.
	 */
	public static final String ACCESS_KEY_VARIABLE_NAME = "AWS_ACCESS_KEY";

	/**
	 * System variable name for AWS Secret Access Key.
	 */
	public static final String SECRET_KEY_VARIABLE_NAME = "AWS_SECRET_KEY";

	/**
	 * Returns Amazon WS Access Key ID.
	 * 
	 * @return AWS Access Key
	 */
	public String getAccessKey() {
		return System.getenv(ACCESS_KEY_VARIABLE_NAME);
	}

	/**
	 * Returns Amazon WS Secret Access Key.
	 * 
	 * @return AWS Secret Access Key
	 */
	public String getSecretKey() {
		return System.getenv(SECRET_KEY_VARIABLE_NAME);
	}

}
