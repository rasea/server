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
package org.rasea.core.configuration;

import java.io.Serializable;

import org.rasea.core.annotation.Property;

public class Mail implements Serializable {

	private static final long serialVersionUID = 4368026386101392366L;

	@Property(key = "mail.host")
	private String host;

	@Property(key = "mail.tls", defaultValue = "false")
	private boolean tls;

	@Property(key = "mail.port")
	private Integer port;

	@Property(key = "mail.username")
	private String username;

	@Property(key = "mail.password")
	private String password;

	public String getHost() {
		return this.host;
	}

	public String getPassword() {
		return this.password;
	}

	public Integer getPort() {
		return this.port;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isTls() {
		return this.tls;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setPort(final Integer port) {
		this.port = port;
	}

	public void setTls(final boolean tls) {
		this.tls = tls;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
}
