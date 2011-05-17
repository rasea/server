package org.rasea.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Embeddable
public class OwnerPk implements Serializable { // NOPMD by cleverson on 05/12/09
	// 15:22
	
	private static final long serialVersionUID = -18840240135129128L;
	
	@ManyToOne
	@JoinColumn(name = "APPLICATION_ID", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ForeignKey(name = "FK_OWNER_APPLICATION")
	@Index(name = "IDX_OWNER_APPLICATION")
	private Application application;
	
	@Index(name = "IDX_OWNER_USERNAME")
	@Column(name = "USERNAME", nullable = false)
	private String username;
	
	@Override
	public boolean equals(final Object obj) { // NOPMD by cleverson on 05/12/09
		// 15:22
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof OwnerPk)) {
			return false;
		}
		final OwnerPk other = (OwnerPk) obj;
		if (this.application == null) {
			if (other.application != null) {
				return false;
			}
		} else if (!this.application.equals(other.application)) {
			return false;
		}
		if (this.username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!this.username.equals(other.username)) {
			return false;
		}
		return true;
	}
	
	public Application getApplication() {
		return this.application;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1; // NOPMD by cleverson on 05/12/09 15:23
		result = prime * result + (this.application == null ? 0 : this.application.hashCode());
		result = prime * result + (this.username == null ? 0 : this.username.hashCode());
		return result;
	}
	
	public void setApplication(final Application application) {
		this.application = application;
	}
	
	public void setUsername(final String username) {
		this.username = username;
	}
}
