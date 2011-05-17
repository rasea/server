package org.rasea.ws.v0.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://rasea.org/types", name = "application", propOrder = { "name",
		"description" })
public class Application {
	
	protected String description;
	
	protected String name;
	
	public String getDescription() {
		return this.description;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
	public void setName(final String value) {
		this.name = value;
	}
}
