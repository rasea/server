package org.rasea.ws.v1.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Resource;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "displayName" })
public class ResourceType {

	@XmlElement(required = true)
	private String name;

	@XmlElement(required = true)
	private String displayName;

	public Resource parse() {
		Resource resource = new Resource();
		resource.setName(name);
		resource.setDisplayName(displayName);

		return resource;
	}

	public static ResourceType parse(Resource operation) {
		ResourceType type = new ResourceType();
		type.setName(operation.getName());
		type.setDisplayName(operation.getDisplayName());

		return type;
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
