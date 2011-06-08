package org.rasea.core.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	private static final long serialVersionUID = -5630651623043896485L;

	/**
	 * Identificação do usuário representada pelo seu username (login).
	 */
	private String login;

	/**
	 * Nome completo do usuário para exibição.
	 */
	private String name;

	/**
	 * Endereço eletrônico do usuário. Não é permitido o cadastro de e-mails duplicados.
	 */
	private String email;

	/**
	 * Senha do usuário para acessar sua aplicação e o Rasea.
	 */
	private String password;

	/**
	 * Data de criação da conta.
	 */
	private Date creation;

	/**
	 * Data de ativação da conta.
	 */
	private Date activation;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getActivation() {
		return activation;
	}

	public void setActivation(Date activation) {
		this.activation = activation;
	}
}
