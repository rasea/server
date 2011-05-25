package org.rasea.ws.v1.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.rasea.core.entity.Application;
import org.rasea.core.entity.Authorization;
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Permission;
import org.rasea.core.entity.Resource;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.util.Constants;
import org.rasea.extensions.entity.User;
import org.rasea.ws.v1.exception.ExceptionFactory;
import org.rasea.ws.v1.exception.WebServiceException;
import org.rasea.ws.v1.request.AuthorizationRequest;
import org.rasea.ws.v1.request.GrantPermissionRequest;
import org.rasea.ws.v1.request.RoleNameRequest;
import org.rasea.ws.v1.request.RoleRequest;
import org.rasea.ws.v1.request.SimpleUserNameRequest;
import org.rasea.ws.v1.request.UserRequest;
import org.rasea.ws.v1.request.UserRoleRequest;
import org.rasea.ws.v1.type.CredentialsType;

@WebService(name = "Management_v1", targetNamespace = "http://rasea.org/ps/wsdl/Management_v1", serviceName = "ManagementService_v1", portName = "ManagementPort_v1")
@SOAPBinding(parameterStyle = ParameterStyle.BARE, style = Style.DOCUMENT, use = Use.LITERAL)
public class ManagementService extends AbstractWebService {

	@WebMethod
	public void addRole(final RoleRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "role", Constants.DEFAULT_OPERATION_INSERT);

			final Application application = this.getApplication(param.getApplicationName());
			Role role = param.getRole().parse();
			role.setApplication(application);

			this.getRoleService().insert(role);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void addUser(final UserRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "user", Constants.DEFAULT_OPERATION_INSERT);

			User user = param.getUser().parse();
			user.setPassword(param.getPassword());
			
			this.getUserService().insert(user);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void assignUser(final UserRoleRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "member", Constants.DEFAULT_OPERATION_UPDATE);

			final User user = this.getUser(param.getUsername());
			final Application application = this.getApplication(param.getApplicationName());
			final Role role = this.getRole(application, param.getRoleName());

			this.getMemberService().insert(role, user);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deassignUser(final UserRoleRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "member", Constants.DEFAULT_OPERATION_UPDATE);

			final User user = this.getUser(param.getUsername());
			final Application application = this.getApplication(param.getApplicationName());
			final Role role = this.getRole(application, param.getRoleName());

			this.getMemberService().delete(role, user);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deleteRole(final RoleNameRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "role", Constants.DEFAULT_OPERATION_DELETE);

			final Application application = this.getApplication(param.getApplicationName());
			final Role role = this.getRole(application, param.getRoleName());

			this.getRoleService().delete(role);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deleteUser(final SimpleUserNameRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "user", Constants.DEFAULT_OPERATION_DELETE);

			final User user = this.getUser(param.getUsername());
			this.getUserService().delete(user);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void disableRole(final RoleNameRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		// TODO Auto-generated method stub

	}

	@WebMethod
	public void disableUser(final SimpleUserNameRequest param,
			@WebParam(header = true) final CredentialsType credentials) throws WebServiceException {
		// TODO Auto-generated method stub

	}

	@WebMethod
	public void enableRole(final RoleNameRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		// TODO Auto-generated method stub

	}

	@WebMethod
	public void enableUser(final SimpleUserNameRequest param, @WebParam(header = true) final CredentialsType credentials)
			throws WebServiceException {
		// TODO Auto-generated method stub

	}

	@WebMethod
	public void grantPermission(final GrantPermissionRequest param,
			@WebParam(header = true) final CredentialsType credentials) throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "authorization", Constants.DEFAULT_OPERATION_UPDATE);

			final Application application = this.getApplication(param.getApplicationName());
			final Resource resource = this.getResource(application, param.getPermission().getResourceName());
			final Operation operation = this.getOperation(application, param.getPermission().getOperationName());
			final Permission permission = new Permission(resource, operation);
			final Role role = this.getRole(application, param.getRoleName());

			final Authorization authorization = new Authorization(permission, role, param.isAllowed());
			this.getAuthorizationService().insert(authorization);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void revokePermission(final AuthorizationRequest param,
			@WebParam(header = true) final CredentialsType credentials) throws WebServiceException {
		try {
			this.checkAuthentication(credentials);
			this.checkPermission(credentials, "authorization", Constants.DEFAULT_OPERATION_UPDATE);

			final Application application = this.getApplication(param.getApplicationName());
			final Resource resource = this.getResource(application, param.getPermission().getResourceName());
			final Operation operation = this.getOperation(application, param.getPermission().getOperationName());
			final Permission permission = new Permission(resource, operation);
			final Role role = this.getRole(application, param.getRoleName());

			final Authorization authorization = new Authorization(permission, role, false);
			this.getAuthorizationService().delete(authorization);

		} catch (final RequiredException cause) {
			throw ExceptionFactory.create(cause);
		}
	}
}
