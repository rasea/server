package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "applicationName", "username", "resourceName" })
public class UserResourceRequest {
	
	@XmlElement(required = true)
	private String applicationName;
	
	@XmlElement(required = true)
	private String username;
	
	@XmlElement(required = true)
	private String resourceName;
	
	public String getApplicationName() {
		return this.applicationName;
	}
	
	public String getResourceName() {
		return this.resourceName;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}
	
	public void setResourceName(final String resourceName) {
		this.resourceName = resourceName;
	}
	
	public void setUsername(final String username) {
		this.username = username;
	}
}
