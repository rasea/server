package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.ws.v1.type.UserType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "user", "password" })
public class UserRequest {

	@XmlElement(required = true)
	private UserType user;

	@XmlElement(required = true)
	private String password;

	public String getPassword() {
		return this.password;
	}

	public UserType getUser() {
		return this.user;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setUser(final UserType user) {
		this.user = user;
	}
}
