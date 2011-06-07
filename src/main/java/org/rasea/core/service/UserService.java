package org.rasea.core.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.rasea.core.domain.User;
import org.rasea.core.manager.UserManager;

import br.gov.frameworkdemoiselle.transaction.Transactional;

public class UserService implements Serializable {

	private static final long serialVersionUID = 2097626878174583664L;

	@Inject
	private UserManager manager;

	@Transactional
	public void insert(User user) {
		manager.insert(user);
	}
}
