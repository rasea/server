package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "username", "newPassword" })
public class NewPasswordRequest {
	
	@XmlElement(required = true)
	private String username;
	
	@XmlElement(required = true)
	private String newPassword;
	
	public String getNewPassword() {
		return this.newPassword;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}
	
	public void setUsername(final String username) {
		this.username = username;
	}
	
}
