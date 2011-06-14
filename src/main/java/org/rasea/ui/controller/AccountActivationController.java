package org.rasea.ui.controller;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.rasea.core.domain.Account;
import org.rasea.core.service.AccountService;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;
import br.gov.frameworkdemoiselle.util.Parameter;

@Named
@ViewScoped
public class AccountActivationController implements Serializable {

	private static final long serialVersionUID = -2528453695349940601L;

	@Inject
	private AccountService service;

	@Inject
	private Parameter<String> username;

	@Inject
	private Parameter<String> activationCode;

	public void activate() {
		Account account = new Account();
		account.setUsername(username.getValue());
		account.setActivationCode(activationCode.getValue());

		service.activate(account);
	}
}
