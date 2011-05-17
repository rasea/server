package org.rasea.core.configuration;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.rasea.core.annotation.Property;
import org.rasea.core.exception.ConfigurationException;

public class ConfigurationLoaderTest { // NOPMD

	public interface SamplePojo<T> {

		T getSample();

		void setSample(final T sample);

	}

	private static final Logger logger = Logger.getLogger(ConfigurationLoaderTest.class);

	@Test
	public void testAdminValues() throws ConfigurationException {
		final ConfigurationLoader loader = new ConfigurationLoader("rasea-server-admin.properties");

		final Admin pojo = new Admin();
		loader.load(pojo);

		Assert.assertEquals("test.admin.password", pojo.getPassword());
		Assert.assertEquals("test@rasea.org", pojo.getEmail());
	}

	@Test
	public void testApplicationValues() throws ConfigurationException {
		final ConfigurationLoader loader = new ConfigurationLoader("rasea-server-application.properties");

		final Application pojo = new Application();
		loader.load(pojo);

		Assert.assertEquals("Test description", pojo.getDescription());
	}

	@Test
	public void testBooleanValues() throws ConfigurationException { // NOPMD
		final SamplePojo<Boolean> pojo1 = new SamplePojo<Boolean>() {

			@Property(key = "bool1")
			public Boolean sample;

			public Boolean getSample() {
				return this.sample;
			}

			public void setSample(final Boolean sample) {
				this.sample = sample;
			}
		};

		final SamplePojo<Boolean> pojo2 = new SamplePojo<Boolean>() {

			@Property(key = "bool2")
			public Boolean sample;

			public Boolean getSample() {
				return this.sample;
			}

			public void setSample(final Boolean sample) {
				this.sample = sample;
			}
		};

		final SamplePojo<Boolean> pojo3 = new SamplePojo<Boolean>() {

			@Property(key = "bool3")
			public Boolean sample;

			public Boolean getSample() {
				return this.sample;
			}

			public void setSample(final Boolean sample) {
				this.sample = sample;
			}
		};

		final SamplePojo<Boolean> pojo4 = new SamplePojo<Boolean>() {

			@Property(key = "bool4")
			public Boolean sample;

			public Boolean getSample() {
				return this.sample;
			}

			public void setSample(final Boolean sample) {
				this.sample = sample;
			}
		};

		final SamplePojo<Boolean> pojo5 = new SamplePojo<Boolean>() {

			@Property(key = "bool5")
			public Boolean sample;

			public Boolean getSample() {
				return this.sample;
			}

			public void setSample(final Boolean sample) {
				this.sample = sample;
			}
		};

		final ConfigurationLoader loader = new ConfigurationLoader("rasea-server-boolean.properties");

		loader.load(pojo1);
		Assert.assertTrue(pojo1.getSample());

		loader.load(pojo2);
		Assert.assertTrue(pojo2.getSample());

		loader.load(pojo3);
		Assert.assertFalse(pojo3.getSample());

		loader.load(pojo4);
		Assert.assertFalse(pojo4.getSample());

		loader.load(pojo5);
		Assert.assertFalse(pojo5.getSample());
	}

	@Test
	public void testConfigurationLoader() throws ConfigurationException {
		Assert.assertNotNull(new ConfigurationLoader("rasea-server-fake.properties"));

		Assert.assertNotNull(new ConfigurationLoader("rasea-server-empty.properties"));

		Assert.assertNotNull(new ConfigurationLoader());
	}

	@Test
	public void testCustomValues() throws ConfigurationException {
		final ConfigurationLoader loader = new ConfigurationLoader("rasea-server-custom.properties");

		final Custom pojo = new Custom();
		loader.load(pojo);

		Assert.assertEquals("org.rasea.custom.Class", pojo.getClazz());
	}

	@Test
	public void testDatabaseValues() throws ConfigurationException {
		final ConfigurationLoader loader = new ConfigurationLoader("rasea-server-database.properties");

		final Database pojo = new Database();
		loader.load(pojo);

		Assert.assertEquals("org.rasea.jdbcDriver", pojo.getDriverClass());
		Assert.assertEquals("jdbc:rasea:test", pojo.getUrl());
		Assert.assertEquals("test.username", pojo.getUsername());
		Assert.assertEquals("test.password", pojo.getPassword());
		Assert.assertEquals("jdbc/test.datasource", pojo.getDatasource());
		Assert.assertEquals("test.schema", pojo.getSchema());
		Assert.assertEquals("test.catalog", pojo.getCatalog());
		Assert.assertEquals(DdlType.VALIDATE, pojo.getDdl());
	}

