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
 * License along with this library; if not, see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
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
		final Resource resource = this.resourceService.load((Long) this.getId());
		resource.setOperations(this.operationService.find(resource));

		return resource;
	}
}
