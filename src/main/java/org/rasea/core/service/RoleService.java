package org.rasea.core.service;

import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Resource;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.DuplicatedException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.exception.StoreException;
import org.rasea.core.manager.RoleManager;
import org.rasea.core.util.ServiceValidator;
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("roleService")
public class RoleService extends AbstractService {

	private static final long serialVersionUID = 7227170173613834972L;

	@In
	private MemberService memberService;

	@In
	private RoleManager roleManager;

	@In
	private ServiceValidator serviceValidator;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Role role) throws RequiredException, StoreException {
		this.serviceValidator.validateRequired("role", role);
		this.serviceValidator.validateRequired("role.id", role.getId());

		for (final User user : this.memberService.find(role)) {
			this.memberService.delete(role, user);
		}

		this.roleManager.delete(role);
	}

	public List<Role> find(final Application application) throws RequiredException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());

		return this.roleManager.find(application);
	}

	public Role find(final Application application, final String name) throws RequiredException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());
		this.serviceValidator.validateRequired("name", name);

		return this.roleManager.find(application, name);
	}

	public List<Role> find(final Resource resource) throws RequiredException {
		this.serviceValidator.validateRequired("resource", resource);
		this.serviceValidator.validateRequired("resource.id", resource.getId());

		return this.roleManager.find(resource);
	}

	public List<Role> findByFilter(final Application application, final String filter) throws RequiredException {
		this.serviceValidator.validateRequired("application", application);

		final List<Role> result;

		if (filter == null) {
			result = this.find(application);
		} else {
			result = this.roleManager.findByFilter(application, filter);
		}

		return result;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Role role) throws RequiredException, DuplicatedException {
		this.serviceValidator.validateRequired("role", role);
		this.serviceValidator.validateRequired("role.name", role.getName());
		this.serviceValidator.validateRequired("role.displayName", role.getDisplayName());
		this.serviceValidator.validateRequired("role.application", role.getApplication());
		this.serviceValidator.validateDuplicateName(role);

		this.roleManager.insert(role);

		if (role.getMembers() != null) {
			for (final User user : role.getMembers()) {
				this.memberService.insert(role, user);
			}
		}
	}

	public Role load(final Long id) throws RequiredException {
		this.serviceValidator.validateRequired("id", id);

		return this.roleManager.load(id);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final Role role) throws DuplicatedException, RequiredException {
		this.serviceValidator.validateRequired("role", role);
		this.serviceValidator.validateRequired("role.id", role.getId());
		this.serviceValidator.validateRequired("role.name", role.getName());
		this.serviceValidator.validateRequired("role.displayName", role.getDisplayName());
		this.serviceValidator.validateRequired("role.application", role.getApplication());
		this.serviceValidator.validateDuplicateName(role);

		this.roleManager.update(role);
	}
}
