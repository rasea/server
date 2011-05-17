package org.rasea.core.configuration;

import java.io.Serializable;

import org.rasea.core.annotation.Property;

public class Ldap implements Serializable {

	private static final long serialVersionUID = 2192120669601834400L;

	@Property(key = "ldap.server.protocol", defaultValue = "ldap")
	private ProtocolType serverProtocol;

	@Property(key = "ldap.server.host")
	private String serverHost;

	@Property(key = "ldap.server.port")
	private Integer serverPort;

	@Property(key = "ldap.bind.dn")
	private String bindDN;

	@Property(key = "ldap.bind.password")
	private String bindPassword;

	@Property(key = "ldap.user.search-context-dn")
	private String userSearchContextDN;

	@Property(key = "ldap.user.search-filter")
	private String userSearchFilter;

	@Property(key = "ldap.user.search-subtree", defaultValue = "false")
	private boolean userSearchSubtree;

	@Property(key = "ldap.user.insert-pattern-dn")
	private String userInsertPatternDN;

	@Property(key = "ldap.user.name-attribute", defaultValue = "sAMAccountName")
	private String userNameAttribute;

	@Property(key = "ldap.user.displayName-attribute", defaultValue = "givenName")
	private String userDisplayNameAttribute;

	@Property(key = "ldap.user.mail-attribute")
	private String userMailAttrinbute;

	@Property(key = "ldap.user.alternateMail-attribute")
	private String userAlternateMailAttrinbute;

	@Property(key = "ldap.user.password-attribute", defaultValue = "userPassword")
	private String userPasswordAttribute;

	@Property(key = "ldap.user.enabled-attribute", defaultValue = "userAccountControl")
	private String userEnabledAttribute;

	@Property(key = "ldap.user.enabled-value", defaultValue = "544")
	private String userEnabledValue;

	@Property(key = "ldap.user.disabled-value", defaultValue = "546")
	private String userDisabledValue;

	@Property(key = "ldap.user.objectClass-attribute", defaultValue = "objectClass")
	private String userObjectClassAttribute;

	@Property(key = "ldap.user.object-classes", defaultValue = "top,person,organizationalPerson,user")
	private String userObjectClasses;

	@Property(key = "ldap.user.additional-valued-attributes")
	private String userAdditionalValuedAttributes;

	@Property(key = "ldap.user.password-charset")
	private String userPasswordCharset;

	public String getBindDN() {
		return this.bindDN;
	}

	public String getBindPassword() {
		return this.bindPassword;
	}

	public String getServerHost() {
		return this.serverHost;
	}

	public Integer getServerPort() {
		return this.serverPort;
	}

	public ProtocolType getServerProtocol() {
		return this.serverProtocol;
	}

	public String getUserAdditionalValuedAttributes() {
		return this.userAdditionalValuedAttributes;
	}

	public String getUserAlternateMailAttrinbute() {
		return this.userAlternateMailAttrinbute;
	}

	public String getUserDisabledValue() {
		return this.userDisabledValue;
	}

	public String getUserDisplayNameAttribute() {
		return this.userDisplayNameAttribute;
	}

	public String getUserEnabledAttribute() {
		return this.userEnabledAttribute;
	}

	public String getUserEnabledValue() {
		return this.userEnabledValue;
	}

	public String getUserInsertPatternDN() {
		return this.userInsertPatternDN;
	}

	public String getUserMailAttrinbute() {
		return this.userMailAttrinbute;
	}

	public String getUserNameAttribute() {
		return this.userNameAttribute;
	}

	public String getUserObjectClassAttribute() {
		return this.userObjectClassAttribute;
	}

	public String getUserObjectClasses() {
		return this.userObjectClasses;
	}

	public String getUserPasswordAttribute() {
		return this.userPasswordAttribute;
	}

	public String getUserPasswordCharset() {
		return this.userPasswordCharset;
	}

	public String getUserSearchContextDN() {
		return this.userSearchContextDN;
	}

	public String getUserSearchFilter() {
		return this.userSearchFilter;
	}

	public boolean isUserSearchSubtree() {
		return this.userSearchSubtree;
	}

	public void setBindDN(final String bindDN) {
		this.bindDN = bindDN;
	}

	public void setBindPassword(final String bindPassword) {
		this.bindPassword = bindPassword;
	}

	public void setServerHost(final String serverHost) {
		this.serverHost = serverHost;
	}

	public void setServerPort(final Integer serverPort) {
		this.serverPort = serverPort;
	}

	public void setServerProtocol(final ProtocolType serverProtocol) {
		this.serverProtocol = serverProtocol;
	}

	public void setUserAdditionalValuedAttributes(final String userAdditionalValuedAttributes) {
		this.userAdditionalValuedAttributes = userAdditionalValuedAttributes;
	}

	public void setUserAlternateMailAttrinbute(final String userAlternateMailAttrinbute) {
		this.userAlternateMailAttrinbute = userAlternateMailAttrinbute;
	}

	public void setUserDisabledValue(final String userDisabledValue) {
		this.userDisabledValue = userDisabledValue;
	}

	public void setUserDisplayNameAttribute(final String userDisplayNameAttribute) {
		this.userDisplayNameAttribute = userDisplayNameAttribute;
	}

	public void setUserEnabledAttribute(final String userEnabledAttribute) {
		this.userEnabledAttribute = userEnabledAttribute;
	}

	public void setUserEnabledValue(final String userEnabledValue) {
		this.userEnabledValue = userEnabledValue;
	}

	public void setUserInsertPatternDN(final String userInsertPatternDN) {
		this.userInsertPatternDN = userInsertPatternDN;
	}

	public void setUserMailAttrinbute(final String userMailAttrinbute) {
		this.userMailAttrinbute = userMailAttrinbute;
	}

	public void setUserNameAttribute(final String userNameAttribute) {
		this.userNameAttribute = userNameAttribute;
	}

	public void setUserObjectClassAttribute(final String userObjectClassAttribute) {
		this.userObjectClassAttribute = userObjectClassAttribute;
	}

	public void setUserObjectClasses(final String userObjectClasses) {
		this.userObjectClasses = userObjectClasses;
	}

	public void setUserPasswordAttribute(final String userPasswordAttribute) {
		this.userPasswordAttribute = userPasswordAttribute;
	}

	public void setUserPasswordCharset(final String userPasswordCharset) {
		this.userPasswordCharset = userPasswordCharset;
	}

	public void setUserSearchContextDN(final String userSearchContextDN) {
		this.userSearchContextDN = userSearchContextDN;
	}

	public void setUserSearchFilter(final String userSearchFilter) {
		this.userSearchFilter = userSearchFilter;
	}

	public void setUserSearchSubtree(final boolean userSearchSubtree) {
		this.userSearchSubtree = userSearchSubtree;
	}
}
