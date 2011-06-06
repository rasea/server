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

import javax.enterprise.inject.Alternative;

import br.gov.frameworkdemoiselle.security.Authenticator;
import br.gov.frameworkdemoiselle.security.User;

@Alternative
public class RaseaAuthenticator implements Authenticator {

	private static final long serialVersionUID = -6728424241183581910L;

	@Override
	public boolean authenticate() {
		return true;
	}

	@Override
	public void unAuthenticate() {
	}

	@Override
	public User getUser() {
		return new User() {

			private static final long serialVersionUID = 1L;

			@Override
			public void setAttribute(Object key, Object value) {
			}

			@Override
			public String getId() {
				return "zyc";
			}

			@Override
			public Object getAttribute(Object key) {
				return null;
			}
		};
	}
}
