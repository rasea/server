package org.rasea.ws.v1.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Role;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "roles" })
public class RolesResponse {
	
	@XmlElement(name = "role", required = true)
	private List<Role> roles;
	
	public List<Role> getRoles() {
		return this.roles;
	}
	
	public void setRoles(final List<Role> roles) {
		this.roles = roles;
	}
}
