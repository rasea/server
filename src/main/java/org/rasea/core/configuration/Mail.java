package org.rasea.core.configuration;

import java.io.Serializable;

import org.rasea.core.annotation.Property;

public class Mail implements Serializable {

	private static final long serialVersionUID = 4368026386101392366L;

	@Property(key = "mail.host")
	private String host;

	@Property(key = "mail.tls", defaultValue = "false")
	private boolean tls;

	@Property(key = "mail.port")
	private Integer port;

	@Property(key = "mail.username")
	private String username;

	@Property(key = "mail.password")
	private String password;

	public String getHost() {
		return this.host;
	}

	public String getPassword() {
		return this.password;
	}

	public Integer getPort() {
		return this.port;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isTls() {
		return this.tls;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setPort(final Integer port) {
		this.port = port;
	}

	public void setTls(final boolean tls) {
		this.tls = tls;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
}
