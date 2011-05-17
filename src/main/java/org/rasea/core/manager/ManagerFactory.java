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
