package org.rasea.core.domain;

import java.io.Serializable;
import java.util.Date;

import org.rasea.core.annotation.Attribute;
import org.rasea.core.annotation.Domain;
import org.rasea.core.annotation.ItemName;

@Domain(name = "ACCOUNTS")
public class Account implements Serializable {

	private static final long serialVersionUID = -5630651623043896485L;

	/**
	 * Identificação do usuário representada pelo seu username (login).
	 */
	@ItemName
	private String login;

	/**
	 * Senha do usuário para acessar sua aplicação e o Rasea.
	 */
	private String password;

	/**
	 * Data de criação da conta.
	 */
	@Attribute(name = "cre_date")
	private Date creationDate;

	/**
	 * Data de ativação da conta.
	 */
	@Attribute(name = "act_date")
	private Date activationDate;

	/**
	 * Código para ativação da conta.
	 */
	@Attribute(name = "act_code")
	private String activationCode;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	@Override
	public String toString() {
		return "Account [login=" + login + ", password=" + password + ", creationDate=" + creationDate
				+ ", activationDate=" + activationDate + ", activationCode=" + activationCode + "]";
	}

}
