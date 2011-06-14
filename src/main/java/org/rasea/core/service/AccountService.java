/*
 * Rasea Server
 * 
 * Copyright (c) 2008, Rasea <http://rasea.org>. All rights reserved.
 *
 * Rasea Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see <http://gnu.org/licenses>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.rasea.core.service;

import java.io.Serializable;
import java.util.Calendar;

import javax.inject.Inject;

import org.rasea.core.domain.Account;
import org.rasea.core.domain.Credentials;
import org.rasea.core.domain.User;
import org.rasea.core.exception.AccountAlreadyActiveException;
import org.rasea.core.exception.AccountDoesNotExistsException;
import org.rasea.core.exception.AccountNotActiveException;
import org.rasea.core.exception.EmailAlreadyAssignedException;
import org.rasea.core.exception.InvalidActivationCodeException;
import org.rasea.core.exception.InvalidCredentialsException;
import org.rasea.core.exception.InvalidEmailFormatException;
import org.rasea.core.exception.InvalidUsernameFormatException;
import org.rasea.core.exception.UsernameAlreadyExistsException;
import org.rasea.core.manager.AccountManager;
import org.rasea.core.util.Hasher;
import org.rasea.core.util.Mailer;
import org.rasea.core.util.Validator;

public class AccountService implements Serializable {

	private static final long serialVersionUID = 2097626878174583664L;

	@Inject
	private AccountManager manager;

	public User authenticate(Credentials credentials) throws AccountNotActiveException, InvalidCredentialsException {
		if (credentials == null || credentials.getUsernameOrEmail() == null || credentials.getPassword() == null) {
			throw new InvalidCredentialsException();
		}

		Account account = null;
		if (Validator.getInstance().isValidEmailFormat(credentials.getUsernameOrEmail())) {
			account = manager.findByEmail(credentials.getUsernameOrEmail());
		} else {
			account = manager.findByUsername(credentials.getUsernameOrEmail());
		}

		if (account == null) {
			throw new InvalidCredentialsException();
		}

		if (account.getActivationDate() == null) {
			throw new AccountNotActiveException();
		}

		final String passwordHash = generatePasswordHash(credentials.getPassword(), account.getUsername());

		if (!account.getPassword().equals(passwordHash)) {
			throw new InvalidCredentialsException();
		}

		User user = new User(account.getUsername());
		user.setPhotoUrl(account.getPhotoUrl());

		return user;
	}

	public void create(Account account) throws InvalidUsernameFormatException, InvalidEmailFormatException,
			UsernameAlreadyExistsException, EmailAlreadyAssignedException {

		if (!Validator.getInstance().isValidUsernameFormat(account.getUsername())) {
			throw new InvalidUsernameFormatException();
		}

		if (!Validator.getInstance().isValidEmailFormat(account.getEmail())) {
			throw new InvalidEmailFormatException();
		}

		if (manager.containsUsername(account.getUsername())) {
			throw new UsernameAlreadyExistsException();
		}

		if (manager.containsEmail(account.getEmail())) {
			throw new EmailAlreadyAssignedException();
		}

		account.setCreationDate(Calendar.getInstance().getTime());
		account.setActivationCode(generateActivationCode(account.getUsername()));

		final String passwordHash = generatePasswordHash(account.getPassword(), account.getUsername());
		account.setPassword(passwordHash);

		manager.create(account);

		Mailer.getInstance().notifyAccountActivation(account);
	}

	public void activate(Account account) throws InvalidActivationCodeException, AccountAlreadyActiveException {
		Account persisted = manager.findByUsername(account.getUsername());

		if (persisted == null) {
			throw new InvalidActivationCodeException();
		}

		if (persisted.getActivationDate() != null) {
			throw new AccountAlreadyActiveException();
		}

		if (!persisted.getActivationCode().equals(account.getActivationCode())) {
			throw new InvalidActivationCodeException();
		}

		account.setActivationDate(Calendar.getInstance().getTime());
		manager.activate(account);

		// TODO Mandar e-mail dizendo que a conta está ativa e mais alguns blá-blá-blás
	}

	public void resetPasswordRequest(final Credentials credentials) {

	}

	public void resetPasswordConfirmation(final Account account) {

	}

	public void delete(Account account) throws AccountDoesNotExistsException {
		if (!manager.containsUsername(account.getUsername())) {
			throw new AccountDoesNotExistsException();
		}
		manager.delete(account);
	}

	/**
	 * Generates an activation code (a 32-bit hex) from a username and system timestamp.
	 * 
	 * @param username
	 * @return String
	 */
	private String generateActivationCode(final String username) {
		return Hasher.md5(username + System.currentTimeMillis());
	}

	/**
	 * Generates a hash string from a given password and username.
	 * 
	 * @param password
	 * @param username
	 * @return String
	 */
	private String generatePasswordHash(final String password, final String username) {
		return Hasher.getInstance().digest(password, username);
	}

	// public void sendMail(String to, String subject, String body) {
	// mail.get().to(to).from("raseatestmail@gmail.com").subject(subject).body().text(body).send();
	// }

}
