package org.rasea.core.domain;

import java.io.Serializable;

import org.rasea.core.annotation.Attribute;
import org.rasea.core.annotation.Domain;
import org.rasea.core.annotation.ItemName;

@Domain(name = "USERS")
public class User implements Serializable {

	private static final long serialVersionUID = 1345893142166701824L;

	@ItemName
	private String login;

	/**
	 * Nome completo do usuário para exibição.
	 */
	private String name;

	/**
	 * Endereço eletrônico do usuário. Não é permitido o cadastro de e-mails duplicados.
	 */
	@Attribute(unique = true)
	private String email;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", name=" + name + ", email=" + email + "]";
	}

}