	@Test
	public void testEnumValues() throws ConfigurationException { // NOPMD
		final ConfigurationLoader loader = new ConfigurationLoader("rasea-server-enum.properties");

		final SamplePojo<SampleEnum> goodPojo1 = new SamplePojo<SampleEnum>() {

			@Property(key = "enum.sample.value", defaultValue = "option1")
			private SampleEnum sample;

			public SampleEnum getSample() {
				return this.sample;
			}

			public void setSample(final SampleEnum sample) {
				this.sample = sample;
			}
		};

		loader.load(goodPojo1);

		final SamplePojo<SampleEnum> goodPojo2 = new SamplePojo<SampleEnum>() {

			@Property(key = "enum.sample.value", defaultValue = "OPTION2")
			private SampleEnum sample;

			public SampleEnum getSample() {
				return this.sample;
			}

			public void setSample(final SampleEnum sample) {
				this.sample = sample;
			}
		};

		loader.load(goodPojo2);

		final SamplePojo<SampleEnum> badPojo = new SamplePojo<SampleEnum>() {

			@Property(key = "enum.sample.value", defaultValue = "option1_FAKE")
			private SampleEnum sample;

			public SampleEnum getSample() {
				return this.sample;
			}

			public void setSample(final SampleEnum sample) {
				this.sample = sample;
			}
		};

		try {
			loader.load(badPojo);
			Assert.fail("This invocation was supposed to fail");

		} catch (final ConfigurationException cause) {
			logger.trace("This exception was supposed to be throwed", cause);
		}

		final Database database = new Database();
		loader.load(database);

		final Store store = new Store();
		loader.load(store);

		try {
			final Ldap ldap = new Ldap();
			loader.load(ldap);

		} catch (final ConfigurationException cause) {
			logger.trace("This exception was supposed to be throwed", cause);
		}
	}

	@Test
	public void testLdapValues() throws ConfigurationException {
		final ConfigurationLoader loader = new ConfigurationLoader("rasea-server-ldap.properties");

		final Ldap pojo = new Ldap();
		loader.load(pojo);

		Assert.assertEquals(ProtocolType.LDAPS, pojo.getServerProtocol());
		Assert.assertEquals("ldap.rasea.org", pojo.getServerHost());
		Assert.assertEquals(Integer.valueOf(9988), pojo.getServerPort());
		Assert.assertEquals("test.username", pojo.getBindDN());
		Assert.assertEquals("test.password", pojo.getBindPassword());
		Assert.assertEquals("ou=Users,dc=test,dc=rasea,dc=org", pojo.getUserSearchContextDN());
		Assert.assertEquals("type=test.employee", pojo.getUserSearchFilter());
		Assert.assertEquals(true, pojo.isUserSearchSubtree());
		Assert.assertEquals("cn={username},ou=Users,dc=test,dc=rasea,dc=org", pojo.getUserInsertPatternDN());
		Assert.assertEquals("test.accountName", pojo.getUserNameAttribute());
		Assert.assertEquals("test.displayName", pojo.getUserDisplayNameAttribute());
		Assert.assertEquals("test.mail", pojo.getUserMailAttrinbute());
		Assert.assertEquals("test.alternateMail", pojo.getUserAlternateMailAttrinbute());
		Assert.assertEquals("test.pwd", pojo.getUserPasswordAttribute());
		Assert.assertEquals("test.enabledControl", pojo.getUserEnabledAttribute());
		Assert.assertEquals("test.enabled", pojo.getUserEnabledValue());
		Assert.assertEquals("test.disabled", pojo.getUserDisabledValue());
		Assert.assertEquals("test.objectClass", pojo.getUserObjectClassAttribute());
		Assert.assertEquals("test.user", pojo.getUserObjectClasses());
		Assert.assertEquals("test.attr1=test.value1,test.attr2=test.value2", pojo.getUserAdditionalValuedAttributes());
		Assert.assertEquals("test.SHA1", pojo.getUserPasswordCharset());
	}

