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
		if (Validator.isValidEmail(credentials.getUsernameOrEmail())) {
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

		String passwordHash = Hasher.getInstance().digest(credentials.getPassword(), account.getUsername());

		if (!account.getPassword().equals(passwordHash)) {
			throw new InvalidCredentialsException();
		}

		User user = new User(account.getUsername());
		user.setPhotoUrl(account.getPhotoUrl());

		return user;
	}

	public void create(Account account) throws InvalidUsernameFormatException, InvalidEmailFormatException,
			UsernameAlreadyExistsException, EmailAlreadyAssignedException {

		if (isValidUsername(account.getUsername())) {
			throw new InvalidUsernameFormatException();
		}

		if (Validator.isValidEmail(account.getEmail())) {
			throw new InvalidEmailFormatException();
		}

		if (manager.containsUsername(account.getUsername())) {
			throw new UsernameAlreadyExistsException();
		}

		if (manager.containsEmail(account.getEmail())) {
			throw new EmailAlreadyAssignedException();
		}

		account.setCreationDate(Calendar.getInstance().getTime());
		account.setActivationCode(generateActivationCode());
		manager.create(account);

		// TODO Mandar e-mail dizendo que a conta foi criada mas que precisa ser ativada clicando no link tal
		// sendMail(user.getEmail(), "título teste", "corpo teste");
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

	public void delete(Account account) throws AccountDoesNotExistsException {
		if (!manager.containsUsername(account.getUsername())) {
			throw new AccountDoesNotExistsException();
		}
		manager.delete(account);
	}

	private String generateActivationCode() {
		// TODO Auto-generated method stub
		return "";
	}

	private boolean isValidUsername(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	// public void sendMail(String to, String subject, String body) {
	// mail.get().to(to).from("raseatestmail@gmail.com").subject(subject).body().text(body).send();
	// }

}
