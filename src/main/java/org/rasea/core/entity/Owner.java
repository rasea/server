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
package org.rasea.core.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "R_APPLICATION_ASSIGNMENT")
@IdClass(OwnerPk.class)
public class Owner {

	@Id
	private String username;

	@Id
	private Application application;

	public Owner() {
		super();
	}

	public Owner(final String username, final Application application) {
		this.username = username;
		this.application = application;
	}

	public Application getApplication() {
		return this.application;
	}

	public String getUsername() {
		return this.username;
	}

	public void setApplication(final Application application) {
		this.application = application;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

}
