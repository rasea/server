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

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Operation;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.service.OperationService;
import org.rasea.ui.annotation.Home;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractEdit;

@Name("operationEdit")
@Title("org.rasea.resource.operation")
@Home(listView = "/listOperation.xhtml", editView = "/editOperation.xhtml")
public class OperationEdit extends AbstractEdit<Operation> {

	private static final long serialVersionUID = 1000502500862576140L;

	@In
	private Application currentApplication;

	@In
	private OperationService operationService;

	@Override
	protected Operation createInstance() {
		return new Operation(this.currentApplication);
	}

	@Override
	protected String handlePersist() throws AbstractApplicationException {
		this.operationService.insert(this.getInstance());
		return this.getListView();
	}

	@Override
	protected String handleRemove() throws AbstractApplicationException {
		this.operationService.delete(this.getInstance());
		return this.getListView();
	}

	@Override
	protected String handleUpdate() throws AbstractApplicationException {
		this.operationService.update(this.getInstance());
		return this.getListView();
	}

	@Override
	public Operation loadInstance() throws Exception {
		return this.operationService.load((Long) this.getId());
	}
}
