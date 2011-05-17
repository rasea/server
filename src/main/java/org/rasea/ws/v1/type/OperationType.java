package org.rasea.ws.v1.type;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Operation;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "displayName" })
public final class OperationType {

	@XmlElement(required = true)
	private String name;

	@XmlElement(required = true)
	private String displayName;

	public Operation parse() {
		Operation operation = new Operation();
		operation.setName(name);
		operation.setDisplayName(displayName);

		return operation;
	}

	public static OperationType parse(Operation operation) {
		OperationType type = new OperationType();
		type.setName(operation.getName());
		type.setDisplayName(operation.getDisplayName());

		return type;
	}

	public static List<OperationType> parse(List<Operation> operations) {
		List<OperationType> types = new ArrayList<OperationType>();

		for (Operation operation : operations) {
			types.add(OperationType.parse(operation));
		}

		return types;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
