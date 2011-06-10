package org.rasea.core.domain;

import java.io.Serializable;

public class User implements Serializable {

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

}
