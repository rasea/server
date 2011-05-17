package org.rasea.core.manager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.naming.OperationNotSupportedException;
import javax.naming.PartialResultException;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;

import org.rasea.core.configuration.Ldap;
import org.rasea.extensions.entity.User;
import org.rasea.extensions.manager.UserManager;
import org.rasea.extensions.manager.UserManagerException;

public class LdapUserManager implements UserManager {

	private static final long serialVersionUID = -1839966422874514403L;

	private final Ldap conf;

	public LdapUserManager(final Ldap conf) {
		this.conf = conf;
	}

	public boolean authenticate(final String username, final String password) throws UserManagerException {
		boolean authenticated = false;

		try {
			final SearchResult searchResult = this.searchByUsername(username);

			if (searchResult != null) {
				final String principal = searchResult.getNameInNamespace();

				final LdapContext ldap = this.createContext(principal, password);
				authenticated = true;

				ldap.close();
			}

		} catch (final NamingException cause) {
			authenticated = false;
			throw new UserManagerException(cause);

		} catch (final IOException cause) {
			authenticated = false;
			throw new UserManagerException(cause);
		}

		return authenticated;
	}

	public void changePassword(final String username, final String password) throws UserManagerException {
		try {
			final byte[] encodePassword = this.encodePassword(password);
			final String dn = this.searchByUsername(username).getNameInNamespace();
			final Attributes attrs = new BasicAttributes(true);

			for (final String attribute : this.conf.getUserPasswordAttribute().split(",")) {
				attrs.put(attribute, encodePassword);
			}

			final LdapContext ldap = this.createContext();
			ldap.modifyAttributes(dn, DirContext.REPLACE_ATTRIBUTE, attrs);
			ldap.close();

		} catch (final OperationNotSupportedException cause) {
			// TODO tratar erro lançando um erro de aplicação. Esssa excessão
			// acontece quando a conexao nao eh segura ou quando a senha é
			// fraca.
			cause.printStackTrace();

		} catch (final NamingException cause) {
			throw new UserManagerException(cause);

		} catch (final IOException cause) {
			throw new UserManagerException(cause);
		}
	}

	private LdapContext createContext() throws NamingException {
		return this.createContext(this.conf.getBindDN(), this.conf.getBindPassword());
	}

	private LdapContext createContext(final String username, final String password) throws NamingException {
		final Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, this.getUrl());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put(Context.REFERRAL, "follow");

		// if (this.conf.isServerSSL()) {
		// env.put(Context.SECURITY_PROTOCOL, "ssl");
		// }

