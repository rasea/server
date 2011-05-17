package org.rasea.ws.v0.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://rasea.org/types", name = "role", propOrder = { "name", "description" })
public class Role {
	
	protected String name;
	
	protected String description;
	
	public String getDescription() {
		return this.description;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setDescription(final String value) {
		this.description = value;
	}
	
	public void setName(final String value) {
		this.name = value;
	}
	
}
