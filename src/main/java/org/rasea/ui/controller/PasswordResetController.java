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

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.rasea.core.domain.Account;
import org.rasea.core.domain.Credentials;
import org.rasea.core.exception.EmptyPasswordException;
import org.rasea.core.exception.PasswordsDoNotMatchException;
import org.rasea.core.service.AccountService;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.util.Parameter;
import br.gov.frameworkdemoiselle.util.Strings;

@Named
@ViewScoped
public class PasswordResetController extends AbstractController {

	private static final long serialVersionUID = 1996192235542503778L;

	@Inject
	private MessageContext messageContext;

	@Inject
	private AccountService service;

	@Inject
	@NotNull
	private Credentials credentials;

	@Inject
	private Parameter<String> usernameParam;

	@Inject
	private Parameter<String> confirmationCodeParam;

	@NotNull
	private String username;

	@NotNull
	private String confirmationCode;

	@NotNull
	private String newPassword;

	@NotNull
	private String confirmPassword;

	public String request() {
		service.passwordResetRequest(credentials);

		messageContext.add("Pedido de reinicialização de senha efetuado com sucesso.");
		messageContext.add("Verifique seu e-mail e siga as instruções para resetar sua senha.");

		return "pretty:index";
	}

	public void confirm() {
		Account account = new Account(usernameParam.getValue());
		account.setActivationCode(confirmationCodeParam.getValue());

		this.setUsername(account.getUsername());
		this.setConfirmationCode(account.getActivationCode());
	}

	public String perform() {
		Account account = new Account(username);
		account.setActivationCode(confirmationCode);
		account.setPassword(newPassword);
		
		if (Strings.isEmpty(newPassword)) {
			throw new EmptyPasswordException();
		}
		
		if (!newPassword.equals(confirmPassword)) {
			throw new PasswordsDoNotMatchException();
		}
		
		service.passwordResetConfirmation(account);
		
		messageContext.add("Sua senha foi alterada com sucesso.");
		messageContext.add("Efetue o login e aproveite!");
		
		return "pretty:index";
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

}
