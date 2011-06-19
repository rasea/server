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

import org.rasea.core.domain.Account;
import org.rasea.core.exception.AccountAlreadyActiveException;
import org.rasea.core.exception.EmptyUsernameException;
import org.rasea.core.exception.InvalidConfirmationCodeException;
import org.rasea.core.exception.InvalidUsernameFormatException;
import org.rasea.core.service.AccountService;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.util.Parameter;

@Named
@ViewScoped
public class AccountActivationController extends AbstractController {

	private static final long serialVersionUID = 5164076649447930796L;

	@Inject
	private MessageContext messageContext;

	@Inject
	private AccountService service;

	@Inject
	private Parameter<String> username;

	@Inject
	private Parameter<String> activationCode;

	public String activate() throws InvalidConfirmationCodeException, AccountAlreadyActiveException, EmptyUsernameException,
			InvalidUsernameFormatException {
		Account account = new Account(username.getValue());
		account.setActivationCode(activationCode.getValue());

		service.activate(account);

		messageContext.add("Sua conta foi ativada com sucesso.");
		messageContext.add("Efetue o login e aproveite!");

		return "pretty:login";
	}
}
