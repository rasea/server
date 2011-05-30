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
package org.rasea.ui.matrix;

import java.util.ArrayList;
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
import org.rasea.core.service.AuthorizationService;
import org.rasea.core.service.OperationService;
import org.rasea.core.service.ResourceService;
import org.rasea.core.service.RoleService;
import org.rasea.ui.annotation.Home;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractMatrix;

@Name("resourceMatrix")
@Title("org.rasea.resource.resource")
@Home(listView = "/listResource.xhtml", editView = "/editResourceAuthorization.xhtml")
public class ResourceMatrix extends AbstractMatrix<Resource, Role, Operation, Boolean> {

	private static final long serialVersionUID = -3197125311669990055L;

	@In
	private AuthorizationService authorizationService;

	@In
	private Application currentApplication;

	@In
	private OperationService operationService;

	private List<Boolean> options;

	@In
	private ResourceService resourceService;

	@In
	private RoleService roleService;

	@Override
	public String getName() {
		return "resource";
	}

	public List<Boolean> getOptions() {
		if (this.options == null) {
			this.options = new ArrayList<Boolean>();
			this.options.add(Boolean.TRUE);
			this.options.add(Boolean.FALSE);
		}

		return this.options;
	}

	@Override
	protected void handlePersist(final Role row, final Operation col, final Boolean cell) throws Exception {
		final Permission permission = new Permission(this.getInstance(), col);
		final Authorization authorization = new Authorization(permission, row, cell);

		this.authorizationService.insert(authorization);
	}

	@Override
	protected void handleRemove(final Role row, final Operation col, final Boolean cell) throws Exception {
		final Permission permission = new Permission(this.getInstance(), col);
		final Authorization authorization = new Authorization(permission, row, cell);

		this.authorizationService.delete(authorization);
	}

	@Override
	protected void handleUpdate(final Role row, final Operation col, final Boolean cell) throws Exception {
		final Permission permission = new Permission(this.getInstance(), col);
		final Authorization authorization = new Authorization(permission, row, cell);

		this.authorizationService.update(authorization);
	}

	@Override
	protected void handleUpdateCol(final Operation col) throws AbstractApplicationException {
		this.operationService.update(col);
	}

	@Override
	protected void handleUpdateRow(final Role row) throws AbstractApplicationException {
		this.roleService.update(row);
	}

	@Override
	protected Boolean loadCell(final Role row, final Operation col) throws Exception {
		Boolean cell;

		final Permission permission = new Permission(this.getInstance(), col);
		final Authorization authz = this.authorizationService.load(permission, row);

		if (authz == null) {
			cell = null;
		} else {
			cell = authz.isAllowed();
		}

		return cell;
	}

	@Override
	protected List<Operation> loadCols() throws Exception {
		return this.operationService.find(this.getInstance());
	}

	@Override
	protected Resource loadInstance() throws Exception {
		return this.resourceService.load((Long) this.getId());
	}

	@Override
	protected List<Role> loadRows() throws Exception {
		return this.roleService.find(this.currentApplication);
	}
}