	@Test
	public void testLoadDefaultValues() throws ConfigurationException {
		final ConfigurationLoader loader = new ConfigurationLoader("rasea-server-empty.properties");

		final Settings pojo = new Settings();
		loader.load(pojo);

		// application
		Assert.assertEquals("Rasea", pojo.getApplication().getDescription());

		// admin
		Assert.assertEquals("rasea", pojo.getAdmin().getPassword());
		Assert.assertEquals("no-reply@rasea.org", pojo.getAdmin().getEmail());

		// mail
		Assert.assertNull(pojo.getMail().getHost());
		Assert.assertFalse(pojo.getMail().isTls());
		Assert.assertNull(pojo.getMail().getPort());
		Assert.assertNull(pojo.getMail().getUsername());
		Assert.assertNull(pojo.getMail().getPassword());

		// database
		Assert.assertEquals("org.hsqldb.jdbcDriver", pojo.getDatabase().getDriverClass());
		Assert.assertEquals("jdbc:hsqldb:rasea-database", pojo.getDatabase().getUrl());
		Assert.assertNull(pojo.getDatabase().getDatasource());
		Assert.assertEquals(DdlType.UPDATE, pojo.getDatabase().getDdl());
		Assert.assertEquals("sa", pojo.getDatabase().getUsername());
		Assert.assertNull(pojo.getDatabase().getPassword());
		Assert.assertNull(pojo.getDatabase().getSchema());
		Assert.assertNull(pojo.getDatabase().getCatalog());

		// store
		Assert.assertEquals(StoreType.DEFAULT, pojo.getStore().getStoreType());
		Assert.assertFalse(pojo.getStore().isReadonly());

		// ldap
		Assert.assertEquals(ProtocolType.LDAP, pojo.getLdap().getServerProtocol());
		Assert.assertNull(pojo.getLdap().getServerHost());
		Assert.assertNull(pojo.getLdap().getServerPort());
		Assert.assertNull(pojo.getLdap().getBindDN());
		Assert.assertNull(pojo.getLdap().getBindPassword());
		Assert.assertNull(pojo.getLdap().getUserSearchContextDN());
		Assert.assertNull(pojo.getLdap().getUserSearchFilter());
		Assert.assertFalse(pojo.getLdap().isUserSearchSubtree());
		Assert.assertNull(pojo.getLdap().getUserInsertPatternDN());
		Assert.assertEquals("sAMAccountName", pojo.getLdap().getUserNameAttribute());
		Assert.assertEquals("givenName", pojo.getLdap().getUserDisplayNameAttribute());
		Assert.assertNull(pojo.getLdap().getUserMailAttrinbute());
		Assert.assertNull(pojo.getLdap().getUserAlternateMailAttrinbute());
		Assert.assertEquals("userPassword", pojo.getLdap().getUserPasswordAttribute());
		Assert.assertEquals("userAccountControl", pojo.getLdap().getUserEnabledAttribute());
		Assert.assertEquals("544", pojo.getLdap().getUserEnabledValue());
		Assert.assertEquals("546", pojo.getLdap().getUserDisabledValue());
		Assert.assertEquals("objectClass", pojo.getLdap().getUserObjectClassAttribute());
		Assert.assertEquals("top,person,organizationalPerson,user", pojo.getLdap().getUserObjectClasses());
		Assert.assertNull(pojo.getLdap().getUserAdditionalValuedAttributes());
		Assert.assertNull(pojo.getLdap().getUserPasswordCharset());

		// custom
		Assert.assertNull(pojo.getCustom().getClazz());
	}

	@Test
	public void testMailValues() throws ConfigurationException {
		final ConfigurationLoader loader = new ConfigurationLoader("rasea-server-mail.properties");

		final Mail pojo = new Mail();
		loader.load(pojo);

		Assert.assertEquals("test.rasea.org", pojo.getHost());
		Assert.assertTrue(pojo.isTls());
		Assert.assertEquals(Integer.valueOf(1313), pojo.getPort());
		Assert.assertEquals("test.username", pojo.getUsername());
		Assert.assertEquals("test.password", pojo.getPassword());
	}

	@Test
	public void testStoreValues() throws ConfigurationException {
		final ConfigurationLoader loader = new ConfigurationLoader("rasea-server-store.properties");

		final Store pojo = new Store();
		loader.load(pojo);

		Assert.assertEquals(StoreType.CUSTOM, pojo.getStoreType());
		Assert.assertTrue(pojo.isReadonly());
	}
}

enum SampleEnum {
	OPTION1, OPTION2, OPTION3
}
