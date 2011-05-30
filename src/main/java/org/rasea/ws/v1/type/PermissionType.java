/*
 * Rasea Server
 * 
 * Copyright (c) 2008, Rasea <http://rasea.org>. All rights reserved.
 *
 * Rasea Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see <http://gnu.org/licenses>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
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
