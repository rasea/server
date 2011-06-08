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

	@Id
	@Length(max = 255)
	@Column(nullable = false, insertable = true)
	private String id;

	@Length(max = 255)
	@Column(nullable = true, insertable = false)
	private String name;

	@Email
	@Length(max = 255)
	@Column(nullable = false, unique = true, insertable = true)
	@Index(name = "idx_user_email")
	private String email;

	@Length(max = 100)
	@Column(nullable = false, insertable = true, updatable = false)
	private String password;

	@Column(nullable = false, insertable = true, updatable = false)
	private Date creation;

	@Column(nullable = true, insertable = false, updatable = false)
	private Date activation;

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
