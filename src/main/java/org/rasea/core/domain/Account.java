package org.rasea.core.domain;

import java.io.Serializable;
import java.util.Date;

import org.rasea.core.annotation.Domain;
import org.rasea.core.annotation.ItemName;

@Domain(name = "ACCOUNTS")
public class Account implements Serializable {

	private static final long serialVersionUID = -5630651623043896485L;

	@ItemName
	private String username;

	private String email;

	private String photoUrl;

	private String password;

	private Date creationDate;

	private String activationCode;

	private Date activationDate;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Account [username=" + username + ", email=" + email + ", photoUrl=" + photoUrl + ", password="
				+ password + ", creationDate=" + creationDate + ", activationCode=" + activationCode
				+ ", activationDate=" + activationDate + "]";
	}
	
}
