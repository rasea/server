package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Application;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "application" })
public class ApplicationRequest {

	@XmlElement(required = true)
	private Application application;

	public Application getApplication() {
		return this.application;
	}

	public void setApplication(final Application application) {
		this.application = application;
	}
}
