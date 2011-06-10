package org.rasea.core.domain;

public class User implements br.gov.frameworkdemoiselle.security.User {

	private static final long serialVersionUID = -1260271078329207417L;

	private String username;

	private String photoUrl;

	public User(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Override
	public String toString() {
		return getUsername();
	}
}
