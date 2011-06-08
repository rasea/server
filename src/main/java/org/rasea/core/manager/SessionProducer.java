package org.rasea.core.manager;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SessionProducer implements Serializable {

	private static final long serialVersionUID = -3833616294107605897L;

	@Inject
	private SessionFactory factory;

	@Produces
	@RequestScoped
	public Session create() {
		return factory.openSession();
	}

	public void destroy(@Disposes Session session) {
		if (session.isOpen()) {
			session.close();
		}
	}
}
