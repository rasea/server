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
package org.rasea.ws.v1.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.rasea.core.entity.Application;
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Permission;
import org.rasea.core.entity.Resource;
import org.rasea.core.util.Constants;
import org.rasea.extensions.entity.User;
import org.rasea.ws.v1.exception.WebServiceException;
import org.rasea.ws.v1.request.ApplicationNameRequest;
import org.rasea.ws.v1.request.ApplicationRequest;
import org.rasea.ws.v1.request.OperationNameRequest;
import org.rasea.ws.v1.request.OperationRequest;
import org.rasea.ws.v1.request.PermissionRequest;
import org.rasea.ws.v1.request.ResourceNameRequest;
import org.rasea.ws.v1.request.ResourceRequest;
import org.rasea.ws.v1.request.UserNameRequest;
import org.rasea.ws.v1.response.OperationsResponse;
import org.rasea.ws.v1.type.ApplicationType;
import org.rasea.ws.v1.type.CredentialsType;
import org.rasea.ws.v1.type.OperationType;
import org.rasea.ws.v1.type.ResourceType;

@WebService(name = "Maintenance_v1", targetNamespace = "http://rasea.org/ps/wsdl/Maintenance_v1", serviceName = "MaintenanceService_v1", portName = "MaintenancePort_v1")
@SOAPBinding(parameterStyle = ParameterStyle.BARE, style = Style.DOCUMENT, use = Use.LITERAL)
public class MaintenanceService extends AbstractWebService {

	@WebMethod
	public void addApplication(final ApplicationRequest param, @WebParam(header = true) final CredentialsType credentials) throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "application", Constants.DEFAULT_OPERATION_INSERT);

			this.getApplicationService().insert(param.getApplication().parse());

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void addOperation(final OperationRequest param, @WebParam(header = true) final CredentialsType credentials) throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "operation", Constants.DEFAULT_OPERATION_INSERT);

			final Application application = this.getApplication(param.getApplicationName());
			Operation operation = param.getOperation().parse();
			operation.setApplication(application);

			this.getOperationService().insert(operation);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void addPermission(final PermissionRequest param, @WebParam(header = true) final CredentialsType credentials) throws WebServiceException {

		try {
			final Application application = this.getApplication(param.getApplicationName());
			final Resource resource = this.getResource(application, param.getPermission().getResourceName());
			final Operation operation = this.getOperation(application, param.getPermission().getOperationName());
			final Permission permission = new Permission(resource, operation);

			this.getPermissionService().insert(permission);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void addResource(final ResourceRequest param, @WebParam(header = true) final CredentialsType credentials) throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "resource", Constants.DEFAULT_OPERATION_INSERT);

			final Application application = this.getApplication(param.getApplicationName());
			Resource resource = param.getResource().parse();
			resource.setApplication(application);

			this.getResourceService().insert(resource);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	@WebResult(name = "application")
	public ApplicationType applicationDetail(final ApplicationNameRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		ApplicationType result = null;

		try {
			Application application = this.getApplication(param.getApplicationName());
			result = ApplicationType.parse(application);

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	@WebMethod
	public void assignOwner(final UserNameRequest param, @WebParam(header = true) final CredentialsType credentials) throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "owner", Constants.DEFAULT_OPERATION_UPDATE);

			final User user = this.getUser(param.getUsername());
			final Application application = this.getApplication(param.getApplicationName());
			this.getOwnerService().insert(application, user);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deassignOwner(final UserNameRequest param, @WebParam(header = true) final CredentialsType credentials) throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "owner", Constants.DEFAULT_OPERATION_UPDATE);

			final User user = this.getUser(param.getUsername());
			final Application application = this.getApplication(param.getApplicationName());
			this.getOwnerService().delete(application, user);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deleteApplication(final ApplicationNameRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "application", Constants.DEFAULT_OPERATION_DELETE);

			final Application application = this.getApplication(param.getApplicationName());
			this.getApplicationService().delete(application);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deleteOperation(final OperationNameRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "operation", Constants.DEFAULT_OPERATION_DELETE);

			final Application application = this.getApplication(param.getApplicationName());
			final Operation operation = this.getOperation(application, param.getOperationName());

			this.getOperationService().delete(operation);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deletePermission(final PermissionRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
	}

	@WebMethod
	public void deleteResource(final ResourceNameRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "resource", Constants.DEFAULT_OPERATION_DELETE);

			final Application application = this.getApplication(param.getApplicationName());
			final Resource resource = this.getResource(application, param.getResourceName());

			this.getResourceService().delete(resource);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	@WebResult(name = "operation")
	public OperationType operationDetail(final OperationNameRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		OperationType result = null;

		try {
			final Application application = this.getApplication(param.getApplicationName());
			Operation operation = this.getOperationService().find(application, param.getOperationName());
			result = OperationType.parse(operation);

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	@WebMethod
	@WebResult(name = "resource")
	public ResourceType resourceDetail(final ResourceNameRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		ResourceType result = null;

		try {
			final Application application = this.getApplication(param.getApplicationName());
			Resource resource = this.getResourceService().find(application, param.getResourceName());
			result = ResourceType.parse(resource);

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	@WebMethod
	@WebResult(name = "operations")
	public OperationsResponse resourceOperations(final ResourceNameRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {

		this.checkAuthentication(credentials);
		this.checkPermission(credentials, "resource", Constants.DEFAULT_OPERATION_SHOW);

		final OperationsResponse result = new OperationsResponse();

		try {
			final Application application = this.getApplication(param.getApplicationName());
			final Resource resource = this.getResource(application, param.getResourceName());
			final List<Operation> operations = this.getOperationService().find(resource);

			result.setOperations(OperationType.parse(operations));

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}
}
