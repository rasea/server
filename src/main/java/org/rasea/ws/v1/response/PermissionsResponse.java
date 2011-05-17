package org.rasea.ws.v1.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.ws.v1.type.PermissionType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "permissions" })
public class PermissionsResponse {

	@XmlElement(name = "permission", required = true)
	private List<PermissionType> permissions;

	public List<PermissionType> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(final List<PermissionType> permissions) {
		this.permissions = permissions;
	}
}
