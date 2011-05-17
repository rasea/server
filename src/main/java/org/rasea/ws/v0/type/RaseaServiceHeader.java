package org.rasea.ws.v0.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://rasea.org/commons", name = "raseaServiceHeader", propOrder = {
		"username", "password" })
public class RaseaServiceHeader {
	private String password;
	
	private String username;
	
	public RaseaServiceHeader() {
	}
	
	public RaseaServiceHeader(final String username, final String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setPassword(final String password) {
		this.password = password;
	}
	
	public void setUsername(final String username) {
		this.username = username;
	}
}
