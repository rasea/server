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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Index;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.rasea.extensions.entity.User;

@Entity
@Table(name = "R_APPLICATION")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "application", propOrder = { "name", "displayName", "resources", "operations", "permissions", "roles", "authorizations" })
public final class Application implements Serializable {

	private static final long serialVersionUID = 4822220253268613599L;

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	@XmlTransient
	private Long id;

	@NotNull
	@Length(max = 100)
	@Column(name = "NAME", unique = true)
	@Index(name = "IDX_APPLICATION_NAME")
	@XmlID
	@XmlElement(required = true)
	private String name;

	@NotNull
	@Length(max = 255)
	@Column(name = "DESCRIPTION")
	@Index(name = "IDX_APPLICATION_DESCRIPTION")
	@XmlElement(required = true)
	private String displayName;

	@Transient
	@XmlTransient
	private List<User> owners;

	@Transient
	@XmlElement(name = "resource")
	@XmlElementWrapper(name = "resources")
	private List<Resource> resources;

	@Transient
	@XmlElement(name = "operation")
	@XmlElementWrapper(name = "operations")
	private List<Operation> operations;

	@Transient
	@XmlElement(name = "permission")
	@XmlElementWrapper(name = "permissions")
	private List<Permission> permissions;

	@Transient
	@XmlElement(name = "role")
	@XmlElementWrapper(name = "roles")
	private List<Role> roles;

	@Transient
	@XmlElement(name = "authorization")
	@XmlElementWrapper(name = "authorizations")
	private List<Authorization> authorizations;

	public Application() {
		super();
	}

	public Application(final Long id) {
		this.id = id;
	}

	public Application(final String name) {
		this.name = name;
	}

	public Application(final String name, final String displayName) {
		this(name);
		this.displayName = displayName;
	}

	public void addAuthorization(final Authorization authorization) {
		if (this.authorizations == null) {
			this.authorizations = new ArrayList<Authorization>();
		}

		this.authorizations.add(authorization);
	}

	public void addOperation(final Operation operation) {
		if (this.operations == null) {
			this.operations = new ArrayList<Operation>();
		}

		this.operations.add(operation);
	}

	public void addPermission(final Permission permission) {
		if (this.permissions == null) {
			this.permissions = new ArrayList<Permission>();
		}

		this.permissions.add(permission);
	}

	public void addResource(final Resource resource) {
		if (this.resources == null) {
			this.resources = new ArrayList<Resource>();
		}

		this.resources.add(resource);
	}

	public void addRole(final Role role) {
		if (this.roles == null) {
			this.roles = new ArrayList<Role>();
		}

		this.roles.add(role);
	}

	// @Override
	// public Application clone() {
	// Application applicarion = null;
	//
	// try {
	// applicarion = (Application) super.clone();
	// applicarion.setId(this.id);
	// applicarion.setName(this.name);
	// applicarion.setDisplayName(this.displayName);
	//
	// } catch (final CloneNotSupportedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return applicarion;
	// }

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Application other = (Application) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public List<Authorization> getAuthorizations() {
		return this.authorizations;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public List<Operation> getOperations() {
		return this.operations;
	}

	public List<User> getOwners() {
		return this.owners;
	}

	public List<Permission> getPermissions() {
		return this.permissions;
	}

	public List<Resource> getResources() {
		return this.resources;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	public void setAuthorizations(final List<Authorization> authorizations) {
		this.authorizations = authorizations;
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOperations(final List<Operation> operations) {
		this.operations = operations;
	}

	public void setOwners(final List<User> owners) {
		this.owners = owners;
	}

	public void setPermissions(final List<Permission> permissions) {
		this.permissions = permissions;
	}

	public void setResources(final List<Resource> resources) {
		this.resources = resources;
	}

	public void setRoles(final List<Role> roles) {
		this.roles = roles;
	}
}
