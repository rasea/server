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

import org.rasea.core.domain.Credentials;
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
	private Credentials credentials;

	@Inject
	private Parameter<String> username;

	@Inject
	private Parameter<String> confirmationCode;

	@NotNull
	private String newPassword;

	@NotNull
	private String confirmPassword;

	public String request() {
		String outcome = null;

		service.passwordResetRequest(credentials);

		messageContext.add("Pedido de reinicialização de senha efetuado com sucesso.");
		messageContext.add("Verifique seu e-mail e siga as instruções para resetar sua senha.");

		outcome = "pretty:index";

		return outcome;
	}

	public String confirm() {
		String outcome = null;

		// verificar se o código de confirmação confere
		// se conferir não fazer nada
		// se nao conferir, exibir mensagem de erro direcionando para pretty:index

		// TODO: ???
		//		service.passwordResetConfirmation(null);

		return outcome;
	}

	public String perform() {
		// Efetiva a mudança da senha

		return null;
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