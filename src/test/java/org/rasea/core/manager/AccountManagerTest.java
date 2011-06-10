package org.rasea.core.manager;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rasea.core.domain.Account;

import br.gov.frameworkdemoiselle.util.DemoiselleRunner;

@RunWith(DemoiselleRunner.class)
public class AccountManagerTest {

	@Inject
	private AccountManager manager;

	private Account getNewFakeAccountInstance() {
		Account account = new Account();
		account.setUsername("fakeuser");
		account.setEmail("fakemail@fakemail.com");
		account.setPassword("fakepass");
		account.setActivationCode("ASDFG12345");
		account.setCreationDate(new Date());
		return account;
	}

	@Test
	public void createAccountSuccessfully() {
		Account account = getNewFakeAccountInstance();

		manager.create(account);
		Account persisted = manager.findByUsername(account.getUsername());

		assertNotNull(persisted);
		assertEquals(account.getUsername(), persisted.getUsername());
		assertEquals(account.getEmail(), persisted.getEmail());
		assertEquals(account.getPassword(), persisted.getPassword());
		assertEquals(account.getCreationDate(), persisted.getCreationDate());
		assertNull(persisted.getActivationDate());

		manager.delete(account);
	}

	@Test
	public void activateAccountSuccessfully() {
		Account account = getNewFakeAccountInstance();
		manager.create(account);

		account.setActivationDate(new Date());
		manager.activate(account);
		assertNotNull(account.getActivationDate());

		Account persisted = manager.findByUsername(account.getUsername());
		assertNotNull(persisted.getActivationDate());

		manager.delete(account);
	}

	@Test
	public void deleteAccountSuccessfully() {
		Account account = getNewFakeAccountInstance();

		manager.create(account);
		manager.delete(account);

		Account persisted;
		
		persisted = manager.findByUsername(account.getUsername());
		assertNull(persisted);
		
		persisted = manager.findByEmail(account.getEmail());
		assertNull(persisted);
	}

	@Test
	public void findExistingAccountByUsername() {
		Account account = getNewFakeAccountInstance();
		manager.create(account);
		
		Account persisted = manager.findByUsername(account.getUsername());
		assertNotNull(persisted);
		assertEquals(account.getUsername(), persisted.getUsername());
		assertEquals(account.getEmail(), persisted.getEmail());

		manager.delete(account);
	}

	@Test
	public void findInexistingAccountByUsername() {
		Account account = getNewFakeAccountInstance();

		Account persisted = manager.findByUsername(account.getUsername());
		assertNull(persisted);
	}

	@Test
	public void findExistingAccountByEmail() {
		Account account = getNewFakeAccountInstance();
		manager.create(account);

		Account persisted = manager.findByEmail(account.getEmail());
		assertNotNull(persisted);
		assertEquals(account.getUsername(), persisted.getUsername());
		assertEquals(account.getEmail(), persisted.getEmail());
		
		manager.delete(account);
	}

	@Test
	public void findInexistingAccountByEmail() {
		Account account = getNewFakeAccountInstance();

		Account persisted = manager.findByEmail(account.getEmail());
		assertNull(persisted);
	}
	
}
