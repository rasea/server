package org.rasea.ws.v1.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.ws.v1.type.RoleType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "roles" })
public class RolesResponse {

	@XmlElement(name = "role", required = true)
	private List<RoleType> roles;

	public List<RoleType> getRoles() {
		return this.roles;
	}

	public void setRoles(final List<RoleType> roles) {
		this.roles = roles;
	}
}
