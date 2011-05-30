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
package org.rasea.core.manager;

import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.rasea.core.configuration.Settings;
import org.rasea.core.configuration.StoreType;
import org.rasea.extensions.manager.UserManager;

@Name("managerFactory")
public class ManagerFactory {

	@In
	private Settings settings;

	@SuppressWarnings("unchecked")
	@Factory(value = "userManager", autoCreate = true)
	public UserManager getuserManager() {
		UserManager userManager;
		final StoreType type = this.settings.getStore().getStoreType();

		if (StoreType.LDAP.equals(type)) {
			userManager = new LdapUserManager(this.settings.getLdap());

		} else if (StoreType.CUSTOM.equals(type)) {
			try {
				final Class<UserManager> clazz = (Class<UserManager>) Class.forName(this.settings.getCustom().getClazz());
				userManager = clazz.newInstance();

			} catch (final Exception cause) {
				userManager = null;
			}

		} else {
			userManager = new DefaultUserManager();
		}

		return userManager;
	}
}
