package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.ws.v1.type.ApplicationType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "application" })
public class ApplicationRequest {

	@XmlElement(required = true)
	private ApplicationType application;

	public ApplicationType getApplication() {
		return this.application;
	}

	public void setApplication(final ApplicationType application) {
		this.application = application;
	}
}
