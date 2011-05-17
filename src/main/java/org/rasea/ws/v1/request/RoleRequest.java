package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.ws.v1.type.RoleType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "applicationName", "role" })
public class RoleRequest {

	@XmlElement(required = true)
	private String applicationName;

	@XmlElement(required = true)
	private RoleType role;

	public String getApplicationName() {
		return this.applicationName;
	}

	public RoleType getRole() {
		return this.role;
	}

	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}

	public void setRole(final RoleType role) {
		this.role = role;
	}
}
