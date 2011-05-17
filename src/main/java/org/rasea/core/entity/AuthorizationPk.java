package org.rasea.core.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Embeddable
public class AuthorizationPk implements Serializable { // NOPMD by cleverson on
	// 05/12/09 15:24
	
	private static final long serialVersionUID = -2805461532424422535L;
	
	@ManyToOne
	@JoinColumns( {
			@JoinColumn(name = "OPERATION_ID", referencedColumnName = "OPERATION_ID", nullable = false),
			@JoinColumn(name = "RESOURCE_ID", referencedColumnName = "RESOURCE_ID", nullable = false) })
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ForeignKey(name = "FK_AUTHORIZATION_PERMISSION")
	private Permission permission;
	
	@ManyToOne
	@JoinColumn(name = "ROLE_ID", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ForeignKey(name = "FK_AUTHORIZATION_ROLE")
	private Role role;
	
	@Override
	public boolean equals(final Object obj) { // NOPMD by cleverson on 05/12/09
		// 15:24
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AuthorizationPk)) {
			return false;
		}
		final AuthorizationPk other = (AuthorizationPk) obj;
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
		int result = 1; // NOPMD by cleverson on 05/12/09 15:24
		result = prime * result + (this.permission == null ? 0 : this.permission.hashCode());
		result = prime * result + (this.role == null ? 0 : this.role.hashCode());
		return result;
	}
	
	public void setPermission(final Permission permission) {
		this.permission = permission;
	}
	
	public void setRole(final Role role) {
		this.role = role;
	}
}
