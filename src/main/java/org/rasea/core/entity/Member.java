package org.rasea.core.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "R_USER_ASSIGNMENT")
@IdClass(MemberPk.class)
public class Member {
	
	@Id
	private String username;
	
	@Id
	private Role role;
	
	public Member() {
		super();
	}
	
	public Member(final String username, final Role role) {
		this.username = username;
		this.role = role;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setRole(final Role role) {
		this.role = role;
	}
	
	public void setUsername(final String username) {
		this.username = username;
	}
	
}
