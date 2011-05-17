package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "username", "confirmationCode" })
public class ResetPasswordRequest {
	
	@XmlElement(required = true)
	private String username;
	
	@XmlElement(required = true)
	private String confirmationCode;
	
	public String getConfirmationCode() {
		return this.confirmationCode;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setConfirmationCode(final String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}
	
	public void setUsername(final String username) {
		this.username = username;
	}
}
