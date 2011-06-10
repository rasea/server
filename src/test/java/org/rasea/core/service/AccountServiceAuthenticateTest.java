package org.rasea.core.service;

import static junit.framework.Assert.fail;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNull;
import static org.powermock.reflect.Whitebox.setInternalState;

import org.junit.Before;
import org.junit.Test;
import org.rasea.core.domain.Credentials;
import org.rasea.core.domain.User;
import org.rasea.core.exception.AccountNotActiveException;
import org.rasea.core.exception.InvalidCredentialsException;
import org.rasea.core.manager.AccountManager;

public class AccountServiceAuthenticateTest {

	private AccountService service;

	private AccountManager manager;

	@Before
	public void begin() {
		service = new AccountService();
		manager = createMock(AccountManager.class);

		setInternalState(service, AccountManager.class, manager);
	}

	@Test
	public void failWithNullCredentials() throws AccountNotActiveException {
		replay(manager);
		User user = null;

		try {
			user = service.authenticate(null);
			fail();

		} catch (InvalidCredentialsException exception) {
			assertNull(user);
		}
	}

	@Test
	public void failWithNullUsername() throws AccountNotActiveException {
		replay(manager);
		User user = null;

		try {
			Credentials credentials = new Credentials();
			credentials.setUsernameOrEmail(null);
			credentials.setPassword("password");

			user = service.authenticate(credentials);
			fail();

		} catch (InvalidCredentialsException exception) {
			assertNull(user);
		}
	}

	@Test
	public void failWithNullPassword() throws AccountNotActiveException {
		replay(manager);
		User user = null;

		try {
			Credentials credentials = new Credentials();
			credentials.setUsernameOrEmail("usernameOrPassword");
			credentials.setPassword(null);

			user = service.authenticate(credentials);
			fail();

		} catch (InvalidCredentialsException exception) {
			assertNull(user);
		}
	}

	@Test
	public void failWithNullUsernameAndPassword() throws AccountNotActiveException {
		replay(manager);
		User user = null;

		try {
			Credentials credentials = new Credentials();
			credentials.setUsernameOrEmail(null);
			credentials.setPassword(null);

			user = service.authenticate(credentials);
			fail();

		} catch (InvalidCredentialsException exception) {
			assertNull(user);
		}
	}
}
