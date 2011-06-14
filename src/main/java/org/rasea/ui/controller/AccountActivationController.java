package org.rasea.ui.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.rasea.core.domain.Account;
import org.rasea.core.service.AccountService;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.util.Parameter;

@Named
@ViewScoped
public class AccountActivationController extends AbstractController {

	private static final long serialVersionUID = -2528453695349940601L;

	@Inject
	private MessageContext messageContext;

	@Inject
	private AccountService service;

	@Inject
	private Parameter<String> username;

	@Inject
	private Parameter<String> activationCode;

	public void activate() {
		Account account = new Account(username.getValue());
		account.setActivationCode(activationCode.getValue());

		service.activate(account);

		messageContext.add("Sua conta foi ativada com sucesso.");
		messageContext.add("Efetue o login e aproveite!");
	}
}
