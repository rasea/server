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
 * License along with this library; if not, see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.rasea.core.util;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.util.Strings;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Resource;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.DuplicatedException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.exception.StoreException;
import org.rasea.core.service.ApplicationService;
import org.rasea.core.service.OperationService;
import org.rasea.core.service.ResourceService;
import org.rasea.core.service.RoleService;
import org.rasea.core.service.UserService;
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("serviceValidator")
@Scope(ScopeType.APPLICATION)
public class ServiceValidator implements Serializable {

	private static final long serialVersionUID = 8324063536708341388L;

	@In
	private ApplicationService applicationService;

	@In
	private OperationService operationService;

	@In
	private ResourceService resourceService;

	@In
	private RoleService roleService;

	@In
	private UserService userService;

	public void validateDuplicateName(final Application application) throws DuplicatedException, RequiredException {
		this.validateRequired("application", application);

		final Application applicationAux = this.applicationService.find(application.getName());

		if (applicationAux != null && applicationAux.getName().equalsIgnoreCase(application.getName())
				&& !applicationAux.getId().equals(application.getId())) {

			throw new DuplicatedException("name", application.getName());
		}
	}

	public void validateDuplicateName(final Operation operation) throws DuplicatedException, RequiredException {
		this.validateRequired("operation", operation);

		final Operation operationAux = this.operationService.find(operation.getApplication(), operation.getName());

		if (operationAux != null && operationAux.getName().equalsIgnoreCase(operation.getName())
				&& operationAux.getApplication().equals(operation.getApplication()) && !operationAux.getId().equals(operation.getId())) {
			throw new DuplicatedException("name", operation.getName());
		}
	}

	public void validateDuplicateName(final Resource resource) throws DuplicatedException, RequiredException {
		this.validateRequired("resource", resource);

		final Resource resourceAux = this.resourceService.find(resource.getApplication(), resource.getName());

		if (resourceAux != null && resourceAux.getName().equalsIgnoreCase(resource.getName())
				&& resourceAux.getApplication().equals(resource.getApplication()) && !resourceAux.getId().equals(resource.getId())) {
			throw new DuplicatedException("name", resource.getName());
		}
	}

	public void validateDuplicateName(final Role role) throws DuplicatedException, RequiredException {
		this.validateRequired("role", role);

		final Role roleAux = this.roleService.find(role.getApplication(), role.getName());

		if (roleAux != null && roleAux.getName().equalsIgnoreCase(role.getName()) && roleAux.getApplication().equals(role.getApplication())
				&& !roleAux.getId().equals(role.getId())) {
			throw new DuplicatedException("name", role.getName());
		}
	}

	public void validateDuplicateName(final User user) throws DuplicatedException, RequiredException, StoreException {
		this.validateRequired("user", user);

		final User userAux = this.userService.load(user.getName());

		if (userAux != null && userAux.getName().equalsIgnoreCase(user.getName())) {
			throw new DuplicatedException("username", user.getName());
		}
	}

	public void validateRequired(final String property, final Object value) throws RequiredException {
		if (value == null) {
			throw new RequiredException(property);
		}
	}

	public void validateRequired(final String property, final String value) throws RequiredException {
		if (Strings.isEmpty(value)) {
			throw new RequiredException(property);
		}
	}
}
