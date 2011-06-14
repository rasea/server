package org.rasea.ui.controller;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.rasea.core.domain.Account;
import org.rasea.core.service.AccountService;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;
import br.gov.frameworkdemoiselle.util.Parameter;

@Named
@ViewScoped
public class AccountActivationController extends AbstractController {

	private static final long serialVersionUID = -2528453695349940601L;

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

		FacesMessage message = new FacesMessage("Sua conta foi ativada com sucesso!");
		getFacesContext().addMessage(null, message);
	}
}
