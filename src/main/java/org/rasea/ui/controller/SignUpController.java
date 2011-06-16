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

import static org.rasea.ui.validator.FieldType.EMAIL;
import static org.rasea.ui.validator.FieldType.USERNAME;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.rasea.core.domain.Account;
import org.rasea.core.exception.EmailAlreadyAssignedException;
import org.rasea.core.exception.EmptyEmailException;
import org.rasea.core.exception.EmptyUsernameException;
import org.rasea.core.exception.InvalidEmailFormatException;
import org.rasea.core.exception.InvalidUsernameFormatException;
import org.rasea.core.exception.UsernameAlreadyExistsException;
import org.rasea.core.service.AccountService;
import org.rasea.ui.validator.Unique;
import org.rasea.ui.validator.ValidFormat;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;
import br.gov.frameworkdemoiselle.message.MessageContext;

@Named
@ViewScoped
public class SignUpController extends AbstractController {

	private static final long serialVersionUID = -7576321048358680557L;

	@Inject
	private MessageContext messageContext;

	@Inject
	private AccountService service;

	@NotNull
	@Length(min = 1, message = "{required.field}")
	@Unique(type = USERNAME, message = UsernameAlreadyExistsException.MESSAGE)
	@ValidFormat(type = USERNAME, message = InvalidUsernameFormatException.MESSAGE)
	private String username;

	@NotNull
	@Length(min = 1, message = "{required.field}")
	@Unique(type = EMAIL, message = EmailAlreadyAssignedException.MESSAGE)
	@ValidFormat(type = EMAIL, message = InvalidEmailFormatException.MESSAGE)
	private String email;

	@NotNull
	@Length(min = 1, message = "{required.field}")
	private String password;

	@NotNull
	@Length(min = 1, message = "{required.field}")
	private String confirmPassword;

	public String createAccount() throws EmptyUsernameException, InvalidUsernameFormatException, EmptyEmailException, InvalidEmailFormatException,
			UsernameAlreadyExistsException, EmailAlreadyAssignedException {
		String outcome;

		if (password.equals(confirmPassword)) {
			Account account = new Account(username);
			account.setEmail(email);
			account.setPassword(password);

			service.create(account);

			messageContext.add("Conta criada com sucesso.");
			messageContext.add("Verifique seu e-mail e siga as instruções para ativar sua conta.");

			outcome = "pretty:index";

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
