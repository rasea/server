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
package org.rasea.core.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "R_PERMISSION")
@IdClass(PermissionPk.class)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "resource", "operation" })
@SuppressWarnings("serial")
public class Permission implements Serializable { // NOPMD by cleverson on
	// 05/12/09 15:20

	@Id
	@XmlIDREF
	@XmlElement(name = "operationName", required = true)
	private Operation operation;

	@Id
	@XmlIDREF
	@XmlElement(name = "resourceName", required = true)
	private Resource resource;

	public Permission() {
		super();
	}

	public Permission(final Resource resource, final Operation operation) {
		this.resource = resource;
		this.operation = operation;
	}

	@Override
	public boolean equals(final Object obj) { // NOPMD by cleverson on 05/12/09
		// 15:21
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Permission)) {
			return false;
		}
		final Permission other = (Permission) obj;
		if (this.operation == null) {
			if (other.operation != null) {
				return false;
			}
		} else if (!this.operation.equals(other.operation)) {
			return false;
		}
		if (this.resource == null) {
			if (other.resource != null) {
				return false;
			}
		} else if (!this.resource.equals(other.resource)) {
			return false;
		}
		return true;
	}

	public Operation getOperation() {
		return this.operation;
	}

	public Resource getResource() {
		return this.resource;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1; // NOPMD by cleverson on 05/12/09 15:21
		result = prime * result + (this.operation == null ? 0 : this.operation.hashCode());
		result = prime * result + (this.resource == null ? 0 : this.resource.hashCode());
		return result;
	}

	public void setOperation(final Operation operation) {
		this.operation = operation;
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}
}
