package org.rasea.ui.matrix;

import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Authorization;
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Permission;
import org.rasea.core.entity.Resource;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.service.AuthorizationService;
import org.rasea.core.service.OperationService;
import org.rasea.core.service.PermissionService;
import org.rasea.core.service.ResourceService;
import org.rasea.core.service.RoleService;
import org.rasea.ui.annotation.Home;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractMatrix;

@Name("roleMatrix")
@Title("org.rasea.resource.role")
@Home(listView = "/listRole.xhtml", editView = "/editRoleAuthorization.xhtml")
public class RoleMatrix extends AbstractMatrix<Role, Resource, Operation, Boolean> {

	private static final long serialVersionUID = 346104712665320541L;

	@In
	private AuthorizationService authorizationService;

	@In
	private Application currentApplication;

	@In
	private OperationService operationService;

	private List<Permission> permissions;

	@In
	private PermissionService permissionService;

	@In
	private ResourceService resourceService;

	@In
	private RoleService roleService;

	@Override
	public String getName() {
		return "role";
	}

	@Override
	protected void handlePersist(final Resource row, final Operation col, final Boolean cell) throws Exception {
		final Permission permission = new Permission(row, col);
		final Authorization authorization = new Authorization(permission, this.getInstance(), cell);

		this.authorizationService.insert(authorization);
	}

	@Override
	protected void handleRemove(final Resource row, final Operation col, final Boolean cell) throws Exception {
		final Permission permission = new Permission(row, col);
		final Authorization authorization = new Authorization(permission, this.getInstance(), cell);

		this.authorizationService.delete(authorization);
	}

	@Override
	protected void handleUpdate(final Resource row, final Operation col, final Boolean cell) throws Exception {
		final Permission permission = new Permission(row, col);
		final Authorization authorization = new Authorization(permission, this.getInstance(), cell);

		this.authorizationService.update(authorization);
	}

	@Override
	protected void handleUpdateCol(final Operation col) throws AbstractApplicationException {
		this.operationService.update(col);
	}

	@Override
	protected void handleUpdateRow(final Resource row) throws AbstractApplicationException {
		this.resourceService.update(row);
	}

	public boolean isValidPermission(final Resource resource, final Operation operation) {
		boolean valid = false;

		try {
			if (this.permissions == null) {
				this.permissions = this.permissionService.find(this.currentApplication);
			}

			valid = this.permissions.contains(new Permission(resource, operation));

		} catch (final RequiredException cause) {
			this.performExceptionHandling(cause);
		}

		return valid;
	}

	@Override
	protected Boolean loadCell(final Resource row, final Operation col) throws Exception {
		Boolean cell;

		final Permission permission = this.permissionService.load(row, col);
		if (permission != null) {
			final Authorization authz = this.authorizationService.load(permission, this.getInstance());
			if (authz == null) {
				cell = null;
			} else {
				cell = authz.isAllowed();
			}
		} else {
			cell = null;// Colocar regra aki
		}

		return cell;
	}

	@Override
	protected List<Operation> loadCols() throws Exception {
		return this.operationService.find(this.currentApplication);
	}

	@Override
	protected Role loadInstance() throws Exception {
		return this.roleService.load((Long) this.getId());
	}

	@Override
	protected List<Resource> loadRows() throws Exception {
		return this.resourceService.find(this.currentApplication);
	}
}
