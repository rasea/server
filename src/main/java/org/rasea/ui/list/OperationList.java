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
import org.rasea.core.entity.Operation;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.service.OperationService;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractList;

@Name("operationList")
@Title("org.rasea.resource.operation")
public class OperationList extends AbstractList<Operation> {

	private static final long serialVersionUID = 7755843532064068899L;

	@In
	private Application currentApplication;

	@In
	private OperationService operationService;

	@Override
	protected List<Operation> handleResultList() throws AbstractApplicationException {
		return this.operationService.findByFilter(this.currentApplication, this.getSearchString());
	}

	@Override
	protected void handleUpdate() throws AbstractApplicationException {
		this.operationService.update(this.getDataModelSelection());
	}
}
