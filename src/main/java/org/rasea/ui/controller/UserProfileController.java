package org.rasea.ui.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.rasea.core.domain.Account;
import org.rasea.core.exception.EmptyUsernameException;
import org.rasea.core.exception.InvalidUsernameFormatException;
import org.rasea.core.service.AccountService;

@Named
@RequestScoped
public class UserProfileController extends AbstractUserController {

	private static final long serialVersionUID = 1L;

	@Inject
	private AccountService service;

	private String username;

	private Account account;

	public void loadAccount() throws EmptyUsernameException, InvalidUsernameFormatException {
		account = service.findByUsername(username);

		System.out.println(username);
	}

	@Override
	protected String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Account getAccount() {
		return account;
	}

}
