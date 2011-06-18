package org.rasea.core.service;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNull;
import static org.powermock.reflect.Whitebox.setInternalState;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.rasea.core.domain.Account;
import org.rasea.core.domain.Credentials;
import org.rasea.core.domain.User;
import org.rasea.core.exception.AccountNotActiveException;
import org.rasea.core.exception.EmptyUsernameException;
import org.rasea.core.exception.InvalidCredentialsException;
import org.rasea.core.exception.InvalidUsernameFormatException;
import org.rasea.core.manager.AccountManager;
import org.rasea.core.util.Hasher;

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
	public void failWithNullCredentials() throws AccountNotActiveException, EmptyUsernameException, InvalidUsernameFormatException {
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
	public void failWithNullUsername() throws AccountNotActiveException, EmptyUsernameException, InvalidUsernameFormatException {
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
	public void failWithNullPassword() throws AccountNotActiveException, EmptyUsernameException, InvalidUsernameFormatException {
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
	public void failWithNullUsernameAndPassword() throws AccountNotActiveException, EmptyUsernameException, InvalidUsernameFormatException {
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

	@Test
	public void failWithValidEmailNotFoundOnDatabase() throws AccountNotActiveException, EmptyUsernameException, InvalidUsernameFormatException {
		Credentials credentials = new Credentials();
		credentials.setUsernameOrEmail("test@test.com");
		credentials.setPassword("1234");

		expect(manager.findByEmail(credentials.getUsernameOrEmail())).andReturn(null);
		replay(manager);

		User user = null;

		try {
			user = service.authenticate(credentials);
			fail();

		} catch (InvalidCredentialsException exception) {
			assertNull(user);
		}
	}

	@Test
	public void failWithValidEmailAndNullActivationDate() throws InvalidCredentialsException, EmptyUsernameException, InvalidUsernameFormatException {
		Credentials credentials = new Credentials();
		credentials.setUsernameOrEmail("test@test.com");
		credentials.setPassword("1234");

		Account account = createMock(Account.class);

		expect(account.getActivationDate()).andReturn(null);
		expect(manager.findByEmail(credentials.getUsernameOrEmail())).andReturn(account);
		replay(manager, account);

		User user = null;

		try {
			user = service.authenticate(credentials);
			fail();

		} catch (AccountNotActiveException exception) {
			assertNull(user);
		}
	}

	@Test
	public void failWithValidEmailAndWrongPassword() throws AccountNotActiveException, EmptyUsernameException, InvalidUsernameFormatException {
		Credentials credentials = new Credentials();
		credentials.setUsernameOrEmail("test@test.com");
		credentials.setPassword("1234");

		Account account = createMock(Account.class);
		expect(account.getActivationDate()).andReturn(Calendar.getInstance().getTime());
		expect(account.getUsername()).andReturn("username");
		expect(account.getPassword()).andReturn("xxx");
		expect(manager.findByEmail(credentials.getUsernameOrEmail())).andReturn(account);
		replay(manager, account);

		User user = null;

		try {
			user = service.authenticate(credentials);
			fail();
		} catch (InvalidCredentialsException e) {
			assertNull(user);
		}
	}

	@Test
	public void succeedWithValidEmail() throws AccountNotActiveException, EmptyUsernameException, InvalidUsernameFormatException {
		Credentials credentials = new Credentials();
		credentials.setUsernameOrEmail("test@test.com");
		credentials.setPassword("1234");

		String passwordHash = Hasher.getInstance().digest(credentials.getPassword(), "username");

		Account account = createMock(Account.class);
		expect(account.getActivationDate()).andReturn(Calendar.getInstance().getTime());
		expect(account.getUsername()).andReturn("username").times(2);
		expect(account.getPassword()).andReturn(passwordHash);
		expect(account.getPhotoUrl()).andReturn("photo/img.jpg");
		expect(manager.findByEmail(credentials.getUsernameOrEmail())).andReturn(account);
		replay(manager, account);

		User user = null;

		try {
			user = service.authenticate(credentials);
			assertNotNull(user);
		} catch (InvalidCredentialsException e) {
			fail();
		}
	}

	@Test
	public void failWithInvalidEmailAndUsernameNotFoundOnDatabase() throws AccountNotActiveException, EmptyUsernameException,
			InvalidUsernameFormatException {
		Credentials credentials = new Credentials();
		credentials.setUsernameOrEmail("test@test,com");
		credentials.setPassword("1234");

		expect(manager.findByUsername(credentials.getUsernameOrEmail())).andReturn(null);
		replay(manager);

		User user = null;

		try {
			user = service.authenticate(credentials);
			fail();

		} catch (InvalidCredentialsException exception) {
			assertNull(user);
		}
	}

	@Test
	public void succeedWithUsernameFoundOnDatabase() throws AccountNotActiveException, EmptyUsernameException, InvalidUsernameFormatException {
		Credentials credentials = new Credentials();
		credentials.setUsernameOrEmail("username");
		credentials.setPassword("1234");

		String passwordHash = Hasher.getInstance().digest(credentials.getPassword(), "username");

		Account account = createMock(Account.class);
		expect(account.getActivationDate()).andReturn(Calendar.getInstance().getTime());
		expect(account.getUsername()).andReturn("username").times(2);
		expect(account.getPassword()).andReturn(passwordHash);
		expect(account.getPhotoUrl()).andReturn("photo/img.jpg");
		expect(manager.findByUsername(credentials.getUsernameOrEmail())).andReturn(account);
		replay(manager, account);

		User user = null;

		try {
			user = service.authenticate(credentials);
			assertNotNull(user);
		} catch (InvalidCredentialsException e) {
			fail();
		}
	}

}
