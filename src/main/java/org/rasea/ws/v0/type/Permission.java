package org.rasea.ws.v0.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://rasea.org/types", name = "permission", propOrder = { "resource",
		"operation" })
public class Permission {
	
	protected String operation;
	
	protected String resource;
	
	public String getOperation() {
		return this.operation;
	}
	
	public String getResource() {
		return this.resource;
	}
	
	public void setOperation(final String value) {
		this.operation = value;
	}
	
	public void setResource(final String value) {
		this.resource = value;
	}
	
}
