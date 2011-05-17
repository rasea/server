package org.rasea.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.security.Credentials;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Authorization;
import org.rasea.core.entity.Data;
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Permission;
import org.rasea.core.entity.Resource;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.DuplicatedException;
import org.rasea.core.exception.NotSupportedException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.exception.StoreException;
import org.rasea.core.util.Constants;
import org.rasea.core.util.ServiceValidator;
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("dataService")
public class DataService extends AbstractService {

	private static final long serialVersionUID = -6567296554232001758L;

	@In
	private ApplicationService applicationService;

	@In
	private AuthorizationService authorizationService;

	@In
	private Credentials credentials;

	@In
	private Application defaultApplication;

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
	private ServiceValidator serviceValidator;

	@In
	private UserService userService;

	public Data find(final Application application) throws RequiredException {
		final ArrayList<Application> applications = new ArrayList<Application>();
		applications.add(application);

		return this.find(applications);
	}

	public Data find(final List<Application> applications) throws RequiredException {
		this.serviceValidator.validateRequired("applications", applications);
		final Data data = new Data();
		data.setVersion(Constants.CURRENT_DATA_VERSION);
		data.setGenerated(new Date());

		for (final Application application : applications) {
			this.serviceValidator.validateRequired("application", application);
			this.serviceValidator.validateRequired("application.id", application.getId());

			final Application app = this.applicationService.load(application.getId());
			app.setResources(this.resourceService.find(application));
			app.setOperations(this.operationService.find(application));
			app.setRoles(this.roleService.find(application));
			app.setPermissions(this.permissionService.find(application));
			app.setAuthorizations(this.authorizationService.find(application));
			data.addApplication(app);
		}

		return data;
	}

	public Data findAll() {
		Data result;

		try {
			final List<Application> applications = this.applicationService.findAll();
			result = this.find(applications);

		} catch (final RequiredException e) {
			result = null;
		}

		return result;
	}

	private void importApplication(final Application xml) throws RequiredException, DuplicatedException, StoreException {
		Application application = this.applicationService.find(xml.getName());

		if (application != null && !application.equals(this.defaultApplication)) {
			if (!xml.getName().equalsIgnoreCase(application.getName()) || !xml.getDisplayName().equalsIgnoreCase(application.getDisplayName())) {
				application.setName(xml.getName());
				application.setDisplayName(xml.getDisplayName());

				this.applicationService.update(application);
			}

		} else if (application != null && !application.equals(this.defaultApplication)) {
			application = new Application();
			application.setName(xml.getName());
			application.setDisplayName(xml.getDisplayName());

			this.applicationService.insert(application);
		}

		final User owner = this.userService.load(this.credentials.getUsername());
		this.ownerService.insert(application, owner);

		this.importResources(application, xml.getResources());
		this.importOperations(application, xml.getOperations());
		this.importRoles(application, xml.getRoles());
		this.importPermissions(application, xml.getPermissions());
		this.importAuthorizations(application, xml.getAuthorizations());
	}

	private void importAuthorizations(final Application application, final List<Authorization> list) throws RequiredException {
		Resource resource;
		Operation operation;
		Permission permission;
		Role role;
		Authorization persisted;

		for (final Authorization xml : list) {
			resource = this.resourceService.find(application, xml.getPermission().getResource().getName());
			operation = this.operationService.find(application, xml.getPermission().getOperation().getName());
			permission = this.permissionService.load(resource, operation);
			role = this.roleService.find(application, xml.getRole().getName());
			persisted = this.authorizationService.load(permission, role);

			if (persisted == null) {
				xml.setRole(role);
				xml.setPermission(permission);
				xml.setAllowed(xml.isAllowed());

				this.authorizationService.insert(xml);

			} else if (xml.isAllowed() != persisted.isAllowed()) {
				persisted.setAllowed(xml.isAllowed());
				this.authorizationService.update(persisted);
			}
		}
	}

	private void importOperations(final Application application, final List<Operation> list) throws RequiredException, DuplicatedException {

		Operation persisted;
		for (final Operation xml : list) {
			persisted = this.operationService.find(application, xml.getName());

			if (persisted == null) {
				xml.setApplication(application);
				this.operationService.insert(xml);

			} else if (!xml.getName().equalsIgnoreCase(persisted.getName())) {
				persisted.setName(xml.getName());
				this.operationService.update(persisted);
			}
		}
	}

	private void importPermissions(final Application application, final List<Permission> list) throws RequiredException {

		Resource resource;
		Operation operation;
		Permission persisted;

		for (final Permission xml : list) {
			resource = this.resourceService.find(application, xml.getResource().getName());
			operation = this.operationService.find(application, xml.getOperation().getName());
			persisted = this.permissionService.load(resource, operation);

			if (persisted == null) {
				xml.setResource(resource);
				xml.setOperation(operation);

				this.permissionService.insert(xml);
			}
		}
	}

	private void importResources(final Application application, final List<Resource> list) throws RequiredException, DuplicatedException {

		Resource persisted;
		for (final Resource xml : list) {
			persisted = this.resourceService.find(application, xml.getName());

			if (persisted == null) {
				xml.setApplication(application);
				this.resourceService.insert(xml);

			} else if (!xml.getName().equalsIgnoreCase(persisted.getName()) || !xml.getDisplayName().equalsIgnoreCase(persisted.getDisplayName())) {
				persisted.setName(xml.getName());
				persisted.setDisplayName(xml.getDisplayName());
				this.resourceService.update(persisted);
			}
		}
	}

	private void importRoles(final Application application, final List<Role> list) throws RequiredException, DuplicatedException {

		Role persisted;
		for (final Role xml : list) {
			persisted = this.roleService.find(application, xml.getName());

			if (persisted == null) {
				xml.setApplication(application);
				this.roleService.insert(xml);

			} else if (!xml.getName().equalsIgnoreCase(persisted.getName()) || !xml.getDisplayName().equalsIgnoreCase(persisted.getDisplayName())) {
				persisted.setName(xml.getName());
				persisted.setDisplayName(xml.getDisplayName());
				this.roleService.update(persisted);
			}
		}
	}

	@Transactional
	public void insert(final Data data) throws NotSupportedException, StoreException, RequiredException, DuplicatedException {
		// TODO Auto-generated method stub
	}

	@Transactional
	public void update(final Data data) throws NotSupportedException, StoreException, RequiredException, DuplicatedException {
		this.serviceValidator.validateRequired("data", data);
		this.serviceValidator.validateRequired("version", data.getVersion());
		this.serviceValidator.validateRequired("applications", data.getApplications());

		if (!Constants.CURRENT_DATA_VERSION.equals(data.getVersion())) {
			throw new NotSupportedException(data.getVersion());
		}

		for (final Application application : data.getApplications()) {
			this.importApplication(application);
		}
	}
}
