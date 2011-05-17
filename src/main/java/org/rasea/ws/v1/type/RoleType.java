package org.rasea.ws.v1.type;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.core.entity.Role;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "displayName", "enabled" })
public final class RoleType {

	@XmlElement(required = true)
	private String name;

	@XmlElement(required = true)
	private String displayName;

	@XmlElement(required = true)
	private boolean enabled;

	public Role parse() {
		Role role = new Role();
		role.setName(name);
		role.setDisplayName(displayName);
		role.setEnabled(enabled);

		return role;
	}

	public static RoleType parse(Role role) {
		RoleType type = new RoleType();
		type.setName(role.getName());
		type.setDisplayName(role.getDisplayName());
		type.setEnabled(role.isEnabled());

		return type;
	}

	public static List<RoleType> parse(List<Role> roles) {
		List<RoleType> types = new ArrayList<RoleType>();

		for (Role role : roles) {
			types.add(RoleType.parse(role));
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
