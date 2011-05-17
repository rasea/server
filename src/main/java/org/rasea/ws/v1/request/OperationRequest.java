package org.rasea.ws.v1.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.ws.v1.type.OperationType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "applicationName", "operation" })
public class OperationRequest {

	@XmlElement(required = true)
	private String applicationName;

	@XmlElement(required = true)
	private OperationType operation;

	public String getApplicationName() {
		return this.applicationName;
	}

	public OperationType getOperation() {
		return this.operation;
	}

	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}

	public void setOperation(final OperationType operation) {
		this.operation = operation;
	}

}
