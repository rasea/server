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
package org.rasea.core.configuration; // NOPMD by cleverson on 05/12/09 15:29

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Authorization;
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Permission;
import org.rasea.core.entity.Resource;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.DuplicatedException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.service.ApplicationService;
import org.rasea.core.service.AuthorizationService;
import org.rasea.core.service.MemberService;
import org.rasea.core.service.OperationService;
import org.rasea.core.service.OwnerService;
import org.rasea.core.service.PermissionService;
import org.rasea.core.service.ResourceService;
import org.rasea.core.service.RoleService;
import org.rasea.core.util.Constants;
import org.rasea.extensions.entity.User;

@Startup
@Name("org.rasea.core.configuration.init")
@Scope(ScopeType.APPLICATION)
public class Init {

	private static final String DELETE = Constants.DEFAULT_OPERATION_DELETE;

	private static final String EXPORT = Constants.DEFAULT_OPERATION_EXPORT;

	private static final String IMPORT = Constants.DEFAULT_OPERATION_IMPORT;

	private static final String INSERT = Constants.DEFAULT_OPERATION_INSERT;

	private static final String SHOW = Constants.DEFAULT_OPERATION_SHOW;

	private static final String UPDATE = Constants.DEFAULT_OPERATION_UPDATE;

	@In
	private ApplicationService applicationService;

	@In
	private AuthorizationService authorizationService;

	@Out
	private Application defaultApplication;

	private Map<String, Operation> defaultOperations;

	private List<Resource> defaultResources;

	@Out
	private Role defaultRole;

	@Out
	private User defaultUser;

	@In
	private MemberService memberService;

	@In
	private OperationService operationService;

	@In
	private OwnerService ownerService;

	@In
	private PermissionService permissionService;

	@In
	private ResourceService resourceService;

	@In
	private RoleService roleService;

	@In
	private Settings settings;

	@Create
	@Transactional(TransactionPropagationType.REQUIRED)
	public void create() throws RequiredException, DuplicatedException {
		this.initUser();
		this.initApplication();
		this.initOwners();
		this.initRole();
		this.initMembers();
		this.initOperation();
		this.initResource();
		this.initPermissions();
		this.initAuthorizations();
	}

	private void initApplication() throws RequiredException, DuplicatedException {
		final String APP_NAME = "rasea-server";
		final String APP_DESCRIPTION = this.settings.getApplication().getDescription();

		Application application = null;

		application = this.applicationService.find("org.rasea.application.rasea.name");
		if (application != null) {
			application.setName(APP_NAME);
			this.applicationService.update(application);
		}

		application = this.applicationService.find("rasea");
		if (application != null) {
			application.setName(APP_NAME);
			this.applicationService.update(application);
		}

		application = this.applicationService.find(APP_NAME);
		if (application == null) {
			application = new Application(APP_NAME, APP_DESCRIPTION);
			this.applicationService.insert(application);

		} else {
			if (!APP_DESCRIPTION.equals(application.getDisplayName())) {
				application.setDisplayName(APP_DESCRIPTION);
				this.applicationService.update(application);
			}
		}

		this.defaultApplication = application;
	}

	private void initAuthorizations() throws RequiredException {
		Authorization authorization;

		for (final Resource resource : this.defaultResources) {
			for (final Operation operation : resource.getOperations()) {
				final Permission permission = new Permission(resource, operation);

				authorization = this.authorizationService.load(permission, this.defaultRole);

				if (authorization != null && !authorization.isAllowed()) {
					this.authorizationService.delete(authorization);
					authorization = null;
				}

				if (authorization == null) {
					this.authorizationService.insert(new Authorization(permission, this.defaultRole, true));
				}
			}
		}
	}

	private void initMembers() throws RequiredException, DuplicatedException {
		final List<Role> memberOf = this.memberService.find(this.defaultApplication, this.defaultUser);

		if (memberOf == null || !memberOf.contains(this.defaultRole)) {
			this.memberService.insert(this.defaultRole, this.defaultUser);
		}
	}