		return new InitialLdapContext(env, null);
	}

	public void delete(final User user) throws UserManagerException {
		try {
			final SearchResult result = this.searchByUsername(user.getName());
			final String dn = result.getNameInNamespace();

			final LdapContext ldap = this.createContext();
			ldap.destroySubcontext(dn);
			ldap.close();

		} catch (final NamingException cause) {
			throw new UserManagerException(cause);

		} catch (final IOException cause) {
			throw new UserManagerException(cause);
		}
	}

	protected byte[] encodePassword(final String password) {
		byte[] encoded = null;

		if (password != null) {
			try {
				encoded = String.format("\"%s\"", password).getBytes(this.conf.getUserPasswordCharset());
			} catch (final UnsupportedEncodingException cause) {
				// TODO Tratar excecao e logar o erro como FATAL
				cause.printStackTrace();

				encoded = null;
			}
		}

		return encoded;
	}

	protected String evaluateExpression(final String expression, final User user) {
		String evaluated = expression.toString();

		evaluated = evaluated.replaceAll("\\{username\\}", user.getName());
		evaluated = evaluated.replaceAll("\\{displayName\\}", user.getDisplayName());
		evaluated = evaluated.replaceAll("\\{email\\}", user.getEmail());
		evaluated = evaluated.replaceAll("\\{mail\\}", user.getEmail());

		return evaluated;
	}

	public List<User> findAll() throws UserManagerException {
		return this.findByFilter(null);
	}

	public List<User> findByFilter(final String filter) throws UserManagerException {
		final List<User> result = new ArrayList<User>();

		try {
			final List<SearchResult> searchResult = this.searchByFilter(filter, true);

			for (final SearchResult item : searchResult) {
				result.add(this.parse(item.getAttributes()));
			}

		} catch (final SizeLimitExceededException cause) {
			// TODO Logar este erro como em nivel de WARN que
			// ocorre quando a busca retorna mais usuarios que o servidor ldap
			// suporta. Colocar a mensagem com base numa chave no
			// messages.properties.
			cause.printStackTrace();

		} catch (final PartialResultException cause) {
			// TODO Logar este erro como em nivel de WARN que
			// ocorre quando ocorre erro na leitura de um registro retornado na
			// busca do LDAP.
			cause.printStackTrace();

		} catch (final NamingException cause) {
			throw new UserManagerException(cause);

		} catch (final IOException cause) {
			throw new UserManagerException(cause);
		}

		return result;
	}

	private String getAttribute(final Attributes attrs, final String name) {
		String value = null;

		if (name != null && attrs.get(name) != null) {
			try {
				value = attrs.get(name).get().toString();
			} catch (final NamingException cause) {
				value = null;
			}
		}

		return value;
	}

	protected String getEnabledValue(final boolean enabled) {
		String result = null;
		final String values;

		if (enabled) {
			values = this.conf.getUserEnabledValue();
		} else {
			values = this.conf.getUserDisabledValue();
		}

		if (values != null) {
			for (final String value : values.split(",")) {
				result = value;
				break;
			}
		}

		return result;
	}

	protected boolean getEnabledValue(final String enabled) {
		boolean result = false;

		if (enabled != null) {
			final String enabledValues[] = this.conf.getUserEnabledValue().split(",");

			for (final String enabledValue : enabledValues) {
				if (enabled.equalsIgnoreCase(enabledValue)) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	protected String getFilterStatement(final String filter, final boolean fulltextSearch) {
		String[] attributes;
		final StringBuffer result = new StringBuffer();

		result.append("(&");

		attributes = this.conf.getUserObjectClassAttribute().split(",");
		for (final String attr : attributes) {

			final String[] objectClasses = this.conf.getUserObjectClasses().split(",");
			for (final String objectClass : objectClasses) {
				result.append("(");
				result.append(attr);
				result.append("=");
				result.append(objectClass);
				result.append(")");
			}
		}

		final String userSearchFilter = this.conf.getUserSearchFilter();
		if (userSearchFilter != null && !"".equals(userSearchFilter.trim())) {
			result.append("(");
			result.append(userSearchFilter);
			result.append(")");
		}

		if (filter != null && !"".equals(filter.trim())) {
			result.append("(|");

			if (fulltextSearch) {
				attributes = this.conf.getUserNameAttribute().split(",");
				for (final String attr : attributes) {
					result.append("(");
					result.append(attr);
					result.append("=*");
					result.append(filter);
					result.append("*)");
				}

				attributes = this.conf.getUserDisplayNameAttribute().split(",");
				for (final String attr : attributes) {
					result.append("(");
					result.append(attr);
					result.append("=*");
					result.append(filter);
					result.append("*)");
				}

				attributes = this.conf.getUserMailAttrinbute().split(",");
				for (final String attr : attributes) {
					result.append("(");
					result.append(attr);
					result.append("=*");
					result.append(filter);
					result.append("*)");
				}

				attributes = this.conf.getUserAlternateMailAttrinbute().split(",");
				for (final String attr : attributes) {
					result.append("(");
					result.append(attr);
					result.append("=*");
					result.append(filter);
					result.append("*)");
				}

			} else {
				attributes = this.conf.getUserNameAttribute().split(",");
				for (final String attr : attributes) {
					result.append("(");
					result.append(attr);
					result.append("=");
					result.append(filter);
					result.append(")");
				}
			}

			result.append(")");
		}

		result.append(")");
		return result.toString();
	}

	protected String getRestrictionFilter(final String filter) {
		final StringBuffer result = new StringBuffer();

		return result.toString();
	}

	private NamingEnumeration<SearchResult> getResult(final String search) throws NamingException, IOException {

		final LdapContext ldap = this.createContext();
		ldap.setRequestControls(new Control[] { new SortControl(this.conf.getUserNameAttribute(), Control.NONCRITICAL) });

		final SearchControls controls = new SearchControls();
		controls.setReturningAttributes(this.getReturningAttributes());

		if (this.conf.isUserSearchSubtree()) {
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		} else {
			controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		}

		final NamingEnumeration<SearchResult> result = ldap.search(this.conf.getUserSearchContextDN(), search, controls);
		ldap.close();

		return result;
	}

	protected String[] getReturningAttributes() {
		final List<String> attrs = new ArrayList<String>();
		String attr;

		attr = this.conf.getUserNameAttribute();
		if (attr != null && !attr.trim().equals("")) {
			for (final String name : attr.split(",")) {
				attrs.add(name);
			}
		}

		attr = this.conf.getUserDisplayNameAttribute();
		if (attr != null && !attr.trim().equals("")) {
			for (final String name : attr.split(",")) {
				attrs.add(name);
			}
		}

		attr = this.conf.getUserMailAttrinbute();
		if (attr != null && !attr.trim().equals("")) {
			for (final String name : attr.split(",")) {
				attrs.add(name);
			}
		}

		attr = this.conf.getUserAlternateMailAttrinbute();
		if (attr != null && !attr.trim().equals("")) {
			for (final String name : attr.split(",")) {
				attrs.add(name);
			}
		}

		attr = this.conf.getUserEnabledAttribute();
		if (attr != null && !attr.trim().equals("")) {
			for (final String name : attr.split(",")) {
				attrs.add(name);
			}
		}

		return attrs.toArray(new String[attrs.size()]);
	}

	protected String getUrl() {
		final String protocol = this.conf.getServerProtocol().toString();
		final String host = this.conf.getServerHost();
		final Integer port = this.conf.getServerPort();

		final StringBuffer url = new StringBuffer();
		url.append(protocol);
		url.append("://");
		url.append(host);

		if (port != null) {
			url.append(":");
			url.append(port);
		}

		url.append("/");

		return url.toString();
	}

	public void insert(final User user) throws UserManagerException {
		try {
			final Attributes attrs = this.parse(user);
			final String expression = this.conf.getUserInsertPatternDN();
			final String dn = this.evaluateExpression(expression, user);

			final LdapContext ldap = this.createContext();
			ldap.bind(dn, null, attrs);
			ldap.close();

		} catch (final NamingException cause) {
			throw new UserManagerException(cause);
		}
	}

	public User load(final String username) throws UserManagerException {
		User user = null;

		try {
			final SearchResult result = this.searchByUsername(username);

			if (result != null) {
				user = this.parse(result.getAttributes());
			}
		} catch (final NamingException cause) {
			throw new UserManagerException(cause);

		} catch (final IOException cause) {
			throw new UserManagerException(cause);
		}

		return user;
	}

	protected User parse(final Attributes attributes) {

		String username = null;
		if (this.conf.getUserNameAttribute() != null) {
			username = this.getAttribute(attributes, this.conf.getUserNameAttribute().split(",")[0]);
		}

		String displayName = null;
		if (this.conf.getUserDisplayNameAttribute() != null) {
			displayName = this.getAttribute(attributes, this.conf.getUserDisplayNameAttribute().split(",")[0]);
		}

		String email = null;
		if (this.conf.getUserMailAttrinbute() != null) {
			email = this.getAttribute(attributes, this.conf.getUserMailAttrinbute().split(",")[0]);
		}

		String alternateMail = null;
		if (this.conf.getUserAlternateMailAttrinbute() != null) {
			alternateMail = this.getAttribute(attributes, this.conf.getUserAlternateMailAttrinbute().split(",")[0]);
		}

		String enabled = null;
		if (this.conf.getUserEnabledAttribute() != null) {
			enabled = this.getAttribute(attributes, this.conf.getUserEnabledAttribute().split(",")[0]);
		}

		final User user = new User(username);
		user.setDisplayName(displayName);
		user.setEmail(email);
		user.setAlternateEmail(alternateMail);
		user.setEnabled(this.getEnabledValue(enabled));

		return user;
	}

	protected Attributes parse(final User user) {
		final Attributes attrs = new BasicAttributes(true);

		this.setAttribute(this.conf.getUserNameAttribute(), user.getName(), attrs);

		this.setAttribute(this.conf.getUserDisplayNameAttribute(), user.getDisplayName(), attrs);

		this.setAttribute(this.conf.getUserMailAttrinbute(), user.getEmail(), attrs);

		this.setAttribute(this.conf.getUserAlternateMailAttrinbute(), user.getAlternateEmail(), attrs);

		this.setAttribute(this.conf.getUserEnabledAttribute(), this.getEnabledValue(user.isEnabled()), attrs);

		if (this.conf.getUserObjectClasses() != null) {
			final String objectClassAttribute = this.conf.getUserObjectClassAttribute();
			final Attribute objects = new BasicAttribute(objectClassAttribute);

			for (final String object : this.conf.getUserObjectClasses().split(",")) {
				objects.add(object.trim());
			}

			attrs.put(objects);
		}

		final String additionalValuedAttrs = this.conf.getUserAdditionalValuedAttributes();
		if (additionalValuedAttrs != null) {
			String name;
			String value;
			for (final String valuedAttribute : additionalValuedAttrs.split(",")) {

				final String[] parts = valuedAttribute.split("=");

				if (parts.length == 2) {
					name = parts[0].trim();
					value = this.evaluateExpression(parts[1].trim(), user);

					attrs.put(name, value);
					// TODO Logar no "else" que o formato está imcompatível em
					// nivel WARN
				}
			}
		}

		return attrs;
	}

	private List<SearchResult> searchByFilter(final String filter, final boolean fulltextSearch) throws NamingException, IOException {
		final List<SearchResult> result = new ArrayList<SearchResult>();

		final String fullFilter = this.getFilterStatement(filter, fulltextSearch);
		final NamingEnumeration<SearchResult> naming = this.getResult(fullFilter);

		SearchResult item;
		while (naming.hasMoreElements()) {
			item = naming.next();
			result.add(item);
		}

		return result;
	}

	private SearchResult searchByUsername(final String username) throws NamingException, IOException {
		SearchResult result = null;

		for (final SearchResult searchResult : this.searchByFilter(username, false)) {
			result = searchResult;
			break;
		}

		return result;
	}

	private void setAttribute(final String name, final String value, final Attributes attrs) {
		String valueAux = value;

		if (valueAux != null && "".equals(valueAux.trim())) {
			valueAux = null;
		}

		if (name != null && !"".equals(name.trim())) {
			for (final String attribute : name.split(",")) {
				attrs.put(attribute, valueAux);
			}
		}

	}

	public void update(final User user) throws UserManagerException {
		try {
			final Attributes attrs = this.parse(user);
			final String dn = this.searchByUsername(user.getName()).getNameInNamespace();

			final LdapContext ldap = this.createContext();
			ldap.modifyAttributes(dn, DirContext.REPLACE_ATTRIBUTE, attrs);
			ldap.close();

		} catch (final NoPermissionException cause) {
			// TODO Erro de permissão no LDAP. Logar em nivel de ERROR.
			throw new UserManagerException(cause);

		} catch (final NamingException cause) {
			throw new UserManagerException(cause);

		} catch (final IOException cause) {
			throw new UserManagerException(cause);
		}
	}
}
