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
