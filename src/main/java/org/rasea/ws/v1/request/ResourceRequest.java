package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Resource;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "applicationName", "resource" })
public class ResourceRequest {

	@XmlElement(required = true)
	private String applicationName;

	@XmlElement(required = true)
	private Resource resource;

	public String getApplicationName() {
		return this.applicationName;
	}

	public Resource getResource() {
		return this.resource;
	}

	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

}
