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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Persistence;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.persistence.EntityManagerFactory;
import org.rasea.core.configuration.Settings;

@Name("entityManagerFactory")
public class SettingsEntityManagerFactory extends EntityManagerFactory {

	@Override
	protected javax.persistence.EntityManagerFactory createEntityManagerFactory() {
		final Settings settings = (Settings) Component.getInstance(Settings.class, true);
		final Map<String, String> properties = new HashMap<String, String>();

		if (settings.getDatabase().getDatasource() == null || settings.getDatabase().getDatasource().length() <= 0) {
			properties.put("hibernate.connection.driver_class", settings.getDatabase().getDriverClass());
			properties.put("hibernate.connection.url", settings.getDatabase().getUrl());
			properties.put("hibernate.connection.username", settings.getDatabase().getUsername());
			properties.put("hibernate.connection.password", settings.getDatabase().getPassword());
		} else {
			properties.put("hibernate.connection.datasource", settings.getDatabase().getDatasource());
			properties.put("jboss.entity.manager.factory.jndi.name", "entityManagerFactory");
		}

		if (settings.getDatabase().getSchema() != null) {
			properties.put("hibernate.default_schema", settings.getDatabase().getSchema());
		}

		if (settings.getDatabase().getCatalog() != null) {
			properties.put("hibernate.default_catalog", settings.getDatabase().getCatalog());
		}

		if (settings.getDatabase().getDdl() != null) {
			properties.put("hibernate.hbm2ddl.auto", settings.getDatabase().getDdl().toString().toLowerCase());
		}

		return Persistence.createEntityManagerFactory(this.getPersistenceUnitName(), properties);
	}

	@Override
	public String getPersistenceUnitName() {
		return "rasea";
	}
}
