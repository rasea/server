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
package org.rasea.core.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.rasea.core.domain.Credentials;
import org.rasea.core.domain.User;
import org.rasea.core.exception.RaseaException;
import org.rasea.core.service.AccountService;

import br.gov.frameworkdemoiselle.security.Authenticator;

@RequestScoped
@Alternative
public class RaseaAuthenticator implements Authenticator {

	private static final long serialVersionUID = -6728424241183581910L;

	@Inject
	private AccountService service;

	@Inject
	private Credentials credentials;

	private User user;

	@Override
	public boolean authenticate() {
		boolean authenticated;

		try {
			user = service.authenticate(credentials);
			authenticated = true;

		} catch (RaseaException cause) {
			authenticated = false;
			throw cause;
		}

		return authenticated;
	}

	@Override
	public void unAuthenticate() {
	}

	@Override
	public User getUser() {
		return user;
	}
}
