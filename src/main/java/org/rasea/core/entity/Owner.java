package org.rasea.core.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "R_APPLICATION_ASSIGNMENT")
@IdClass(OwnerPk.class)
public class Owner {
	
	@Id
	private String username;
	
	@Id
	private Application application;
	
	public Owner() {
		super();
	}
	
	public Owner(final String username, final Application application) {
		this.username = username;
		this.application = application;
	}
	
	public Application getApplication() {
		return this.application;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setApplication(final Application application) {
		this.application = application;
	}
	
	public void setUsername(final String username) {
		this.username = username;
	}
	
}