	private void initOperation() throws RequiredException, DuplicatedException {
		this.defaultOperations = new Hashtable<String, Operation>();
		final Map<String, Operation> operations = new Hashtable<String, Operation>();

		final Operation _insert = new Operation(this.defaultApplication, INSERT);
		_insert.setDisplayName("org.rasea.operation.insert");

		final Operation _update = new Operation(this.defaultApplication, UPDATE);
		_update.setDisplayName("org.rasea.operation.update");

		final Operation _delete = new Operation(this.defaultApplication, DELETE);
		_delete.setDisplayName("org.rasea.operation.delete");

		final Operation _show = new Operation(this.defaultApplication, SHOW);
		_show.setDisplayName("org.rasea.operation.show");

		final Operation _import = new Operation(this.defaultApplication, IMPORT);
		_import.setDisplayName("org.rasea.operation.import");

		final Operation _export = new Operation(this.defaultApplication, EXPORT);
		_export.setDisplayName("org.rasea.operation.export");

		operations.put(Init.INSERT, _insert);
		operations.put(Init.UPDATE, _update);
		operations.put(Init.DELETE, _delete);
		operations.put(Init.SHOW, _show);
		operations.put(Init.IMPORT, _import);
		operations.put(Init.EXPORT, _export);

		Operation persisted;
		Operation aux;
		for (final Entry<String, Operation> entry : operations.entrySet()) {
			aux = entry.getValue();
			persisted = this.operationService.find(this.defaultApplication, aux.getName());

			if (persisted == null) {
				this.operationService.insert(aux);
				persisted = aux;
			}

			this.defaultOperations.put(entry.getKey(), persisted);
		}

		for (final Operation operation : this.operationService.find(this.defaultApplication)) {
			if (!this.defaultOperations.containsValue(operation)) {
				this.operationService.delete(operation);
			}
		}
	}

	private void initOwners() throws RequiredException, DuplicatedException {
		final List<Application> ownerships = this.ownerService.find(this.defaultUser);

		if (ownerships == null || !ownerships.contains(this.defaultApplication)) {
			this.ownerService.insert(this.defaultApplication, this.defaultUser);
		}
	}

	private void initPermissions() throws RequiredException {
		Permission permission;
		for (final Resource resource : this.defaultResources) {
			for (final Operation operation : resource.getOperations()) {
				permission = this.permissionService.load(resource, operation);

				if (permission == null) {
					this.permissionService.insert(new Permission(resource, operation));
				}
			}
		}
	}

