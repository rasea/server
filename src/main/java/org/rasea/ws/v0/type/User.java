package org.rasea.ws.v0.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://rasea.org/types", name = "user", propOrder = { "username",
		"displayName", "email", "alternateEmail" })
public class User {
	
	protected String displayName;
	
	protected String email;
	
	protected String alternateEmail;
	
	protected String username;
	
	public User() {
	}
	
	public User(final String username) {
		this.username = username;
	}
	
	public String getAlternateEmail() {
		return this.alternateEmail;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setAlternateEmail(final String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}
	
	public void setDisplayName(final String value) {
		this.displayName = value;
	}
	
	public void setEmail(final String value) {
		this.email = value;
	}
	
	public void setUsername(final String value) {
		this.username = value;
	}
	
}
