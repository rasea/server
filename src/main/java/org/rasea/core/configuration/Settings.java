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
package org.rasea.core.configuration;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@AutoCreate
@Name("settings")
@Scope(ScopeType.APPLICATION)
public class Settings implements Serializable {

	private static final long serialVersionUID = 757536937757755925L;

	private Application application;

	private Admin admin;

	private Mail mail;

	private Database database;

	private Store store;

	private Ldap ldap;

	private Custom custom;

	public Admin getAdmin() {
		return this.admin;
	}

	public Application getApplication() {
		return this.application;
	}

	public Custom getCustom() {
		return this.custom;
	}

	public Database getDatabase() {
		return this.database;
	}

	public Ldap getLdap() {
		return this.ldap;
	}

	public Mail getMail() {
		return this.mail;
	}

	public Store getStore() {
		return this.store;
	}

	public void setAdmin(final Admin admin) {
		this.admin = admin;
	}

	public void setApplication(final Application application) {
		this.application = application;
	}

	public void setCustom(final Custom custom) {
		this.custom = custom;
	}

	public void setDatabase(final Database database) {
		this.database = database;
	}

	public void setLdap(final Ldap ldap) {
		this.ldap = ldap;
	}

	public void setMail(final Mail mail) {
		this.mail = mail;
	}

	public void setStore(final Store store) {
		this.store = store;
	}
}
