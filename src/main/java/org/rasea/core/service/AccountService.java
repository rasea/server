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
import org.rasea.core.exception.EmptyEmailException;
import org.rasea.core.exception.EmptyUsernameException;
import org.rasea.core.exception.InvalidConfirmationCodeException;
import org.rasea.core.exception.InvalidCredentialsException;
import org.rasea.core.exception.InvalidEmailFormatException;
import org.rasea.core.exception.InvalidUsernameFormatException;
import org.rasea.core.exception.UsernameAlreadyExistsException;
import org.rasea.core.manager.AccountManager;
import org.rasea.core.util.Hasher;
import org.rasea.core.util.Mailer;
import org.rasea.core.util.Validator;

import br.gov.frameworkdemoiselle.util.Strings;

public class AccountService implements Serializable {

	private static final long serialVersionUID = 2097626878174583664L;

	@Inject
	private AccountManager manager;

	public User authenticate(final Credentials credentials) throws AccountNotActiveException,
			InvalidCredentialsException, EmptyUsernameException, InvalidUsernameFormatException {
		
		if (credentials == null || credentials.getUsernameOrEmail() == null || credentials.getPassword() == null) {
			throw new InvalidCredentialsException();
		}

		Account account = null;
		if (Validator.getInstance().isValidEmailFormat(credentials.getUsernameOrEmail())) {
			account = manager.findByEmail(credentials.getUsernameOrEmail());
		} else {
			account = findByUsername(credentials.getUsernameOrEmail());
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

		final User user = new User(account.getUsername());
		user.setLastLogin(account.getLastLogin());
		String photoURL = account.getPhotoUrl();
		if (photoURL == null || photoURL.isEmpty()) {
			photoURL = generateGravatarURL(account.getEmail());
		}
		user.setPhotoUrl(photoURL);

		account.setLastLogin(Calendar.getInstance().getTime());
		manager.saveLoginInfo(account);
		
		return user;
	}

	public Account findByUsername(String username) throws EmptyUsernameException, InvalidUsernameFormatException {
		validateUsername(username);
		return manager.findByUsername(username);
	}

	private void validateUsername(String username) throws EmptyUsernameException, InvalidUsernameFormatException {
		if (Strings.isEmpty(username)) {
			throw new EmptyUsernameException();
		}

		if (!Validator.getInstance().isValidUsernameFormat(username)) {
			throw new InvalidUsernameFormatException();
		}
	}

	public void create(final Account account) throws EmptyUsernameException, InvalidUsernameFormatException,
			EmptyEmailException, InvalidEmailFormatException, UsernameAlreadyExistsException, EmailAlreadyAssignedException {

		if (Strings.isEmpty(account.getEmail())) {
			throw new EmptyEmailException();
		}

		if (!Validator.getInstance().isValidEmailFormat(account.getEmail())) {
			throw new InvalidEmailFormatException();
		}

		if (containsUsername(account.getUsername())) {
			throw new UsernameAlreadyExistsException();
		}

		if (containsEmail(account.getEmail())) {
			throw new EmailAlreadyAssignedException();
		}

		account.setCreationDate(Calendar.getInstance().getTime());
		account.setActivationCode(generateCode(account.getUsername()));

		final String passwordHash = generatePasswordHash(account.getPassword(), account.getUsername());
		account.setPassword(passwordHash);

		manager.create(account);

		Mailer.getInstance().notifyAccountActivation(account);
	}

	public void activate(final Account account) throws InvalidConfirmationCodeException,
			AccountAlreadyActiveException, EmptyUsernameException, InvalidUsernameFormatException {

		if (Strings.isEmpty(account.getActivationCode())) {
			throw new InvalidConfirmationCodeException();
		}

		Account persisted = findByUsername(account.getUsername());

		if (persisted == null) {
			throw new InvalidConfirmationCodeException();
		}

		if (persisted.getActivationDate() != null) {
			throw new AccountAlreadyActiveException();
		}

		if (!persisted.getActivationCode().equals(account.getActivationCode())) {
			throw new InvalidConfirmationCodeException();
		}

		account.setActivationDate(Calendar.getInstance().getTime());
		manager.activate(account);

		// TODO Mandar e-mail dizendo que a conta está ativa e mais alguns blá-blá-blás
	}

	public void passwordResetRequest(String email) throws InvalidConfirmationCodeException,
			EmptyEmailException, InvalidEmailFormatException, AccountDoesNotExistsException {

		if (Strings.isEmpty(email)) {
			throw new EmptyEmailException();
		}

		if (!Validator.getInstance().isValidEmailFormat(email)) {
			throw new InvalidEmailFormatException();
		}

		Account account = manager.findByEmail(email);

		if (account == null) {
			throw new AccountDoesNotExistsException();
		}

		account.setPasswordResetCode(generateCode(account.getUsername()));
		manager.askPasswordReset(account);

		Mailer.getInstance().notifyPasswordResetRequest(account);
	}

	public void passwordResetConfirmation(final Account account) throws InvalidConfirmationCodeException,
			EmptyUsernameException, InvalidUsernameFormatException {

		if (Strings.isEmpty(account.getPasswordResetCode())) {
			throw new InvalidConfirmationCodeException();
		}

		Account persisted = findByUsername(account.getUsername());

		if (persisted == null) {
			throw new InvalidConfirmationCodeException();
		}

		if (!account.getPasswordResetCode().equals(persisted.getPasswordResetCode())) {
			throw new InvalidConfirmationCodeException();
		}

		final String passwordHash = generatePasswordHash(account.getPassword(), account.getUsername());
		account.setPassword(passwordHash);

		manager.confirmPasswordReset(account);

		// TODO: mandar e-mail dizendo que a senha foi alterada com sucesso (sem incluí-la no texto!)
	}

	public boolean containsUsername(final String username) throws EmptyUsernameException, InvalidUsernameFormatException {
		validateUsername(username);
		return manager.containsUsername(username);
	}

	public boolean containsEmail(final String email) {
		return manager.containsEmail(email);
	}

	public void delete(final Account account) throws AccountDoesNotExistsException,
			EmptyUsernameException, InvalidUsernameFormatException {
		
		if (!containsUsername(account.getUsername())) {
			throw new AccountDoesNotExistsException();
		}

		manager.delete(account);
	}

	/**
	 * Generates an code (a 32-bit hex) from a username and system timestamp.
	 * 
	 * @param username
	 * @return String
	 */
	private String generateCode(final String username) {
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

	/**
	 * Generates the URL corresponding to the user gravatar, based on its e-mail
	 * address.
	 * 
	 * @param email
	 * @return String
	 */
	private String generateGravatarURL(final String email) {

		// TODO: parametrizar opção de usar versão segura
		final String GRAVATAR_PREFIX_SECURE = "https://secure.gravatar.com/avatar/";
		// final String GRAVATAR_PREFIX_REGULAR = "http://www.gravatar.com/avatar/";

		final String emailHash = Hasher.md5(email);
		final String sizeInfo = "?s=140";

		StringBuffer sb = new StringBuffer(80);
		sb.append(GRAVATAR_PREFIX_SECURE);
		sb.append(emailHash);
		sb.append(sizeInfo);

		// TODO: incluir gravatar default
		// sb.append("&d=http://imagem/default.png");

		return sb.toString();
	}

	// public void sendMail(String to, String subject, String body) {
	// mail.get().to(to).from("raseatestmail@gmail.com").subject(subject).body().text(body).send();
	// }

}
