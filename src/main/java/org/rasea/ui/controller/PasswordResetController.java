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
package org.rasea.ui.controller;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.rasea.core.domain.Account;
import org.rasea.core.exception.AccountDoesNotExistsException;
import org.rasea.core.exception.EmptyEmailException;
import org.rasea.core.exception.EmptyUsernameException;
import org.rasea.core.exception.InvalidConfirmationCodeException;
import org.rasea.core.exception.InvalidEmailFormatException;
import org.rasea.core.service.AccountService;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.util.Parameter;

@Named
@ViewScoped
public class PasswordResetController extends AbstractController {

	private static final long serialVersionUID = 1996192235542503778L;

	@Inject
	private MessageContext messageContext;

	@Inject
	private AccountService service;

	@NotNull
	@Length(min = 1, message = "{required.field}")
	private String email;

	@Inject
	private Parameter<String> usernameParam;

	@Inject
	private Parameter<String> confirmationCodeParam;

	@NotNull
	private String username;

	@NotNull
	private String confirmationCode;

	@NotNull
	@Length(min = 1, message = "{required.field}")
	private String newPassword;

	@NotNull
	@Length(min = 1, message = "{required.field}")
	private String confirmPassword;

	public String request() throws InvalidConfirmationCodeException, EmptyEmailException, InvalidEmailFormatException, AccountDoesNotExistsException {
		service.passwordResetRequest(email);

		messageContext.add("Pedido de reinicialização de senha efetuado com sucesso.");
		messageContext.add("Verifique seu e-mail e siga as instruções para resetar sua senha.");

		return "pretty:index";
	}

	public void confirm() {
		// TODO Neste ponto tem que verificar se o código é válido. Se não for, nem prossegue.

		Account account = new Account(usernameParam.getValue());
		account.setPasswordResetCode(confirmationCodeParam.getValue());

		this.setUsername(account.getUsername());
		this.setConfirmationCode(account.getPasswordResetCode());
	}

	public String perform() throws EmptyUsernameException, InvalidConfirmationCodeException {
		String outcome;

		if (newPassword.equals(confirmPassword)) {
			Account account = new Account(username);
			account.setPasswordResetCode(confirmationCode);
			account.setPassword(newPassword);

			service.passwordResetConfirmation(account);

			messageContext.add("Sua senha foi alterada com sucesso.");
			messageContext.add("Efetue o login e aproveite!");

			return "pretty:index";

		} else {
			FacesMessage message = new FacesMessage("Não confere com a senha");
			getFacesContext().addMessage("confirmPassword", message);

			outcome = null;
		}

		return outcome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
