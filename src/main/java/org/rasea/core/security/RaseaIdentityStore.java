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

import static org.jboss.seam.ScopeType.APPLICATION;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.management.IdentityStore;
import org.rasea.core.service.UserService;

@Name("raseaIdentityStore")
@Scope(APPLICATION)
public class RaseaIdentityStore implements IdentityStore {

	@In
	private UserService userService;

	public boolean addRoleToGroup(final String role, final String group) {
		return false;
	}

	public boolean authenticate(final String username, final String password) {
		boolean result = false;

		try {
			result = userService.authenticate(username, password);
		} catch (Exception cause) {
			throw new RuntimeException(cause);
		}

		return result;
	}

	@Override
	public boolean supportsFeature(Feature feature) {
		return false;
	}

	@Override
	public boolean createUser(String username, String password) {
		return false;
	}

	@Override
	public boolean createUser(String username, String password, String firstname, String lastname) {
		return false;
	}

	@Override
	public boolean deleteUser(String name) {
		return false;
	}

	@Override
	public boolean enableUser(String name) {
		return false;
	}

	@Override
	public boolean disableUser(String name) {
		return false;
	}

	@Override
	public boolean isUserEnabled(String name) {
		return false;
	}

	@Override
	public boolean changePassword(String name, String password) {
		boolean result = false;

		try {
			userService.changePassword(name, password);
			result = true;

		} catch (Exception cause) {
			throw new RuntimeException(cause);
		}

		return result;
	}

	@Override
	public boolean userExists(String name) {
		return false;
	}

	@Override
	public boolean createRole(String role) {
		return false;
	}

	@Override
	public boolean grantRole(String name, String role) {
		return false;
	}

	@Override
	public boolean revokeRole(String name, String role) {
		return false;
	}

	@Override
	public boolean deleteRole(String role) {
		return false;
	}

	@Override
	public boolean roleExists(String name) {
		return false;
	}

	@Override
	public boolean removeRoleFromGroup(String role, String group) {
		return false;
	}

	@Override
	public List<String> listUsers() {
		return null;
	}

	@Override
	public List<String> listUsers(String filter) {
		return null;
	}

	@Override
	public List<String> listRoles() {
		return null;
	}

	@Override
	public List<String> listGrantableRoles() {
		return null;
	}

	@Override
	public List<String> getGrantedRoles(String name) {
		return null;
	}

	@Override
	public List<String> getImpliedRoles(String name) {
		return new ArrayList<String>();
	}

	@Override
	public List<String> getRoleGroups(String name) {
		return null;
	}

	@Override
	public List<Principal> listMembers(String role) {
		return null;
	}
}
