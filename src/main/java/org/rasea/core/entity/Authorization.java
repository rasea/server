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
 * License along with this library; if not, see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.rasea.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Index;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "R_PERMISSION_ASSIGNMENT")
@IdClass(AuthorizationPk.class)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "role", "permission", "allowed" })
public class Authorization { // NOPMD by cleverson on 05/12/09 15:22

	@Id
	@XmlIDREF
	@XmlElement(name = "roleName", required = true)
	private Role role;

	@Id
	@XmlElement(required = true, nillable = false)
	private Permission permission;

	@NotNull
	@Column(name = "ALLOWED")
	@Index(name = "IDX_AUTHORIZATION_ENABLED")
	@XmlElement(required = true)
	private boolean allowed;

	public Authorization() {
		super();
	}

	public Authorization(final Permission permission, final Role role, final boolean allowed) {
		this.permission = permission;
		this.role = role;
		this.allowed = allowed;
	}

	@Override
	public boolean equals(final Object obj) { // NOPMD by cleverson on 05/12/09
		// 15:22
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Authorization other = (Authorization) obj;
		if (this.permission == null) {
			if (other.permission != null) {
				return false;
			}
		} else if (!this.permission.equals(other.permission)) {
			return false;
		}
		if (this.role == null) {
			if (other.role != null) {
				return false;
			}
		} else if (!this.role.equals(other.role)) {
			return false;
		}
		return true;
	}

	public Permission getPermission() {
		return this.permission;
	}

	public Role getRole() {
		return this.role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1; // NOPMD by cleverson on 05/12/09 15:22
		result = prime * result + (this.permission == null ? 0 : this.permission.hashCode());
		result = prime * result + (this.role == null ? 0 : this.role.hashCode());
		return result;
	}

	public boolean isAllowed() {
		return this.allowed;
	}

	public void setAllowed(final boolean allowed) {
		this.allowed = allowed;
	}

	public void setPermission(final Permission permission) {
		this.permission = permission;
	}

	public void setRole(final Role role) {
		this.role = role;
	}
}
