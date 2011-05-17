package org.rasea.ws.v1.type;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Permission;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "operationName", "resourceName" })
public class PermissionType {

	@XmlElement(required = true)
	private String operationName;

	@XmlElement(required = true)
	private String resourceName;

	public static PermissionType parse(Permission permission) {
		PermissionType type = new PermissionType();
		type.setResourceName(permission.getResource().getName());
		type.setOperationName(permission.getOperation().getName());

		return type;
	}

	public static List<PermissionType> parse(List<Permission> permissions) {
		List<PermissionType> types = new ArrayList<PermissionType>();

		for (Permission permission : permissions) {
			types.add(PermissionType.parse(permission));
		}

		return types;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
}
