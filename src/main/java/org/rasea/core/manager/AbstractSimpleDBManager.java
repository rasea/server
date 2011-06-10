package org.rasea.core.manager;

import static java.lang.String.format;

import javax.annotation.PostConstruct;

import br.gov.frameworkdemoiselle.util.Beans;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;

public abstract class AbstractSimpleDBManager {

	private AmazonSimpleDB simpleDB;

	private String domain;

	@PostConstruct
	@SuppressWarnings("unused")
	private void init() {
	}

	protected AmazonSimpleDB getSimpleDB() {
		if (simpleDB == null) {
			simpleDB = Beans.getReference(AmazonSimpleDB.class);

			CreateDomainRequest request = new CreateDomainRequest(getDomain());
			simpleDB.createDomain(request);
		}

		return simpleDB;
	}

	protected String getDomain() {
		if (domain == null) {
			Domain annotation = getClass().getAnnotation(Domain.class);

			if (annotation != null) {
				domain = annotation.value();

			} else {
				String message = format("A classe %s deve ser anotada com @%s", getClass().getCanonicalName(), Domain.class.getSimpleName());
				throw new RuntimeException(message);
			}
		}

		return domain;
	}
}
