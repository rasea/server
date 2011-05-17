package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Role;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "applicationName", "role" })
public class RoleRequest {
	
	@XmlElement(required = true)
	private String applicationName;
	
	@XmlElement(required = true)
	private Role role;
	
	public String getApplicationName() {
		return this.applicationName;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}
	
	public void setRole(final Role role) {
		this.role = role;
	}
}
