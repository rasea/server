package org.rasea.core.configuration;

import java.io.Serializable;

import org.rasea.core.annotation.Property;

public class Application implements Serializable {

	private static final long serialVersionUID = -8581173789326212515L;

	@Property(key = "application.description", defaultValue = "Rasea")
	private String description;

	private String version;

	public String getDescription() {
		return this.description;
	}

	public String getVersion() {
		return this.version;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setVersion(final String version) {
		this.version = version;
	}
}
