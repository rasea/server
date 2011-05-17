package org.rasea.ws.v1.type;

import java.io.Serializable;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Index;
import org.hibernate.validator.NotNull;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "role", propOrder = { "name", "displayName", "enabled" })
public final class RoleType implements Serializable {

	@XmlElement(required = true)
	private String name;

	@XmlElement(required = true)
	private String displayName;

	@NotNull
	@Column(name = "ENABLED")
	@Index(name = "IDX_ROLE_ENABLED")
	@XmlElement(required = true)
	private boolean enabled;

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
