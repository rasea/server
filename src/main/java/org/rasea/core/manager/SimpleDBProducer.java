package org.rasea.core.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

public class SimpleDBProducer implements Serializable {

	private static final long serialVersionUID = 2196757803109604415L;

	@Produces
	@ApplicationScoped
	public AmazonSimpleDB create() throws IOException {
		InputStream input = this.getClass().getResourceAsStream("AwsCredentials.properties");
		AmazonSimpleDB db = new AmazonSimpleDBClient(new PropertiesCredentials(input));

		return db;
	}
}
