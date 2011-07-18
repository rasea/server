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

import br.gov.frameworkdemoiselle.security.Authorizer;

@Alternative
public class RaseaAuthorizer implements Authorizer {

	private static final long serialVersionUID = -1432059116255260326L;

	@Override
	public boolean hasRole(String role) {
		return true;
	}

	@Override
	public boolean hasPermission(String resource, String operation) {
		return true;
	}
}
