package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.extensions.entity.User;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "user", "password" })
public class UserRequest {
	
	@XmlElement(required = true)
	private User user;
	
	@XmlElement(required = true)
	private String password;
	
	public String getPassword() {
		return this.password;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setPassword(final String password) {
		this.password = password;
	}
	
	public void setUser(final User user) {
		this.user = user;
	}
}
