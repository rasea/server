package org.rasea.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.validator.Length;

@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "credentials", propOrder = { "username", "password" })
public class Credentials {
	
	@Id
	@Column(name = "USERNAME", nullable = false)
	@XmlElement(required = true)
	private String username;
	
	@Length(max = 100)
	@Column(name = "PASSWORD", insertable = false, updatable = false)
	@XmlElement(required = true)
	private String password;
	
	public String getPassword() {
		return this.password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setPassword(final String password) {
		this.password = password;
	}
	
	public void setUsername(final String username) {
		this.username = username;
	}
}
