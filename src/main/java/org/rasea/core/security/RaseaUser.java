package org.rasea.core.security;

import br.gov.frameworkdemoiselle.security.User;

public class RaseaUser implements User {

	private static final long serialVersionUID = 1L;

	private final String login;

	private final String name;

	public RaseaUser(String login, String name) {
		this.login = login;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getLogin() {
		return login;
	}
}
