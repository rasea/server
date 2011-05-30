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
package org.rasea.core.util;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.rasea.core.configuration.Settings;

@AutoCreate
@Name("settingsValidator")
@Scope(ScopeType.APPLICATION)
public class SettingsValidator implements Serializable {

	private static final long serialVersionUID = 4857734302021002487L;

	@In
	private Settings settings;

	public boolean isAdminMailValid() {
		boolean ret = true;

		if (this.settings.getAdmin().getEmail() == null || this.settings.getAdmin().getEmail().trim().length() == 0) {
			ret = false;
		}

		return ret;
	}

	public boolean isMailHostValid() {
		boolean ret = true;
		if (this.settings.getMail().getHost() == null || this.settings.getMail().getHost().trim().length() == 0) {
			ret = false;
		}

		return ret;
	}

	public boolean isMailPasswordValid() {
		boolean ret = true;
		if (this.settings.getMail().getUsername() != null && this.settings.getMail().getUsername().trim().length() > 0
				&& (this.settings.getMail().getPassword() == null || this.settings.getMail().getPassword().trim().length() == 0)) {
			ret = false;
		}
		return ret;
	}

	public boolean isMailUserNameValid() {
		boolean ret = true;

		if (this.settings.getMail().getPassword() != null && this.settings.getMail().getPassword().trim().length() > 0
				&& (this.settings.getMail().getUsername() == null || this.settings.getMail().getUsername().trim().length() == 0)) {
			ret = false;
		}

		return ret;
	}
}
