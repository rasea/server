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

import org.rasea.core.domain.Account;
import org.rasea.core.exception.EmptyUsernameException;
import org.rasea.core.exception.InvalidUsernameFormatException;
import org.rasea.core.service.AccountService;

@Named
@RequestScoped
public class UserProfileController extends AbstractUserController {

	private static final long serialVersionUID = 1L;

	@Inject
	private AccountService accountService;

	private Account account;

	private String username;

	public Account getAccount() throws EmptyUsernameException, InvalidUsernameFormatException {
		if (account == null) {
			account = accountService.findByUsername(getUsername());
		}

		return account;
	}

	@Override
	protected String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
