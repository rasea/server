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
package org.rasea.ws.v0.type;

/**
 * Classe utilitária responsável por converter os entities da aplicação nos
 * tipos usados nos webservices.
 * 
 * @author cleverson.sacramento
 * @since TODO colocar data
 */
public final class TypeParser {

	public static org.rasea.core.entity.Application parse(final Application application) {
		final org.rasea.core.entity.Application complex = new org.rasea.core.entity.Application();

		complex.setName(application.getName());
		complex.setDisplayName(application.getDescription());

		return complex;
	}

	public static org.rasea.core.entity.Operation parse(final Operation operation) {
		final org.rasea.core.entity.Operation complex = new org.rasea.core.entity.Operation();

		complex.setName(operation.getName());
		complex.setDisplayName(operation.getName());

		return complex;
	}

	public static Application parse(final org.rasea.core.entity.Application application) {
		final Application simple = new Application();

		simple.setName(application.getName());
		simple.setDescription(application.getDisplayName());

		return simple;
	}

	public static Operation parse(final org.rasea.core.entity.Operation operation) {
		final Operation simple = new Operation();

		simple.setName(operation.getName());

		return simple;
	}

	/**
	 * Converte um org.rasea.core.Authorization, extraindo a permissão.
	 * 
	 * @param authorization
	 * @return org.rasea.permission.Permission
	 */
	public static Permission parse(final org.rasea.core.entity.Permission permission) {
		final Permission simple = new Permission();

		if (permission != null) {
			simple.setOperation(permission.getOperation().getName());
			simple.setResource(permission.getResource().getName());
		}

		return simple;
	}

	public static Resource parse(final org.rasea.core.entity.Resource resource) {
		final Resource simple = new Resource();

		simple.setName(resource.getName());
		simple.setDescription(resource.getDisplayName());

		return simple;
	}

	/**
	 * Converte um org.rasea.core.Role.
	 * 
	 * @param role
	 * @return org.rasea.role.Role
	 */
	public static Role parse(final org.rasea.core.entity.Role role) {
		final Role simple = new Role();

		simple.setName(role.getName());
		simple.setDescription(role.getDisplayName());

		return simple;
	}

	/**
	 * Converte um org.rasea.core.User.
	 * 
	 * @param user
	 * @return org.rasea.user.User
	 */
	public static User parse(final org.rasea.extensions.entity.User user) {
		if (user == null) {
			return null;
		}
		final User simple = new User();

		simple.setUsername(user.getName());
		simple.setEmail(user.getEmail());
		simple.setAlternateEmail(user.getAlternateEmail());
		simple.setDisplayName(user.getDisplayName());

		return simple;
	}

	public static org.rasea.core.entity.Resource parse(final Resource resource) {
		final org.rasea.core.entity.Resource complex = new org.rasea.core.entity.Resource();

		complex.setName(resource.getName());
		complex.setDisplayName(resource.getDescription());

		return complex;
	}

	/**
	 * Converte um org.rasea.core.User.
	 * 
	 * @param user
	 * @return org.rasea.user.User
	 */
	public static org.rasea.extensions.entity.User parse(final User user) {
		if (user == null) {
			return null;
		}

		final org.rasea.extensions.entity.User entity = new org.rasea.extensions.entity.User();

		if (user.getUsername() != null && !"".equals(user.getUsername().trim())) {
			entity.setName(user.getUsername());
		}

		if (user.getEmail() != null && !"".equals(user.getEmail().trim())) {
			entity.setEmail(user.getEmail());
		}

		if (user.getAlternateEmail() != null && !"".equals(user.getAlternateEmail().trim())) {
			entity.setAlternateEmail(user.getAlternateEmail());
		}

		if (user.getDisplayName() != null && !"".equals(user.getDisplayName().trim())) {
			entity.setDisplayName(user.getDisplayName());
		}

		return entity;
	}
}
