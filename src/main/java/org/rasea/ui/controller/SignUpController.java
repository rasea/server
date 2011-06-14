package org.rasea.ui.controller;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.rasea.core.domain.Account;
import org.rasea.core.exception.EmailAlreadyAssignedException;
import org.rasea.core.exception.InvalidEmailFormatException;
import org.rasea.core.exception.InvalidUsernameFormatException;
import org.rasea.core.exception.UsernameAlreadyExistsException;
import org.rasea.core.service.AccountService;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.util.Faces;

@Named
@ViewScoped
public class SignUpController extends AbstractController {

	private static final long serialVersionUID = -2528453695349940601L;

	@Inject
	private MessageContext messageContext;

	@Inject
	private AccountService service;

	@NotNull
	private String username;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String confirmPassword;

	public String createAccount() {
		String outcome = null;

		try {
			Account account = new Account(username);
			account.setEmail(email);
			account.setPassword(password);

			service.create(account);

			messageContext.add("Conta criada com sucesso.");
			messageContext.add("Verifique seu e-mail e siga as instruções para ativar sua conta.");

			outcome = "pretty:index";

		} catch (InvalidUsernameFormatException cause) {
			Faces.addMessage("username", cause);

		} catch (UsernameAlreadyExistsException cause) {
			Faces.addMessage("username", cause);

		} catch (InvalidEmailFormatException cause) {
			Faces.addMessage("email", cause);

		} catch (EmailAlreadyAssignedException cause) {
			Faces.addMessage("email", cause);
		}

		return outcome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
