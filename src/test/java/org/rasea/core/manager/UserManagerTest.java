package org.rasea.core.manager;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;

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

//		String activationCode = manager.createAccount(user);
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
	public void mustFailOnTryingToCreateAccountWithNullLogin() {
		User user = getNewFakeUserInstance();
		user.setLogin(null);

		try {
			manager.createAccount(user);

			manager.deleteAccount(user);
			fail("Não deveria inserir usuário com login nulo!");

		} catch (Exception cause) {
			// TODO Se chegar aqui está tudo certo!
		}
	}

	@Test
	public void mustFailOnTryingToCreateAccountWithNullEmail() {
		User user = getNewFakeUserInstance();
		user.setEmail(null);

		try {
			manager.createAccount(user);

			manager.deleteAccount(user);
			fail("Não deveria inserir usuário com e-mail nulo!");

		} catch (Exception cause) {
			// TODO Se chegar aqui está tudo certo!
		}
	}

	@Test
	public void mustFailOnTryingToCreateAccountWithNullPassword() {
		User user = getNewFakeUserInstance();
		user.setPassword(null);

		try {
			manager.createAccount(user);

			manager.deleteAccount(user);
			fail("Não deveria inserir usuário com password nulo!");

		} catch (Exception cause) {
			// TODO Se chegar aqui está tudo certo!
		}
	}

	@Test
	public void mustFailOnTryingToCreateAccountWithNullCreationDate() {
		User user = getNewFakeUserInstance();
		user.setCreation(null);

		try {
			manager.createAccount(user);

			manager.deleteAccount(user);
			fail("Não deveria inserir usuário com data de criação nula!");

		} catch (Exception cause) {
			// TODO Se chegar aqui está tudo certo!
		}
	}

	@Test
	public void mustFailOnTryingToCreateAccountWithDuplicatedEmail() {
		User user;

		user = getNewFakeUserInstance();
		manager.createAccount(user);

		user = getNewFakeUserInstance();
		user.setLogin(user.getLogin() + "2");

		try {
			manager.createAccount(user);

			manager.deleteAccount(user);
			fail("Não deveria inserir com e-mail duplicado!");

		} catch (Exception cause) {
			// TODO Se chegar aqui está tudo certo!
		}

		manager.deleteAccount(user);
	}

	@Test
	public void mustFailOnTryingToCreateAccountWithDuplicatedLogin() {
		User user;

		user = getNewFakeUserInstance();
		manager.createAccount(user);

		user = getNewFakeUserInstance();
		user.setEmail(user.getEmail() + "2");

		try {
			manager.createAccount(user);

			manager.deleteAccount(user);
			fail("Não deveria inserir com login duplicado!");

		} catch (Exception cause) {
			// TODO Se chegar aqui está tudo certo!
		}

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

		User persisted = manager.findByLogin(user.getLogin());
		assertNull(persisted);
	}

	@Test
	public void findingExistentUserByEmail() {
		User user = getNewFakeUserInstance();

		manager.createAccount(user);
		User persisted = manager.findByEmail(user.getEmail());
		assertNotNull(persisted);
		manager.deleteAccount(user);
	}

	@Test
	public void notFindingInexistentUserByEmail() {
		User user = getNewFakeUserInstance();

		User persisted = manager.findByEmail(user.getEmail());
		assertNull(persisted);
	}
}
