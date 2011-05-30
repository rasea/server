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
	protected List<Resource> handleResultList() throws AbstractApplicationException {
		return this.resourceService.findByFilter(this.currentApplication, this.getSearchString());
	}

	@Override
	protected void handleUpdate() throws AbstractApplicationException {
		this.resourceService.update(this.getDataModelSelection());
	}
}
