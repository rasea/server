package org.rasea.core.manager;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class SessionFactoryProducer implements Serializable {

	private static final long serialVersionUID = -3492215005447726620L;

	@Produces
	@ApplicationScoped
	public SessionFactory create() {
		Configuration cfg = new AnnotationConfiguration().configure();
		return cfg.buildSessionFactory();
	}

	public void destroy(@Disposes SessionFactory factory) {
		if (!factory.isClosed()) {
			factory.close();
		}
	}
}
