package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Permission;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "applicationName", "permission", "roleName", "allowed" })
public class GrantPermissionRequest {
	
	@XmlElement(required = true)
	private String applicationName;
	
	@XmlElement(required = true)
	private Permission permission;
	
	@XmlElement(required = true)
	private String roleName;
	
	@XmlElement(required = true)
	private boolean allowed;
	
	public String getApplicationName() {
		return this.applicationName;
	}
	
	public Permission getPermission() {
		return this.permission;
	}
	
	public String getRoleName() {
		return this.roleName;
	}
	
	public boolean isAllowed() {
		return this.allowed;
	}
	
	public void setAllowed(final boolean allowed) {
		this.allowed = allowed;
	}
	
	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}
	
	public void setPermission(final Permission permission) {
		this.permission = permission;
	}
	
	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}
	
}
