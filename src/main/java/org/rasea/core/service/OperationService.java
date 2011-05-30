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
package org.rasea.core.service;

import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Resource;
import org.rasea.core.exception.DuplicatedException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.manager.OperationManager;
import org.rasea.core.util.ServiceValidator;

@AutoCreate
@Name("operationService")
public class OperationService extends AbstractService {

	private static final long serialVersionUID = -7320231673851315639L;

	@In
	private OperationManager operationManager;

	@In
	private ServiceValidator serviceValidator;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Operation operation) throws RequiredException {
		this.serviceValidator.validateRequired("operation", operation);
		this.serviceValidator.validateRequired("operation.id", operation.getId());

		this.operationManager.delete(operation);
	}

	public List<Operation> find(final Application application) throws RequiredException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());

		return this.operationManager.find(application);
	}

	public Operation find(final Application application, final String name) throws RequiredException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());
		this.serviceValidator.validateRequired("name", name);

		return this.operationManager.find(application, name);
	}

	public List<Operation> find(final Resource resource) throws RequiredException {
		this.serviceValidator.validateRequired("resource", resource);
		this.serviceValidator.validateRequired("resource.id", resource.getId());

		return this.operationManager.find(resource);
	}

	public List<Operation> findByFilter(final Application application, final String filter) throws RequiredException {
		this.serviceValidator.validateRequired("application", application);

		final List<Operation> result;

		if (filter == null) {
			result = this.find(application);
		} else {
			result = this.operationManager.findByFilter(application, filter);
		}

		return result;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Operation operation) throws RequiredException, DuplicatedException {
		this.serviceValidator.validateRequired("operation", operation);
		this.serviceValidator.validateRequired("operation.name", operation.getName());
		this.serviceValidator.validateRequired("operation.displayName", operation.getDisplayName());
		this.serviceValidator.validateRequired("operation.application", operation.getApplication());
		this.serviceValidator.validateDuplicateName(operation);

		this.operationManager.insert(operation);
	}

	public Operation load(final Long id) throws RequiredException {
		this.serviceValidator.validateRequired("id", id);

		return this.operationManager.load(id);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final Operation operation) throws RequiredException, DuplicatedException {
		this.serviceValidator.validateRequired("operation", operation);
		this.serviceValidator.validateRequired("operation.id", operation.getId());
		this.serviceValidator.validateRequired("operation.name", operation.getName());
		this.serviceValidator.validateRequired("operation.displayName", operation.getDisplayName());
		this.serviceValidator.validateDuplicateName(operation);

		this.operationManager.update(operation);
	}
}
