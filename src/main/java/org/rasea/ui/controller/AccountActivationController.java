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

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.rasea.core.exception.AccountAlreadyActiveException;
import org.rasea.core.exception.EmptyUsernameException;
import org.rasea.core.exception.InvalidConfirmationCodeException;
import org.rasea.core.exception.InvalidUsernameFormatException;
import org.rasea.core.service.AccountService;

import br.gov.frameworkdemoiselle.message.MessageContext;

@Named
@RequestScoped
public class AccountActivationController extends AbstractController {

	private static final long serialVersionUID = 5164076649447930796L;

	@Inject
	private MessageContext messageContext;

	@Inject
	private AccountService service;

	private String activationCode;

	public String activate() throws InvalidConfirmationCodeException, AccountAlreadyActiveException,
			EmptyUsernameException, InvalidUsernameFormatException {
		// Account account = new Account(username);
		// account.setActivationCode(activationCode);
		//
		// service.activate(account);

		service.activate(activationCode);

		messageContext.add("Sua conta foi ativada com sucesso.");
		messageContext.add("Efetue o login e aproveite!");

		return "pretty:login";
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
}
