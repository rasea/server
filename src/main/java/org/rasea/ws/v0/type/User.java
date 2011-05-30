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
 * License along with this library; if not, see <http://gnu.org/licenses>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.rasea.ws.v0.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://rasea.org/types", name = "user", propOrder = { "username", "displayName", "email", "alternateEmail" })
public class User {

	protected String displayName;

	protected String email;

	protected String alternateEmail;

	protected String username;

	public User() {
	}

	public User(final String username) {
		this.username = username;
	}

	public String getAlternateEmail() {
		return this.alternateEmail;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public String getEmail() {
		return this.email;
	}

	public String getUsername() {
		return this.username;
	}

	public void setAlternateEmail(final String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public void setDisplayName(final String value) {
		this.displayName = value;
	}

	public void setEmail(final String value) {
		this.email = value;
	}

	public void setUsername(final String value) {
		this.username = value;
	}

}
