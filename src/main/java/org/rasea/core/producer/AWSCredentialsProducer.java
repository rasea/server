package org.rasea.core.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.util.Strings;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

public class AWSCredentialsProducer {

	/**
	 * System variable name for AWS Access Key.
	 */
	private static final String ACCESS_KEY_VARIABLE_NAME = "AWS_ACCESS_KEY";
	/**
	 * System variable name for AWS Secret Access Key.
	 */
	private static final String SECRET_KEY_VARIABLE_NAME = "AWS_SECRET_KEY";

	@Inject
	private Logger logger;

	@Produces
	@ApplicationScoped
	public AWSCredentials create() {
		final String accessKey = getAccessKey();
		final String secretKey = getSecretKey();

		validateNotNull(accessKey, ACCESS_KEY_VARIABLE_NAME);
		validateNotNull(secretKey, SECRET_KEY_VARIABLE_NAME);

		return new BasicAWSCredentials(accessKey, secretKey);
	}

	/**
	 * Returns Amazon WS Secret Access Key.
	 * 
	 * @return AWS Secret Access Key
	 */
	private String getSecretKey() {
		return System.getenv(SECRET_KEY_VARIABLE_NAME);
	}

	/**
	 * Returns Amazon WS Access Key ID.
	 * 
	 * @return AWS Access Key
	 */
	private String getAccessKey() {
		return System.getenv(ACCESS_KEY_VARIABLE_NAME);
	}

	private void validateNotNull(String keyValue, String variableName) {
		if (Strings.isEmpty(keyValue)) {

			String message = "A variável de ambiente " + variableName
					+ " precisa ser definida. No Linux, inclua isto no arquivo ~/.profile do usuário: export " + variableName + "=<valor>";

			logger.error(message);

			// TODO Criar uma ConfigurationException e lançá-la ao invés de RuntimeException. Ela pode até herdar de
			// RuntimeException se for o caso.
			throw new RuntimeException(message);
		}
	}

}
