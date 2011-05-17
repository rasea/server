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
import org.rasea.core.entity.Credentials;
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

@WebService(name = "Maintenance_v1", targetNamespace = "http://rasea.org/ps/wsdl/Maintenance_v1", serviceName = "Maintenance_v1", portName = "MaintenancePort_v1")
@SOAPBinding(parameterStyle = ParameterStyle.BARE, style = Style.DOCUMENT, use = Use.LITERAL)
public class MaintenanceService extends AbstractWebService {

	@WebMethod
	public void addApplication(final ApplicationRequest param, @WebParam(header = true) final Credentials credentials) throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "application", Constants.DEFAULT_OPERATION_INSERT);

			this.getApplicationService().insert(param.getApplication());

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void addOperation(final OperationRequest param, @WebParam(header = true) final Credentials credentials) throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "operation", Constants.DEFAULT_OPERATION_INSERT);

			final Application application = this.getApplication(param.getApplicationName());
			param.getOperation().setApplication(application);

			this.getOperationService().insert(param.getOperation());

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void addPermission(final PermissionRequest param, @WebParam(header = true) final Credentials credentials) throws WebServiceException {

		try {
			final Application application = this.getApplication(param.getApplicationName());
			final Resource resource = this.getResource(application, param.getPermission().getResource().getName());
			final Operation operation = this.getOperation(application, param.getPermission().getOperation().getName());
			final Permission permission = new Permission(resource, operation);

			this.getPermissionService().insert(permission);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void addResource(final ResourceRequest param, @WebParam(header = true) final Credentials credentials) throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "resource", Constants.DEFAULT_OPERATION_INSERT);

			final Application application = this.getApplication(param.getApplicationName());
			param.getResource().setApplication(application);

			this.getResourceService().insert(param.getResource());

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	@WebResult(name = "application")
	public Application applicationDetail(final ApplicationNameRequest param, @WebParam(header = true) final Credentials credentials)
			throws WebServiceException {
		Application result = null;

		try {
			result = this.getApplication(param.getApplicationName());

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	@WebMethod
	public void assignOwner(final UserNameRequest param, @WebParam(header = true) final Credentials credentials) throws WebServiceException {
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
	public void deassignOwner(final UserNameRequest param, @WebParam(header = true) final Credentials credentials) throws WebServiceException {
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
	public void deleteApplication(final ApplicationNameRequest param, @WebParam(header = true) final Credentials credentials)
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
	public void deleteOperation(final OperationNameRequest param, @WebParam(header = true) final Credentials credentials) throws WebServiceException {
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
	public void deletePermission(final PermissionRequest param, @WebParam(header = true) final Credentials credentials) throws WebServiceException {
	}

	@WebMethod
	public void deleteResource(final ResourceNameRequest param, @WebParam(header = true) final Credentials credentials) throws WebServiceException {
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
	public Operation operationDetail(final OperationNameRequest param, @WebParam(header = true) final Credentials credentials)
			throws WebServiceException {
		Operation result = null;

		try {
			final Application application = this.getApplication(param.getApplicationName());
			result = this.getOperationService().find(application, param.getOperationName());

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	@WebMethod
	@WebResult(name = "resource")
	public Resource resourceDetail(final ResourceNameRequest param, @WebParam(header = true) final Credentials credentials)
			throws WebServiceException {
		Resource result = null;

		try {
			final Application application = this.getApplication(param.getApplicationName());
			result = this.getResourceService().find(application, param.getResourceName());

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	@WebMethod
	@WebResult(name = "operations")
	public OperationsResponse resourceOperations(final ResourceNameRequest param, @WebParam(header = true) final Credentials credentials)
			throws WebServiceException {

		this.checkAuthentication(credentials);
		this.checkPermission(credentials, "resource", Constants.DEFAULT_OPERATION_SHOW);

		final OperationsResponse result = new OperationsResponse();

		try {
			final Application application = this.getApplication(param.getApplicationName());
			final Resource resource = this.getResource(application, param.getResourceName());
			final List<Operation> operations = this.getOperationService().find(resource);

			result.setOperations(operations);

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}
}
