package org.rasea.ws.v1.type;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.extensions.entity.User;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "displayName", "email", "alternateEmail", "enabled" })
public final class UserType {

	@XmlElement(required = true)
	private String name;

	@XmlElement(required = true)
	private String displayName;

	@XmlElement(required = false)
	private String email;

	@XmlElement(required = false)
	private String alternateEmail;

	@XmlElement(required = true)
	private boolean enabled;

	public User parse() {
		User role = new User();
		role.setName(name);
		role.setDisplayName(displayName);
		role.setEmail(email);
		role.setAlternateEmail(alternateEmail);
		role.setEnabled(enabled);

		return role;
	}

	public static UserType parse(User user) {
		UserType type = new UserType();
		type.setName(user.getName());
		type.setDisplayName(user.getDisplayName());
		type.setEmail(user.getEmail());
		type.setAlternateEmail(user.getAlternateEmail());
		type.setEnabled(user.isEnabled());

		return type;
	}

	public static List<UserType> parse(List<User> users) {
		List<UserType> types = new ArrayList<UserType>();

		for (User user : users) {
			types.add(UserType.parse(user));
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
