package org.rasea.ui.list;

import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Resource;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.service.ResourceService;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractList;

@Name("resourceList")
@Title("org.rasea.resource.resource")
public class ResourceList extends AbstractList<Resource> {

	private static final long serialVersionUID = 5395778738325210217L;

	@In
	private Application currentApplication;

	@In
	private ResourceService resourceService;

	@Override
	protected List<Resource> handleResultList()
			throws AbstractApplicationException {
		return this.resourceService.findByFilter(this.currentApplication, this
				.getSearchString());
	}

	@Override
	protected void handleUpdate() throws AbstractApplicationException {
		this.resourceService.update(this.getDataModelSelection());
	}
}
