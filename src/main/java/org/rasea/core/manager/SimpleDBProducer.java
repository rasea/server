package org.rasea.core.manager;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.rasea.core.util.AmazonUtil;
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
	private AmazonUtil util;

	@Produces
	@ApplicationScoped
	public AmazonSimpleDB create() throws IOException {
		String accessKey = util.getAccessKey();
		String secretKey = util.getSecretKey();

		validateNotNull(accessKey, util.getAccessKeyVariableName());
		validateNotNull(secretKey, util.getSecretKeyVariableName());

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		return new AmazonSimpleDBClient(credentials);
	}

	private void validateNotNull(String keyValue, String variableName) {
		if (Strings.isEmpty(keyValue)) {
			String message = "A variável de ambiente " + variableName
					+ " deve ser definida no sitema. No Linux, edite o arquivo ~/.profile e inclua a isto: export "
					+ variableName + "=<valor>";

			logger.error(message);

			// TODO Criar uma ConfigurationException e lançá-la ao invés de RuntimeException. Ela pode até herdar de
			// RuntimeException se for o caso.
			throw new RuntimeException(message);
		}
	}
}
