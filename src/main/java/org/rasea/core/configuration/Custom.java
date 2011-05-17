package org.rasea.core.configuration;

import java.io.Serializable;

import org.rasea.core.annotation.Property;

public class Custom implements Serializable {

	private static final long serialVersionUID = -2060972386322016997L;

	@Property(key = "custom.class")
	private String clazz;

	public String getClazz() {
		return this.clazz;
	}

	public void setClazz(final String clazz) {
		this.clazz = clazz;
	}

}
