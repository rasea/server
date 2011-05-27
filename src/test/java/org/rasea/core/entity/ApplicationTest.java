package org.rasea.core.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rasea.extensions.entity.User;

/**
 * 
 */
public class ApplicationTest {

	private void assertNullTransientProperties(final Application application) {
		Assert.assertNull(application.getAuthorizations());
		Assert.assertNull(application.getOperations());
		Assert.assertNull(application.getOwners());
		Assert.assertNull(application.getPermissions());
		Assert.assertNull(application.getResources());
		Assert.assertNull(application.getRoles());
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddAuthorization() {
		// Assert.fail("Not yet implemented");
	}

	@Test
	public void testAddOperation() {
		// Assert.fail("Not yet implemented");
	}

	@Test
	public void testAddPermission() {
		// Assert.fail("Not yet implemented");
	}

	@Test
	public void testAddResource() {
		// Assert.fail("Not yet implemented");
	}

	@Test
	public void testAddRole() {
		// Assert.fail("Not yet implemented");
	}

	@Test
	public void testApplication() {
		final Application application = new Application();

		Assert.assertNull(application.getId());
		Assert.assertNull(application.getName());
		Assert.assertNull(application.getDisplayName());

		this.assertNullTransientProperties(application);
	}

	@Test
	public void testApplicationLong() {
		final Long SAMPLE = new Random().nextLong();
		final Application application = new Application(SAMPLE);

		Assert.assertEquals(SAMPLE, application.getId());
		Assert.assertNull(application.getName());
		Assert.assertNull(application.getDisplayName());

		this.assertNullTransientProperties(application);
	}

	@Test
	public void testApplicationString() {
		final String SAMPLE = String.valueOf(new Random().nextFloat());
		final Application application = new Application(SAMPLE);

		Assert.assertNull(application.getId());
		Assert.assertEquals(SAMPLE, application.getName());
		Assert.assertNull(application.getDisplayName());

		this.assertNullTransientProperties(application);
	}

	@Test
	public void testApplicationStringString() {
		final String SAMPLE_1 = String.valueOf(new Random().nextFloat());
		final String SAMPLE_2 = String.valueOf(new Random().nextFloat());
		final Application application = new Application(SAMPLE_1, SAMPLE_2);

		Assert.assertNull(application.getId());
		Assert.assertEquals(SAMPLE_1, application.getName());
		Assert.assertEquals(SAMPLE_2, application.getDisplayName());

		this.assertNullTransientProperties(application);
	}

	@Test
	public void testAuthorizations() {
		final List<Authorization> SAMPLE = new ArrayList<Authorization>();
		final long init = new Random().nextLong();

		Resource resource;
		Operation operation;
		Permission permission;
		Role role;
		for (int i = 0; i < 10; i++) {
			resource = new Resource(init + i);
			operation = new Operation(init * 2 + i);
			role = new Role(init * 3 + i);

			permission = new Permission(resource, operation);
			SAMPLE.add(new Authorization(permission, role, (init + 1) % 3 == 0));
		}

		final Application application = new Application();
		application.setAuthorizations(SAMPLE);

		Assert.assertEquals(SAMPLE, application.getAuthorizations());
	}

	@Test
	public void testDisplayName() {
		final String SAMPLE = String.valueOf(new Random().nextFloat());

		final Application application = new Application();
		application.setDisplayName(SAMPLE);

		Assert.assertEquals(SAMPLE, application.getDisplayName());
	}

	@Test
	public void testEqualsObject() {
		// Assert.fail("Not yet implemented");
	}

	@Test
	public void testId() {
		final Long SAMPLE = new Random().nextLong();

		final Application application = new Application();
		application.setId(SAMPLE);

		Assert.assertEquals(SAMPLE, application.getId());
	}

	@Test
	public void testName() {
		final String SAMPLE = String.valueOf(new Random().nextFloat());

		final Application application = new Application();
		application.setName(SAMPLE);

		Assert.assertEquals(SAMPLE, application.getName());
	}

	@Test
	public void testOperations() {
		final List<Operation> SAMPLE = new ArrayList<Operation>();
		final long init = new Random().nextLong();

		for (int i = 0; i < 10; i++) {
			SAMPLE.add(new Operation(init + i));
		}

		final Application application = new Application();
		application.setOperations(SAMPLE);

		Assert.assertEquals(SAMPLE, application.getOperations());
	}

	@Test
	public void testOwners() {
		final List<User> SAMPLE = new ArrayList<User>();
		final long init = new Random().nextLong();

		for (int i = 0; i < 10; i++) {
			SAMPLE.add(new User(String.valueOf(init + i)));
		}

		final Application application = new Application();
		application.setOwners(SAMPLE);

		Assert.assertEquals(SAMPLE, application.getOwners());
	}

	@Test
	public void testPermissions() {
		final List<Permission> SAMPLE = new ArrayList<Permission>();
		final long init = new Random().nextLong();

		Resource resource;
		Operation operation;
		for (int i = 0; i < 10; i++) {
			resource = new Resource(init + i);
			operation = new Operation(init * 2 + i);
			SAMPLE.add(new Permission(resource, operation));
		}

		final Application application = new Application();
		application.setPermissions(SAMPLE);

		Assert.assertEquals(SAMPLE, application.getPermissions());
	}

	@Test
	public void testResources() {
		final List<Resource> SAMPLE = new ArrayList<Resource>();
		final long init = new Random().nextLong();

		for (int i = 0; i < 10; i++) {
			SAMPLE.add(new Resource(init + i));
		}

		final Application application = new Application();
		application.setResources(SAMPLE);

		Assert.assertEquals(SAMPLE, application.getResources());
	}

	@Test
	public void testRoles() {
		final List<Role> SAMPLE = new ArrayList<Role>();
		final long init = new Random().nextLong();

		for (int i = 0; i < 10; i++) {
			SAMPLE.add(new Role(init + i));
		}

		final Application application = new Application();
		application.setRoles(SAMPLE);

		Assert.assertEquals(SAMPLE, application.getRoles());
	}

}
