package org.rasea.core.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClient;

public class AmazonSimpleEmailServiceAsyncProducer {

	@Inject
	private AWSCredentials credentials;

	@Produces
	@ApplicationScoped
	public AmazonSimpleEmailServiceAsync create() {
		return new AmazonSimpleEmailServiceAsyncClient(credentials);
	}
}
