/*
 * Rasea Server
 * 
 * Copyright (c) 2008, Rasea <http://rasea.org>. All rights reserved.
 *
 * Rasea Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
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
