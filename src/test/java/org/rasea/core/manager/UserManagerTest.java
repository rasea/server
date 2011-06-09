package org.rasea.core.manager;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rasea.core.domain.User;

import br.gov.frameworkdemoiselle.util.DemoiselleRunner;

@RunWith(DemoiselleRunner.class)
public class UserManagerTest {

	@Inject
	private UserManager manager;

	private User getNewFakeUserInstance() {
		User user = new User();

		user.setLogin("fakelogin");
		user.setEmail("fakeemail@fakeemail.com");
		user.setPassword("fakepassword");
		user.setCreation(new Date());

		return user;
	}

	@Test
	public void accountCreatedSuccessfully() {
		User user = getNewFakeUserInstance();

		manager.createAccount(user);
		User persisted = manager.findByLogin(user.getLogin());

		assertNotNull(persisted);
		assertEquals(user.getLogin(), persisted.getLogin());
		assertEquals(user.getEmail(), persisted.getEmail());
		assertEquals(user.getPassword(), persisted.getPassword());
		assertEquals(user.getCreation(), persisted.getCreation());
		assertNull(persisted.getActivation());

		manager.deleteAccount(user);
	}

	@Test
	public void accountDeletedSuccessfully() {
		User user = getNewFakeUserInstance();

		manager.createAccount(user);
		manager.deleteAccount(user);

		User persisted;
		persisted = manager.findByLogin(user.getLogin());
		assertNull(persisted);
		persisted = manager.findByEmail(user.getEmail());
		assertNull(persisted);
	}

	@Test
	public void findingExistentUserByLogin() {
		User user = getNewFakeUserInstance();

		manager.createAccount(user);
		User persisted = manager.findByLogin(user.getLogin());
		assertNotNull(persisted);
		manager.deleteAccount(user);
	}
	
	@Test
	public void notFindingInexistentUserByLogin() {
		User user = getNewFakeUserInstance();
		
		User  persisted = manager.findByLogin(user.getLogin());
		assertNotNull(persisted);
		
		manager.deleteAccount(user);
	}

	@Test
	public void findingExistentUserByEmail() {
		User user = getNewFakeUserInstance();

		manager.createAccount(user);
		User persisted = manager.findByEmail(user.getEmail());
		assertNotNull(persisted);
		manager.deleteAccount(user);
	}
}
