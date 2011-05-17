package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "applicationName", "roleName", "username" })
public class UserRoleRequest {
	
	@XmlElement(required = true)
	private String applicationName;
	
	@XmlElement(required = true)
	private String roleName;
	
	@XmlElement(required = true)
	private String username;
	
	public String getApplicationName() {
		return this.applicationName;
	}
	
	public String getRoleName() {
		return this.roleName;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}
	
	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}
	
	public void setUsername(final String username) {
		this.username = username;
	}
	
}
