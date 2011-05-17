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
