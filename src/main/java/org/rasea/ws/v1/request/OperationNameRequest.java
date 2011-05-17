package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "applicationName", "operationName" })
public class OperationNameRequest {

	@XmlElement(required = true)
	private String applicationName;

	@XmlElement(required = true)
	private String operationName;

	public String getApplicationName() {
		return this.applicationName;
	}

	public String getOperationName() {
		return this.operationName;
	}

	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}

	public void setOperationName(final String operationName) {
		this.operationName = operationName;
	}

}
