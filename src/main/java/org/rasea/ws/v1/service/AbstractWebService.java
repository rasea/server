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
package org.rasea.ws.v1.service;

import java.util.List;

import org.jboss.seam.Component;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Permission;
import org.rasea.core.entity.Resource;
import org.rasea.core.exception.DuplicatedException;
import org.rasea.core.exception.EmailSendException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.exception.StoreException;
import org.rasea.core.service.ApplicationService;
import org.rasea.core.service.AuthorizationService;
import org.rasea.core.service.DataService;
import org.rasea.core.service.MemberService;
import org.rasea.core.service.OperationService;
import org.rasea.core.service.OwnerService;
import org.rasea.core.service.PermissionService;
import org.rasea.core.service.ResourceService;
import org.rasea.core.service.RoleService;
import org.rasea.core.service.UserService;
import org.rasea.extensions.entity.User;
import org.rasea.ws.v1.exception.ExceptionFactory;
import org.rasea.ws.v1.exception.WebServiceFault;
import org.rasea.ws.v1.type.CredentialsType;

public abstract class AbstractWebService {

	protected void checkAuthentication(final CredentialsType credentials) throws WebServiceFault {
		try {
			final boolean authenticated = this.getUserService().authenticate(credentials.getUsername(),
					credentials.getPassword());

			if (!authenticated) {
				throw ExceptionFactory.createAuthenticationFailed(credentials.getUsername());
			}

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	protected void checkPermission(final CredentialsType credentials, final String resourceName,
			final String operationName) throws WebServiceFault {
		try {
			final String username = credentials.getUsername();

			final Application application = this.getDefaultApplication();
			final List<Permission> permissions = this.getPermissionService().find(application, new User(username));
			final Resource resource = this.getResource(application, resourceName);
			final Operation operation = this.getOperation(application, operationName);
			final Permission permission = new Permission(resource, operation);

			if (!permissions.contains(permission)) {
				throw ExceptionFactory.createPermissionDenied(username, application.getName(), resourceName,
						operationName);
			}

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	protected org.rasea.core.entity.Application getApplication(final String application) throws WebServiceFault,
			RequiredException {
		final org.rasea.core.entity.Application app = this.getApplicationService().find(application);

		if (app == null) {
			throw ExceptionFactory.createNotFound("application", application);
		}

		return app;
	}

	protected ApplicationService getApplicationService() {
		return (ApplicationService) Component.getInstance(ApplicationService.class, true);
	}

	protected AuthorizationService getAuthorizationService() {
		return (AuthorizationService) Component.getInstance(AuthorizationService.class, true);
	}

	protected DataService getDataService() {
		return (DataService) Component.getInstance(DataService.class, true);
	}

	protected Application getDefaultApplication() {
		return (Application) Component.getInstance("defaultApplication");
	}

	protected MemberService getMemberService() {
		return (MemberService) Component.getInstance(MemberService.class, true);
	}

	protected org.rasea.core.entity.Operation getOperation(final org.rasea.core.entity.Application aplication,
			final String operation) throws WebServiceFault, RequiredException {
		final org.rasea.core.entity.Operation op = this.getOperationService().find(aplication, operation);

		if (op == null) {
			throw ExceptionFactory.createNotFound("operation", operation);
		}

		return op;
	}

	protected OperationService getOperationService() {
		return (OperationService) Component.getInstance(OperationService.class, true);
	}

	protected OwnerService getOwnerService() {
		return (OwnerService) Component.getInstance(OwnerService.class, true);
	}

	protected PermissionService getPermissionService() {
		return (PermissionService) Component.getInstance(PermissionService.class, true);
	}

	protected org.rasea.core.entity.Resource getResource(final org.rasea.core.entity.Application aplication,
			final String resource) throws WebServiceFault, RequiredException {
		final org.rasea.core.entity.Resource res = this.getResourceService().find(aplication, resource);

		if (res == null) {
			throw ExceptionFactory.createNotFound("resource", resource);
		}

		return res;
	}

	protected ResourceService getResourceService() {
		return (ResourceService) Component.getInstance(ResourceService.class, true);
	}

	protected org.rasea.core.entity.Role getRole(final org.rasea.core.entity.Application aplication, final String role)
			throws WebServiceFault, RequiredException {
		final org.rasea.core.entity.Role serRole = this.getRoleService().find(aplication, role);

		if (serRole == null) {
			throw ExceptionFactory.createNotFound("role", role);
		}

		return serRole;
	}

	protected RoleService getRoleService() {
		return (RoleService) Component.getInstance(RoleService.class, true);
	}

	protected org.rasea.extensions.entity.User getUser(final String username) throws WebServiceFault,
			RequiredException, StoreException {
		final org.rasea.extensions.entity.User usr = this.getUserService().load(username);

		if (usr == null) {
			throw ExceptionFactory.createNotFound("username", username);
		}
		return usr;
	}

	protected UserService getUserService() {
		return (UserService) Component.getInstance(UserService.class, true);
	}

	protected void handleException(final Exception cause) throws WebServiceFault {
		if (cause instanceof RequiredException) {
			throw ExceptionFactory.create((RequiredException) cause);

		} else if (cause instanceof DuplicatedException) {
			throw ExceptionFactory.create((DuplicatedException) cause);

		} else if (cause instanceof StoreException) {
			throw ExceptionFactory.create((StoreException) cause);

		} else if (cause instanceof EmailSendException) {
			throw ExceptionFactory.create((EmailSendException) cause);

		} else if (cause instanceof WebServiceFault) {
			throw (WebServiceFault) cause;

		} else {
			throw ExceptionFactory.createUnknown(cause);
		}
	}
}
