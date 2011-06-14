package org.rasea.core.producer;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

public class AmazonSimpleDBProducer {

	@Inject
	private AWSCredentials credentials;

	@Produces
	@ApplicationScoped
	public AmazonSimpleDB create() throws IOException {
		return new AmazonSimpleDBClient(credentials);
	}
}
