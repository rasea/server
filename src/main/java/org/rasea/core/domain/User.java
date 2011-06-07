package org.rasea.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "RASEA_USER")
public class User implements Serializable {

	private static final long serialVersionUID = -5630651623043896485L;

	@Id
	@Length(max = 255)
	private String id;

	@Length(max = 255)
	private String name;

	@NotNull
	@Length(max = 255)
	@Column(unique = true)
	@Index(name = "idx_user_email")
	private String email;

	@Length(max = 100)
	@Column(nullable = false, updatable = false)
	private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
