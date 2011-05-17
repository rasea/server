package org.rasea.ws.v1.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Operation;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "operations" })
public class OperationsResponse {
	
	@XmlElement(name = "operation", required = true)
	private List<Operation> operations;
	
	public List<Operation> getOperations() {
		return this.operations;
	}
	
	public void setOperations(final List<Operation> operations) {
		this.operations = operations;
	}
}
