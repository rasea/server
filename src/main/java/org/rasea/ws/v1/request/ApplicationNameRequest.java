package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "applicationName" })
public class ApplicationNameRequest {
	
	@XmlElement(required = true)
	private String applicationName;
	
	public String getApplicationName() {
		return this.applicationName;
	}
	
	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}
}
