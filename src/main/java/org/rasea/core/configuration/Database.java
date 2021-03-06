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

import org.rasea.core.annotation.Property;

public class Database implements Serializable {

	private static final long serialVersionUID = -8581609598210431052L;

	@Property(key = "database.driverClass", defaultValue = "org.hsqldb.jdbcDriver")
	private String driverClass;

	@Property(key = "database.url", defaultValue = "jdbc:hsqldb:rasea-database")
	private String url;

	@Property(key = "database.username", defaultValue = "sa")
	private String username;

	@Property(key = "database.password")
	private String password;

	@Property(key = "database.datasource")
	private String datasource;

	@Property(key = "database.schema")
	private String schema;

	@Property(key = "database.catalog")
	private String catalog;

	@Property(key = "database.ddl", defaultValue = "update")
	private DdlType ddl;

	public String getCatalog() {
		return this.catalog;
	}

	public String getDatasource() {
		return this.datasource;
	}

	public DdlType getDdl() {
		return this.ddl;
	}

	public String getDriverClass() {
		return this.driverClass;
	}

	public String getPassword() {
		return this.password;
	}

	public String getSchema() {
		return this.schema;
	}

	public String getUrl() {
		return this.url;
	}

	public String getUsername() {
		return this.username;
	}

	public void setCatalog(final String catalog) {
		this.catalog = catalog;
	}

	public void setDatasource(final String transactionLookupClass) {
		this.datasource = transactionLookupClass;
	}

	public void setDdl(final DdlType ddl) {
		this.ddl = ddl;
	}

	public void setDriverClass(final String driverClass) {
		this.driverClass = driverClass;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setSchema(final String schema) {
		this.schema = schema;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

}
