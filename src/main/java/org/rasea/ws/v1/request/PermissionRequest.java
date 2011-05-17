package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Permission;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "applicationName", "permission" })
public class PermissionRequest {

	@XmlElement(required = true)
	private String applicationName;

	@XmlElement(required = true)
	private Permission permission;

	public String getApplicationName() {
		return this.applicationName;
	}

	public Permission getPermission() {
		return this.permission;
	}

	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}

	public void setPermission(final Permission permission) {
		this.permission = permission;
	}

}
