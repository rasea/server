package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.ws.v1.type.PermissionType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "applicationName", "permission", "roleName" })
public class AuthorizationRequest {

	@XmlElement(required = true)
	private String applicationName;

	@XmlElement(required = true)
	private PermissionType permission;

	@XmlElement(required = true)
	private String roleName;

	public String getApplicationName() {
		return this.applicationName;
	}

	public PermissionType getPermission() {
		return this.permission;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}

	public void setPermission(final PermissionType permission) {
		this.permission = permission;
	}

	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}

}
