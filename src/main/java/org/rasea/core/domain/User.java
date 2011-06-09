package org.rasea.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "RASEA_USER")
public class User implements Serializable {

	private static final long serialVersionUID = -5630651623043896485L;

	/**
	 * IdentificaÃ§Ã£o do usuÃ¡rio representada pelo seu username (login).
	 */
	@Id
	@Length(max = 255)
	@Column(nullable = false, insertable = true)
	private String login;

	/**
	 * Nome completo do usuÃ¡rio para exibiÃ§Ã£o.
	 */
	@Length(max = 255)
	@Column(nullable = true, insertable = false)
	private String name;

	/**
	 * EndereÃ§o eletrÃ´nico do usuÃ¡rio. NÃ£o Ã© permitido o cadastro de e-mails duplicados.
	 */
	@Email
	@Length(max = 255)
	@Column(nullable = false, unique = true, insertable = true)
	@Index(name = "idx_user_email")
	private String email;

	/**
	 * Senha do usuÃ¡rio para acessar sua aplicaÃ§Ã£o e o Rasea.
	 */
	@Length(max = 100)
	@Column(nullable = false, insertable = true, updatable = false)
	private String password;

	/**
	 * Data de criaÃ§Ã£o da conta.
	 */
	@Column(nullable = false, insertable = true, updatable = false)
	private Date creation;

	/**
	 * Data de ativaÃ§Ã£o da conta.
	 */
	@Column(nullable = true, insertable = false, updatable = false)
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

	@Override
	public String toString() {
		return "User [login=" + login + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", creation=" + creation + ", activation=" + activation + "]";
	}
	
}