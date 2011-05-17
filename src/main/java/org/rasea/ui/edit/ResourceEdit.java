package org.rasea.ui.edit;

import java.util.ArrayList;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Resource;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.service.OperationService;
import org.rasea.core.service.PermissionService;
import org.rasea.core.service.ResourceService;
import org.rasea.ui.annotation.Home;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractEdit;

@Name("resourceEdit")
@Title("org.rasea.resource.resource")
@Home(listView = "/listResource.xhtml", editView = "/editResource.xhtml")
public class ResourceEdit extends AbstractEdit<Resource> {

	private static final long serialVersionUID = -5549146220431519528L;

	@In
	private Application currentApplication;

	@In
	private OperationService operationService;

	@In
	private PermissionService permissionService;

	@In
	private ResourceService resourceService;

	@Override
	public Resource createInstance() {
		final Resource resource = new Resource(this.currentApplication);
		resource.setOperations(new ArrayList<Operation>());

		return resource;
	}

	@Override
	public String handlePersist() throws AbstractApplicationException {
		this.resourceService.insert(this.getInstance());
		this.permissionService.update(this.getInstance());

		return this.getListView();
	}

	@Override
	public String handleRemove() throws AbstractApplicationException {
		this.resourceService.delete(this.getInstance());
		return this.getListView();
	}

	@Override
	public String handleUpdate() throws AbstractApplicationException {
		this.resourceService.update(this.getInstance());
		this.permissionService.update(this.getInstance());

		return this.getListView();

	}

	@Override
	public Resource loadInstance() throws Exception {
		final Resource resource = this.resourceService
				.load((Long) this.getId());
		resource.setOperations(this.operationService.find(resource));

		return resource;
	}
}
