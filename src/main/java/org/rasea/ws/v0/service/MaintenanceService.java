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
package org.rasea.ws.v0.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.rasea.core.util.Constants;
import org.rasea.ws.v0.exception.WebServiceException;
import org.rasea.ws.v0.type.Application;
import org.rasea.ws.v0.type.Operation;
import org.rasea.ws.v0.type.RaseaServiceHeader;
import org.rasea.ws.v0.type.Resource;
import org.rasea.ws.v0.type.TypeParser;

@WebService(targetNamespace = "http://rasea.org/ws/Maintenance", name = "Maintenance", serviceName = "MaintenanceService")
@SOAPBinding(parameterStyle = ParameterStyle.WRAPPED, style = Style.DOCUMENT, use = Use.LITERAL)
public class MaintenanceService extends AbstractWebService {

	@WebMethod
	public void addApplication(@WebParam(name = "application") final Application application,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "application", Constants.DEFAULT_OPERATION_INSERT);

			final org.rasea.core.entity.Application app = TypeParser.parse(application);
			this.getApplicationService().insert(app);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void addOperation(@WebParam(name = "application") final String application, @WebParam(name = "operation") final Operation operation,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "operation", Constants.DEFAULT_OPERATION_INSERT);

			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Operation op = TypeParser.parse(operation);
			op.setApplication(app);
			this.getOperationService().insert(op);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	// TODO Mudar essa assinatura para receber um permission e pronto
	@WebMethod
	public void addPermission(@WebParam(name = "application") final String application, @WebParam(name = "resource") final Resource resource,
			@WebParam(name = "operation") final Operation operation,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

		try {
			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Resource res = this.getResource(app, resource.getName());
			final org.rasea.core.entity.Operation op = this.getOperation(app, operation.getName());

			final org.rasea.core.entity.Permission perm = new org.rasea.core.entity.Permission();
			perm.setResource(res);
			perm.setOperation(op);

			this.getPermissionService().insert(perm);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void addResource(@WebParam(name = "application") final String application, @WebParam(name = "resource") final Resource resource,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "resource", Constants.DEFAULT_OPERATION_INSERT);

			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Resource res = TypeParser.parse(resource);
			res.setApplication(app);
			this.getResourceService().insert(res);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	@WebResult(name = "application")
	public Application applicationDetail(@WebParam(name = "application") final String application,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		Application result = null;

		try {
			final org.rasea.core.entity.Application app = this.getApplicationService().find(application);

			if (app != null) {
				result = TypeParser.parse(app);
			}

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	@WebMethod
	public void assignOwner(@WebParam(name = "application") final String application, @WebParam(name = "username") final String username,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "owner", Constants.DEFAULT_OPERATION_UPDATE);

			final org.rasea.extensions.entity.User usr = this.getUser(username);
			final org.rasea.core.entity.Application app = this.getApplication(application);
			this.getOwnerService().insert(app, usr);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deassignOwner(@WebParam(name = "application") final String application, @WebParam(name = "username") final String username,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "owner", Constants.DEFAULT_OPERATION_UPDATE);

			final org.rasea.extensions.entity.User usr = this.getUser(username);
			final org.rasea.core.entity.Application app = this.getApplication(application);
			this.getOwnerService().delete(app, usr);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	@WebResult(name = "name")
	@Deprecated
	public String defaultApplicationName(@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header)
			throws WebServiceException {
		this.checkAuthentication(header);
		// this.checkPermission(header, "application",
		// Constants.DEFAULT_OPERATION_SHOW);

		return this.getDefaultApplication().getName();
	}

	@WebMethod
	public void deleteApplication(@WebParam(name = "application") final String application,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "application", Constants.DEFAULT_OPERATION_DELETE);

			final org.rasea.core.entity.Application app = this.getApplication(application);
			this.getApplicationService().delete(app);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deleteOperation(@WebParam(name = "application") final String application, @WebParam(name = "operation") final String operation,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "operation", Constants.DEFAULT_OPERATION_DELETE);

			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Operation op = this.getOperation(app, operation);
			this.getOperationService().delete(op);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deletePermission(@WebParam(name = "application") final String application, @WebParam(name = "resource") final Resource resource,
			@WebParam(name = "operation") final Operation operation,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
	}

	@WebMethod
	public void deleteResource(@WebParam(name = "application") final String application, @WebParam(name = "resource") final String resource,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "resource", Constants.DEFAULT_OPERATION_DELETE);

			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Resource res = this.getResource(app, resource);
			this.getResourceService().delete(res);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	@WebResult(name = "operation")
	public Operation operationDetail(@WebParam(name = "application") final String application, @WebParam(name = "operation") final String operation,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		Operation result = null;

		try {
			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Operation op = this.getOperationService().find(app, operation);

			if (op != null) {
				result = TypeParser.parse(op);
			}

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	@WebMethod
	@WebResult(name = "resource")
	public Resource resourceDetail(@WebParam(name = "application") final String application, @WebParam(name = "resource") final String resource,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		Resource result = null;

		try {
			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Resource res = this.getResourceService().find(app, resource);

			if (res != null) {
				result = TypeParser.parse(res);
			}

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	@WebMethod
	@WebResult(name = "operations")
	public List<Operation> resourceOperations(@WebParam(name = "application") final String application,
			@WebParam(name = "resource") final String resource, @WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header)
			throws WebServiceException {

		this.checkAuthentication(header);
		this.checkPermission(header, "resource", Constants.DEFAULT_OPERATION_SHOW);

		List<Operation> result = null;

		try {
			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Resource res = this.getResource(app, resource);
			final List<org.rasea.core.entity.Operation> ops = this.getOperationService().find(res);

			result = new ArrayList<Operation>();
			for (final org.rasea.core.entity.Operation op : ops) {
				result.add(TypeParser.parse(op));
			}

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}
}
