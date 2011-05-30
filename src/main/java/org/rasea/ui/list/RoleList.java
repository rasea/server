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
import org.rasea.core.entity.Role;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.service.RoleService;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractList;

@Name("roleList")
@Title("org.rasea.resource.role")
public class RoleList extends AbstractList<Role> {

	private static final long serialVersionUID = 8196809827368355031L;

	@In
	private Application currentApplication;

	@In
	private RoleService roleService;

	@Override
	protected List<Role> handleResultList() throws AbstractApplicationException {
		return this.roleService.findByFilter(this.currentApplication, this.getSearchString());
	}

	@Override
	protected void handleUpdate() throws AbstractApplicationException {
		this.roleService.update(this.getDataModelSelection());
	}
}
