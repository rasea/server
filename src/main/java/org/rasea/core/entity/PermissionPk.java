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

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Embeddable
public class PermissionPk implements Serializable { // NOPMD by cleverson on
	// 05/12/09 15:21

	private static final long serialVersionUID = 5492121367306528968L;

	@ManyToOne
	@JoinColumn(name = "OPERATION_ID", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ForeignKey(name = "FK_PERMISSION_OPERATION")
	private Operation operation;

	@ManyToOne
	@JoinColumn(name = "RESOURCE_ID", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ForeignKey(name = "FK_PERMISSION_RESOURCE")
	private Resource resource;

	@Override
	public boolean equals(final Object obj) { // NOPMD by cleverson on 05/12/09
		// 15:21
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PermissionPk)) {
			return false;
		}
		final PermissionPk other = (PermissionPk) obj;
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
