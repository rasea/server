package org.rasea.core.manager;

import java.io.Serializable;

import javax.inject.Inject;

import org.hibernate.Session;
import org.rasea.core.domain.User;

import br.gov.frameworkdemoiselle.transaction.Transactional;

public class UserManager implements Serializable {

	private static final long serialVersionUID = 2097626878174583664L;

	@Inject
	private Session session;

	@Transactional
	public void insert(User user) {
		session.persist(user);
	}
}
