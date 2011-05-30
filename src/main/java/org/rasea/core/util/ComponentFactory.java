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
package org.rasea.core.util;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Credentials;
import org.rasea.core.configuration.ConfigurationLoader;
import org.rasea.core.configuration.Settings;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.ConfigurationException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.service.ApplicationService;
import org.rasea.extensions.entity.User;

@Name("org.rasea.core.util.factory")
public class ComponentFactory {

	@In
	private ApplicationService applicationService;

	@Factory(value = "currentApplication", scope = ScopeType.SESSION, autoCreate = true)
	public Application initCurrentApplication() throws ConfigurationException {
		Application currentApplication = new Application(Long.valueOf(-99999999));

		final Credentials credentials = (Credentials) Component.getInstance(Credentials.class);

		try {
			final User owner = new User(credentials.getUsername());
			final List<Application> ownerships = this.applicationService.find(owner);

			if (ownerships != null && !ownerships.isEmpty()) {
				currentApplication = ownerships.get(0);
			}

		} catch (final RequiredException cause) {
			throw new ConfigurationException("Unable to determine the current application", cause);
		}

		return currentApplication;
	}

	@Factory(value = "defaultApplication", scope = ScopeType.APPLICATION, autoCreate = true)
	public Application initDefaultApplication() {
		return new Application();
	}

	@Factory(value = "defaultRole", scope = ScopeType.APPLICATION, autoCreate = true)
	public Role initDefaultRole() {
		return new Role();
	}

	@Factory(value = "ownerships", scope = ScopeType.SESSION, autoCreate = true)
	public List<Application> initOwnerships() throws RequiredException {
		final Credentials credentials = (Credentials) Component.getInstance(Credentials.class);

		final User owner = new User(credentials.getUsername());
		return this.applicationService.find(owner);
	}

	@Factory(value = "settings", scope = ScopeType.APPLICATION, autoCreate = true)
	public Settings initSettings() throws ConfigurationException {
		final Properties metadata = new Properties();

		try {
			final URL url = Thread.currentThread().getContextClassLoader().getResource(Constants.METADATA_PROPERTY_FILE_NAME);
			metadata.load(url.openStream());

		} catch (final IOException cause) {
			throw new ConfigurationException("Unable to load project metadata", cause);
		}

		final ConfigurationLoader loader = new ConfigurationLoader(Constants.CONFIGURATION_PROPERTY_FILE_NAME);

		final Settings settings = new Settings();
		loader.load(settings);
		settings.getApplication().setVersion(metadata.getProperty("application.version"));

		return settings;
	}
}
