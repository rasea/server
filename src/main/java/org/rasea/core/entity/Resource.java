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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "R_RESOURCE", uniqueConstraints = { @UniqueConstraint(columnNames = { "APPLICATION_ID", "NAME" }) })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resource", propOrder = { "name", "displayName" })
@SuppressWarnings("serial")
public class Resource implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	@XmlTransient
	private Long id;

	@NotNull
	@Length(max = 1024)
	@Column(name = "NAME")
	@XmlID
	@XmlElement(required = true)
	private String name;

	@NotNull
	@Length(max = 556)
	@Column(name = "DESCRIPTION")
	@Index(name = "IDX_RESOURCE_DESCRIPTION")
	@XmlElement(required = true)
	private String displayName;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "APPLICATION_ID")
	@ForeignKey(name = "FK_RESOURCE_APPLICATION")
	@XmlTransient
	private Application application;

	@Transient
	@XmlTransient
	private List<Operation> operations;

	public Resource() {
		super();
	}

	public Resource(final Application application) {
		this.application = application;
	}

	public Resource(final Application application, final String name) {
		this(application);
		this.name = name;
	}

	public Resource(final Application application, final String name, final String displayName) {
		this(application, name);
		this.displayName = displayName;
	}

	public Resource(final Long id) {
		this.id = id;
	}

	public void addOperation(final Operation operation) {
		if (this.operations == null) {
			this.operations = new ArrayList<Operation>();
		}

		this.operations.add(operation);
	}

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
		final Resource other = (Resource) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Application getApplication() {
		return this.application;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	public void setApplication(final Application application) {
		this.application = application;
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
}
