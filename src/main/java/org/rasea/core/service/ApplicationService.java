package org.rasea.core.service;

import java.util.List;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Resource;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.DuplicatedException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.exception.StoreException;
import org.rasea.core.manager.ApplicationManager;
import org.rasea.core.util.Constants;
import org.rasea.core.util.ServiceValidator;
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("applicationService")
public class ApplicationService extends AbstractService {

	private static final long serialVersionUID = 8014609131154268246L;

	@In
	private ApplicationManager applicationManager;

	@In
	private OperationService operationService;

	@In
	private ResourceService resourceService;

	@In
	private RoleService roleService;

	@In
	private ServiceValidator serviceValidator;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Application application) throws RequiredException, StoreException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());

		for (final Role role : this.roleService.find(application)) {
			this.roleService.delete(role);
		}

		for (final Resource resource : this.resourceService.find(application)) {
			this.resourceService.delete(resource);
		}

		for (final Operation operation : this.operationService.find(application)) {
			this.operationService.delete(operation);
		}

		this.applicationManager.delete(application);
	}

	public Application find(final String name) throws RequiredException {
		this.serviceValidator.validateRequired("name", name);

		return this.applicationManager.find(name);
	}

	public List<Application> find(final User owner) throws RequiredException {
		return this.applicationManager.find(owner);
	}

	public List<Application> findAll() throws RequiredException {
		List<Application> result = null;

		final boolean show = Identity.instance().hasPermission("application", Constants.DEFAULT_OPERATION_SHOW);

		if (show) {
			result = this.applicationManager.findAll();

		} else {
			final Credentials credentials = (Credentials) Component.getInstance(Credentials.class);
			String username = null;

			if (credentials != null) {
				username = credentials.getUsername();
			}

			result = this.find(new User(username));
		}

		return result;
	}

	public List<Application> findByFilter(final String filter) throws RequiredException {
		final List<Application> result;

		if (filter == null) {
			result = this.findAll();
		} else {
			result = this.applicationManager.findByFilter(filter);
		}

		return result;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Application application) throws RequiredException, DuplicatedException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.name", application.getName());
		this.serviceValidator.validateRequired("application.displayName", application.getDisplayName());
		this.serviceValidator.validateDuplicateName(application);

		this.applicationManager.insert(application);
	}

	public Application load(final Long id) throws RequiredException {
		this.serviceValidator.validateRequired("id", id);

		return this.applicationManager.load(id);
	}

	//	
	// @Transactional(TransactionPropagationType.REQUIRED)
	// private void replicate(final Application application) throws
	// RequiredException,
	// DuplicatedException {
	// String name;
	// String displayName;
	//		
	// Operation auxOperation;
	// for (final Operation operation :
	// this.operationService.find(this.defaultApplication)) {
	// name = SeamResourceBundle.getBundle().getString(operation.getName());
	//			
	// auxOperation = operation.clone();
	// auxOperation.setId(null);
	// auxOperation.setApplication(application);
	// auxOperation.setName(name);
	//			
	// this.operationService.insert(auxOperation);
	// }
	//		
	// Role auxRole;
	// for (final Role role : this.roleService.find(this.defaultApplication)) {
	// try {
	// displayName =
	// SeamResourceBundle.getBundle().getString(role.getDescription());
	//				
	// auxRole = role.clone();
	// auxRole.setId(null);
	// auxRole.setApplication(application);
	// auxRole.setDescription(displayName);
	// auxRole.setMembers(null);
	//				
	// this.roleService.insert(auxRole);
	//				
	// } catch (final MissingResourceException cause) {
	// auxRole = null;
	// }
	// }
	// }

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final Application application) throws RequiredException, DuplicatedException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());
		this.serviceValidator.validateRequired("application.name", application.getName());
		this.serviceValidator.validateRequired("application.displayName", application.getDisplayName());
		this.serviceValidator.validateDuplicateName(application);

		this.applicationManager.update(application);
	}
}
