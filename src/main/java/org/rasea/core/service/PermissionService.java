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
package org.rasea.core.service;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Authorization;
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Permission;
import org.rasea.core.entity.Resource;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.manager.PermissionManager;
import org.rasea.core.util.ServiceValidator;
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("permissionService")
public class PermissionService extends AbstractService {

	private static final long serialVersionUID = 6539053151276934744L;

	@In
	private AuthorizationService authorizationService;

	@In
	private PermissionManager permissionManager;

	@In
	private ServiceValidator serviceValidator;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Permission permission) throws RequiredException {
		this.serviceValidator.validateRequired("permission", permission);
		this.serviceValidator.validateRequired("permission.resource", permission.getResource());
		this.serviceValidator.validateRequired("permission.operation", permission.getOperation());
		this.serviceValidator.validateRequired("permission.resource.id", permission.getResource().getId());
		this.serviceValidator.validateRequired("permission.operation.id", permission.getOperation().getId());

		if (this.load(permission.getResource(), permission.getOperation()) != null) {
			this.permissionManager.delete(permission);
		}
	}

	public List<Permission> find(final Application application) throws RequiredException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());

		return this.permissionManager.find(application);
	}

	public List<Permission> find(final Application application, final User user) throws RequiredException {
		final List<Permission> result = new ArrayList<Permission>();

		final List<Authorization> authorizations = this.authorizationService.find(application, user);

		for (final Authorization authorization : authorizations) {
			if (!result.contains(authorization.getPermission())) {
				result.add(authorization.getPermission());
			}
		}

		return result;
	}

	public List<Permission> find(final Resource resource) throws RequiredException {
		this.serviceValidator.validateRequired("resource", resource);
		this.serviceValidator.validateRequired("resource.id", resource.getId());

		return this.permissionManager.find(resource);
	}

	public List<Permission> find(final Role role) throws RequiredException {
		final List<Permission> result = new ArrayList<Permission>();

		final List<Authorization> authorizations = this.authorizationService.find(role);

		for (final Authorization authorization : authorizations) {
			if (!result.contains(authorization.getPermission())) {
				result.add(authorization.getPermission());
			}
		}

		return result;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Permission permission) throws RequiredException {
		this.serviceValidator.validateRequired("permission", permission);
		this.serviceValidator.validateRequired("permission.resource", permission.getResource());
		this.serviceValidator.validateRequired("permission.operation", permission.getOperation());
		this.serviceValidator.validateRequired("permission.resource.id", permission.getResource().getId());
		this.serviceValidator.validateRequired("permission.operation.id", permission.getOperation().getId());

		if (this.load(permission.getResource(), permission.getOperation()) == null) {
			this.permissionManager.insert(permission);
		}
	}

	public Permission load(final Resource resource, final Operation operation) throws RequiredException {
		this.serviceValidator.validateRequired("resource", resource);
		this.serviceValidator.validateRequired("resource.id", resource.getId());
		this.serviceValidator.validateRequired("operation", operation);
		this.serviceValidator.validateRequired("operation.id", operation.getId());

		return this.permissionManager.find(resource, operation);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final Resource resource) throws RequiredException {
		this.serviceValidator.validateRequired("resource", resource);
		this.serviceValidator.validateRequired("resource.id", resource.getId());
		this.serviceValidator.validateRequired("resource.application", resource.getApplication());
		this.serviceValidator.validateRequired("resource.application.id", resource.getApplication().getId());

		final List<Permission> selectedPermissions = new ArrayList<Permission>();

		if (resource.getOperations() != null) {
			Permission permission;
			for (final Operation operation : resource.getOperations()) {
				permission = new Permission(resource, operation);
				selectedPermissions.add(permission);
			}
		}

		final List<Permission> currentPermissions = this.find(resource);

		final List<Permission> permissionsToDelete = new ArrayList<Permission>(currentPermissions);
		permissionsToDelete.removeAll(selectedPermissions);

		for (final Permission permission : permissionsToDelete) {
			this.delete(permission);
		}

		final List<Permission> permissionsToInsert = new ArrayList<Permission>(selectedPermissions);
		permissionsToInsert.removeAll(currentPermissions);

		for (final Permission permission : permissionsToInsert) {
			this.insert(permission);
		}
	}
}
