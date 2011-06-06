package org.rasea.core.security;

import java.io.Serializable;

import javax.inject.Named;

@Named
public class Credentials implements Serializable {

	private static final long serialVersionUID = 633687017842755204L;

	private String username;

	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
