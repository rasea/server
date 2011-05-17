package org.rasea.ws.v1.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Application;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "displayName" })
public final class ApplicationType {

	@XmlElement(required = true)
	private String name;

	@XmlElement(required = true)
	private String displayName;

	public Application parse() {
		Application application = new Application();
		application.setName(name);
		application.setDisplayName(displayName);

		return application;
	}

	public static ApplicationType parse(Application application) {
		ApplicationType type = new ApplicationType();
		type.setName(application.getName());
		type.setDisplayName(application.getDisplayName());

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
