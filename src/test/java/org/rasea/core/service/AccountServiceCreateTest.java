package org.rasea.core.service;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.powermock.reflect.Whitebox.setInternalState;
import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rasea.core.domain.Account;
import org.rasea.core.exception.EmailAlreadyAssignedException;
import org.rasea.core.exception.EmptyEmailException;
import org.rasea.core.exception.EmptyUsernameException;
import org.rasea.core.exception.InvalidEmailFormatException;
import org.rasea.core.exception.InvalidUsernameFormatException;
import org.rasea.core.exception.UsernameAlreadyExistsException;
import org.rasea.core.manager.AccountManager;
import org.rasea.core.util.Mailer;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Mailer.class)
public class AccountServiceCreateTest {

	private AccountService service;

	private AccountManager manager;

	@Before
	public void begin() {
		service = new AccountService();
		manager = createMock(AccountManager.class);

		setInternalState(service, AccountManager.class, manager);
	}

	@Test
	public void failWithNullUsername() {
		replay(manager);

		Account acct = new Account(null);

		try {
			service.create(acct);
			fail();

		} catch (EmptyUsernameException e) {
			assertNull(acct.getCreationDate());
		}
	}

	@Test
	public void failWithEmptyUsername() {
		replay(manager);

		Account acct = new Account(StringUtils.EMPTY);

		try {
			service.create(acct);
			fail();

		} catch (EmptyUsernameException e) {
			assertNull(acct.getCreationDate());
		}
	}

	@Test
	public void failWithInvalidUsername() {
		replay(manager);

		Account acct = new Account("invalid username");

		try {
			service.create(acct);
			fail();

		} catch (InvalidUsernameFormatException e) {
			assertNull(acct.getCreationDate());
		}
	}

	@Test
	public void failWithNullEmail() {
		replay(manager);

		Account acct = new Account("username");
		acct.setEmail(null);

		try {
			service.create(acct);
			fail();

		} catch (EmptyEmailException e) {
			assertNull(acct.getCreationDate());
		}
	}

	@Test
	public void failWithEmptyEmail() {
		replay(manager);

		Account acct = new Account("username");
		acct.setEmail(StringUtils.EMPTY);

		try {
			service.create(acct);
			fail();

		} catch (EmptyEmailException e) {
			assertNull(acct.getCreationDate());
		}
	}

	@Test
	public void failWithInvalidEmail() {
		replay(manager);

		Account acct = new Account("username");
		acct.setEmail("invalid@email,com");

		try {
			service.create(acct);
			fail();

		} catch (InvalidEmailFormatException e) {
			assertNull(acct.getCreationDate());
		}
	}

	@Test
	public void failWithExistentUsername() {
		Account acct = new Account("username");
		acct.setEmail("test@test.com");

		expect(manager.containsUsername(acct.getUsername())).andReturn(true);
		replay(manager);

		try {
			service.create(acct);
			fail();

		} catch (UsernameAlreadyExistsException e) {
			assertNull(acct.getCreationDate());
		}
	}

	@Test
	public void failWithExistentEmail() {
		Account acct = new Account("username");
		acct.setEmail("test@test.com");

		expect(manager.containsUsername(acct.getUsername())).andReturn(false);
		expect(manager.containsEmail(acct.getEmail())).andReturn(true);
		manager.create(acct);
		expectLastCall();
		replay(manager);

		try {
			service.create(acct);
			fail();
		} catch (EmailAlreadyAssignedException e) {
			assertNull(acct.getCreationDate());
		}
	}

	@Test
	public void succeedCreation() {
		Account acct = new Account("username");
		acct.setEmail("test@test.com");
		
		PowerMock.mockStatic(Mailer.class);
		
		Mailer mockSingleton = createMock(Mailer.class);
		
		expect(Mailer.getInstance()).andReturn(mockSingleton).anyTimes();
		
        mockSingleton.notifyAccountActivation(acct);
        expectLastCall().times(2);
        
        PowerMock.replay(Mailer.class, mockSingleton);
		
		expect(manager.containsUsername(acct.getUsername())).andReturn(false);
		expect(manager.containsEmail(acct.getEmail())).andReturn(false);
		
		manager.create(acct);
		expectLastCall();
		
		PowerMock.replay(manager);
		
		try {
			service.create(acct);
			Assert.assertNotNull(acct.getCreationDate());
		} catch (EmptyUsernameException e) {
			fail();
		} catch (InvalidUsernameFormatException e) {
			fail();
		} catch (EmptyEmailException e) {
			fail();
		} catch (InvalidEmailFormatException e) {
			fail();
		} catch (UsernameAlreadyExistsException e) {
			fail();
		} catch (EmailAlreadyAssignedException e) {
			fail();
		}
	}

}
