package org.rasea.core.manager;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.rasea.core.util.AmazonAccountUtil;
import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.util.Strings;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

public class SimpleDBProducer implements Serializable {

	private static final long serialVersionUID = 2196757803109604415L;

	@Inject
	private Logger logger;

	@Inject
	private AmazonAccountUtil util;

	@Produces
	@ApplicationScoped
	public AmazonSimpleDB create() throws IOException {
		final String accessKey = util.getAccessKey();
		final String secretKey = util.getSecretKey();

		validateNotNull(accessKey, AmazonAccountUtil.ACCESS_KEY_VARIABLE_NAME);
		validateNotNull(secretKey, AmazonAccountUtil.SECRET_KEY_VARIABLE_NAME);

		final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		return new AmazonSimpleDBClient(credentials);
	}

	private void validateNotNull(String keyValue, String variableName) {
		if (Strings.isEmpty(keyValue)) {
			
			String message = "A variável de ambiente " + variableName
					+ " precisa ser definida. No Linux, inclua isto no arquivo ~/.profile do usuário: export "
					+ variableName + "=<valor>";

			logger.error(message);

			// TODO Criar uma ConfigurationException e lançá-la ao invés de RuntimeException. Ela pode até herdar de
			// RuntimeException se for o caso.
			throw new RuntimeException(message);
		}
	}
}
