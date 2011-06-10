package org.rasea.core.manager;

import static java.lang.String.format;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.rasea.core.annotation.Domain;

import br.gov.frameworkdemoiselle.util.Beans;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.util.DateUtils;

public abstract class AbstractSimpleDBManager<T> {

	private AmazonSimpleDB simpleDB;

	private String domain;

	// FIXME: variável não pode ser estática (vide documentação do DateUtils)
	public final DateUtils dateUtils = new DateUtils();

	@PostConstruct
	@SuppressWarnings("unused")
	private void init() {
	}

	protected AmazonSimpleDB getSimpleDB() {
		if (simpleDB == null) {
			simpleDB = Beans.getReference(AmazonSimpleDB.class);

			CreateDomainRequest request = new CreateDomainRequest(getDomainName());
			simpleDB.createDomain(request);
		}

		return simpleDB;
	}

	protected String getDomainName() {
		if (domain == null) {
			Domain annotation = getClass().getAnnotation(Domain.class);

			if (annotation != null) {
				domain = annotation.value();

			} else {
				String message = format("A classe %s deve ser anotada com @%s", getClass().getCanonicalName(),
						Domain.class.getSimpleName());
				throw new RuntimeException(message);
			}
		}

		return domain;
	}
	
	protected Date parseDateValue(final String value) {
		Date date = null;
		if (value != null && !"".equals(value)) {
			try {
				date = dateUtils.parseIso8601Date(value);
			} catch (ParseException e) {
			}
		}
		return date;
	}

}
