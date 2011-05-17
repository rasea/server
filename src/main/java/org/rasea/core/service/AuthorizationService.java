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
import org.rasea.core.entity.Permission;
import org.rasea.core.entity.Resource;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.manager.AuthorizationManager;
import org.rasea.core.util.ServiceValidator;
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("authorizationService")
public class AuthorizationService extends AbstractService {

	private static final long serialVersionUID = 8962790667594491289L;

	@In
	private AuthorizationManager authorizationManager;

	@In
	private ServiceValidator serviceValidator;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Authorization authorization) throws RequiredException {
		this.validateDelete(authorization);

		final Authorization authz = this.load(authorization.getPermission(), authorization.getRole());

		if (authz != null) {
			this.authorizationManager.delete(authorization);
		}
	}

	public List<Authorization> find(final Application application) throws RequiredException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());

		return this.authorizationManager.find(application);
	}

	public List<Authorization> find(final Application application, final User user) throws RequiredException {
		this.validateFind(application, user);

		final List<Authorization> result = new ArrayList<Authorization>();
		final List<Authorization> allowed = this.authorizationManager.find(application, user, true);
		final List<Authorization> revoked = this.authorizationManager.find(application, user, false);

		boolean valid;
		for (final Authorization authorization : allowed) {
			valid = true;

			for (final Authorization deniedAuthorization : revoked) {
				if (authorization.getPermission().equals(deniedAuthorization.getPermission())) {
					valid = false;
					break;
				}
			}

			if (valid) {
				result.add(authorization);
			}
		}

		return result;
	}

	public List<Authorization> find(final Resource resource) throws RequiredException {
		this.serviceValidator.validateRequired("resource", resource);
		this.serviceValidator.validateRequired("resource.id", resource.getId());

		return this.authorizationManager.find(resource);
	}

	public List<Authorization> find(final Role role) throws RequiredException {

		this.serviceValidator.validateRequired("role", role);
		this.serviceValidator.validateRequired("role.id", role.getId());

		return this.authorizationManager.find(role);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Authorization authorization) throws RequiredException {
		this.validatePersist(authorization);

		final Authorization authz = this.load(authorization.getPermission(), authorization.getRole());

		if (authz == null) {
			this.authorizationManager.insert(authorization);

		} else if (authz.isAllowed() != authorization.isAllowed()) {
			this.authorizationManager.update(authorization);
		}
	}

	public Authorization load(final Permission permission, final Role role) throws RequiredException {
		this.validateFind(permission, role);

		return this.authorizationManager.find(permission, role);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final Authorization authorization) throws RequiredException {
		this.validatePersist(authorization);

		this.authorizationManager.update(authorization);
	}

	private void validateDelete(final Authorization authorization) throws RequiredException {
		this.serviceValidator.validateRequired("authorization", authorization);
		this.serviceValidator.validateRequired("authorization.permission", authorization.getPermission());
		this.serviceValidator.validateRequired("authorization.role", authorization.getRole());
		this.serviceValidator.validateRequired("authorization.permission.operation", authorization.getPermission().getOperation());
		this.serviceValidator.validateRequired("authorization.permission.resource", authorization.getPermission().getResource());
		this.serviceValidator.validateRequired("authorization.role.id", authorization.getRole().getId());
		this.serviceValidator.validateRequired("authorization.permission.operation.id", authorization.getPermission().getOperation().getId());
		this.serviceValidator.validateRequired("authorization.permission.resource.id", authorization.getPermission().getResource().getId());
	}

	private void validateFind(final Application application, final User user) throws RequiredException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());

		this.serviceValidator.validateRequired("user", user);
		this.serviceValidator.validateRequired("user.username", user.getName());
	}

	private void validateFind(final Permission permission, final Role role) throws RequiredException {
		this.serviceValidator.validateRequired("permission", permission);
		this.serviceValidator.validateRequired("permission.resource", permission.getResource());
		this.serviceValidator.validateRequired("permission.resource.id", permission.getResource().getId());
		this.serviceValidator.validateRequired("permission.operation", permission.getOperation());
		this.serviceValidator.validateRequired("permission.operation.id", permission.getOperation().getId());
		this.serviceValidator.validateRequired("role", role);
		this.serviceValidator.validateRequired("role.id", role.getId());
	}

	private void validatePersist(final Authorization authorization) throws RequiredException {
		this.serviceValidator.validateRequired("authorization", authorization);
		this.serviceValidator.validateRequired("authorization.permission", authorization.getPermission());
		this.serviceValidator.validateRequired("authorization.role", authorization.getRole());
		this.serviceValidator.validateRequired("authorization.permission.operation", authorization.getPermission().getOperation());
		this.serviceValidator.validateRequired("authorization.permission.resource", authorization.getPermission().getResource());
		this.serviceValidator.validateRequired("authorization.role.id", authorization.getRole().getId());
		this.serviceValidator.validateRequired("authorization.permission.operation.id", authorization.getPermission().getOperation().getId());
		this.serviceValidator.validateRequired("authorization.permission.resource.id", authorization.getPermission().getResource().getId());
	}
}
