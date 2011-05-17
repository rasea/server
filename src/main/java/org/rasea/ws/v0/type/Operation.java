package org.rasea.ws.v0.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://rasea.org/types", name = "operation", propOrder = { "name" })
public class Operation {
	
	protected String name;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(final String value) {
		this.name = value;
	}
}
