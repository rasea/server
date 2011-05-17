package org.rasea.core.configuration;

import java.io.Serializable;

import org.rasea.core.annotation.Property;

public class Admin implements Serializable {

	private static final long serialVersionUID = -6077319428783298816L;

	@Property(key = "admin.password", defaultValue = "rasea", profile = "conf")
	private String password;

	@Property(key = "admin.email", defaultValue = "no-reply@rasea.org")
	private String email;

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return "rasea";
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
