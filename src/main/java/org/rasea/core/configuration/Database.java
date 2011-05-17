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