	private void initResource() throws RequiredException, DuplicatedException {
		this.defaultResources = new ArrayList<Resource>();
		final List<Resource> resources = new ArrayList<Resource>();
		Resource resource;

		resource = new Resource(this.defaultApplication, "application", "org.rasea.resource.application");
		resource.addOperation(this.defaultOperations.get(Init.INSERT));
		resource.addOperation(this.defaultOperations.get(Init.UPDATE));
		resource.addOperation(this.defaultOperations.get(Init.DELETE));
		resource.addOperation(this.defaultOperations.get(Init.SHOW));
		resource.addOperation(this.defaultOperations.get(Init.IMPORT));
		resource.addOperation(this.defaultOperations.get(Init.EXPORT));
		resources.add(resource);

		resource = new Resource(this.defaultApplication, "operation", "org.rasea.resource.operation");
		resource.addOperation(this.defaultOperations.get(Init.INSERT));
		resource.addOperation(this.defaultOperations.get(Init.UPDATE));
		resource.addOperation(this.defaultOperations.get(Init.DELETE));
		resource.addOperation(this.defaultOperations.get(Init.SHOW));
		resources.add(resource);

		resource = new Resource(this.defaultApplication, "resource", "org.rasea.resource.resource");
		resource.addOperation(this.defaultOperations.get(Init.INSERT));
		resource.addOperation(this.defaultOperations.get(Init.UPDATE));
		resource.addOperation(this.defaultOperations.get(Init.DELETE));
		resource.addOperation(this.defaultOperations.get(Init.SHOW));
		resources.add(resource);

		resource = new Resource(this.defaultApplication, "authorization", "org.rasea.resource.authorization");
		resource.addOperation(this.defaultOperations.get(Init.UPDATE));
		resource.addOperation(this.defaultOperations.get(Init.SHOW));
		resources.add(resource);

		resource = new Resource(this.defaultApplication, "role", "org.rasea.resource.role");
		resource.addOperation(this.defaultOperations.get(Init.INSERT));
		resource.addOperation(this.defaultOperations.get(Init.UPDATE));
		resource.addOperation(this.defaultOperations.get(Init.DELETE));
		resource.addOperation(this.defaultOperations.get(Init.SHOW));
		resources.add(resource);

		resource = new Resource(this.defaultApplication, "user", "org.rasea.resource.user");
		resource.addOperation(this.defaultOperations.get(Init.INSERT));
		resource.addOperation(this.defaultOperations.get(Init.UPDATE));
		resource.addOperation(this.defaultOperations.get(Init.DELETE));
		resource.addOperation(this.defaultOperations.get(Init.SHOW));
		resources.add(resource);

		resource = new Resource(this.defaultApplication, "member", "org.rasea.resource.member");
		resource.addOperation(this.defaultOperations.get(Init.UPDATE));
		resource.addOperation(this.defaultOperations.get(Init.SHOW));
		resources.add(resource);

		resource = new Resource(this.defaultApplication, "profile", "org.rasea.resource.profile");
		resource.addOperation(this.defaultOperations.get(Init.UPDATE));
		resource.addOperation(this.defaultOperations.get(Init.SHOW));
		resources.add(resource);

		resource = new Resource(this.defaultApplication, "settings", "org.rasea.resource.settings");
		resource.addOperation(this.defaultOperations.get(Init.UPDATE));
		resource.addOperation(this.defaultOperations.get(Init.SHOW));
		resources.add(resource);

		resource = new Resource(this.defaultApplication, "owner", "org.rasea.resource.owner");
		resource.addOperation(this.defaultOperations.get(Init.UPDATE));
		resource.addOperation(this.defaultOperations.get(Init.SHOW));
		resources.add(resource);

		Resource persisted;
		for (final Resource aux : resources) {
			persisted = this.resourceService.find(this.defaultApplication, aux.getName());

			if (persisted == null) {
				this.resourceService.insert(aux);
				persisted = aux;

			} else if (!aux.getDisplayName().equals(persisted.getDisplayName())) {
				persisted.setDisplayName(aux.getDisplayName());
				this.resourceService.update(persisted);
			}

			persisted.setOperations(aux.getOperations());
			this.defaultResources.add(persisted);
		}
	}

	private void initRole() throws RequiredException, DuplicatedException {
		final String ROLE_NAME = "admin";
		final String ROLE_DESCRIPTION = "org.rasea.role.admin.displayName";

		Role role = null;

		role = this.roleService.find(this.defaultApplication, "org.rasea.role.admin");
		if (role != null) {
			role.setName(ROLE_NAME);
			this.roleService.update(role);
		}

		role = this.roleService.find(this.defaultApplication, ROLE_NAME);
		if (role == null) {
			role = new Role(this.defaultApplication, ROLE_NAME);
			role.setDisplayName(ROLE_DESCRIPTION);
			role.setEnabled(true);
			this.roleService.insert(role);

		} else {
			boolean changed = false;

			if (ROLE_DESCRIPTION.equals(role.getDisplayName())) {
				changed = true;
				role.setDisplayName(ROLE_DESCRIPTION);
			}

			if (!role.isEnabled()) {
				changed = true;
				role.setEnabled(true);
			}

			if (changed) {
				this.roleService.update(role);
			}
		}

		this.defaultRole = role;
	}

	private void initUser() {
		final String ADMIN_USERNAME = this.settings.getAdmin().getUsername();
		final String ADMIN_DISPLAY_NAME = "org.rasea.admin.displayName";
		final String ADMIN_EMAIL = this.settings.getAdmin().getEmail();

		final User admin = new User(ADMIN_USERNAME);
		admin.setDisplayName(ADMIN_DISPLAY_NAME);
		admin.setEmail(ADMIN_EMAIL);
		admin.setEnabled(true);

		this.defaultUser = admin;
	}
}
