package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Operation;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "applicationName", "operation" })
public class OperationRequest {

	@XmlElement(required = true)
	private String applicationName;

	@XmlElement(required = true)
	private Operation operation;

	public String getApplicationName() {
		return this.applicationName;
	}

	public Operation getOperation() {
		return this.operation;
	}

	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}

	public void setOperation(final Operation operation) {
		this.operation = operation;
	}

}
