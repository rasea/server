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
import org.rasea.core.entity.Permission;
import org.rasea.core.entity.Role;
import org.rasea.core.util.Constants;
import org.rasea.extensions.entity.User;
import org.rasea.ws.v1.exception.WebServiceException;
import org.rasea.ws.v1.request.ApplicationNameRequest;
import org.rasea.ws.v1.request.EmptyRequest;
import org.rasea.ws.v1.request.NewPasswordRequest;
import org.rasea.ws.v1.request.ResetPasswordRequest;
import org.rasea.ws.v1.request.ResourceRoleRequest;
import org.rasea.ws.v1.request.RoleNameRequest;
import org.rasea.ws.v1.request.SimpleUserNameRequest;
import org.rasea.ws.v1.request.UserNameRequest;
import org.rasea.ws.v1.request.UserResourceRequest;
import org.rasea.ws.v1.response.OperationsResponse;
import org.rasea.ws.v1.response.PermissionsResponse;
import org.rasea.ws.v1.response.RolesResponse;
import org.rasea.ws.v1.response.UsersResponse;
import org.rasea.ws.v1.type.CredentialsType;

/**
 * <p>
 * Webservice usado para o asserções de autorização e segurança.
 * </p>
 * 
 * @author cleverson.sacramento@gmail.com
 * @since 2009-11-07 <br>
 */
@WebService(name = "AccessControl_v1", targetNamespace = "http://rasea.org/ps/wsdl/AccessControl_v1", serviceName = "AccessControlService_v1", portName = "AccessControlPort_v1")
@SOAPBinding(parameterStyle = ParameterStyle.BARE, style = Style.DOCUMENT, use = Use.LITERAL)
public class AccessControlService extends AbstractWebService {

	/**
	 * Retorna os papéis de um usuário para uma dada aplicação.
	 * 
	 * @param application
	 * @param username
	 * @return List<Role>
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "roles")
	public RolesResponse assignedRoles(final UserNameRequest param, @WebParam(header = true) final CredentialsType credentialsType)
			throws WebServiceException {

		this.checkAuthentication(credentialsType);
		if (!credentialsType.getUsername().equals(param.getApplicationName())) {
			this.checkPermission(credentialsType, "role", Constants.DEFAULT_OPERATION_SHOW);
		}

		final RolesResponse result = new RolesResponse();

		try {
			final Application app = this.getApplication(param.getApplicationName());
			final User usr = this.getUser(param.getUsername());
			final List<Role> roles = this.getMemberService().find(app, usr);

			result.setRoles(roles);

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	/**
	 * Retorna os usuários de um dado papel em uma palicação.
	 * 
	 * @param application
	 * @param role
	 * @return List<User>
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "users")
	public UsersResponse assignedUsers(final RoleNameRequest param, @WebParam(header = true) final CredentialsType credentialsType)
			throws WebServiceException {

		this.checkAuthentication(credentialsType);
		this.checkPermission(credentialsType, "role", Constants.DEFAULT_OPERATION_SHOW);

		final UsersResponse result = new UsersResponse();

		try {
			final Application app = this.getApplication(param.getApplicationName());
			final Role role = this.getRole(app, param.getRoleName());
			final List<User> users = this.getMemberService().find(role);

			result.setUsers(users);

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	/**
	 * Autentica o usuário no diretório de usuários. Este procedimento não
	 * mantém nenhum estado ou sessão no servidor RASEA para o usuário
	 * autenticado.
	 * 
	 * @param credentialsType
	 * @return boolean
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "authenticated")
	public boolean authenticate(final CredentialsType credentialsType) throws WebServiceException {
		boolean ret = false;

		try {
			ret = this.getUserService().authenticate(credentialsType.getUsername(), credentialsType.getPassword());

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return ret;
	}

	/**
	 * Altera a senha de um usuário válido.
	 * 
	 * @param username
	 * @param password
	 * @param header
	 * @return
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "changed")
	public boolean changePassword(final NewPasswordRequest param, @WebParam(header = true) final CredentialsType credentialsType) throws WebServiceException {

		this.checkAuthentication(credentialsType);
		if (!credentialsType.getUsername().equals(param.getUsername())) {
			this.checkPermission(credentialsType, "user", Constants.DEFAULT_OPERATION_UPDATE);
		}

		try {
			this.getUser(param.getUsername());
			this.getUserService().changePassword(param.getUsername(), param.getNewPassword());

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return true;
	}

	/**
	 * Retorna todos os papeis de uma dada aplicação.
	 * 
	 * @param applicationName
	 * @return List<Role>
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "roles")
	public RolesResponse listRoles(final ApplicationNameRequest param, @WebParam(header = true) final CredentialsType credentialsType)
			throws WebServiceException {

		this.checkAuthentication(credentialsType);
		this.checkPermission(credentialsType, "role", Constants.DEFAULT_OPERATION_SHOW);

		final RolesResponse result = new RolesResponse();

		try {
			final Application app = this.getApplication(param.getApplicationName());
			final List<Role> roles = this.getRoleService().find(app);

			result.setRoles(roles);

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	/**
	 * Retorna todos os usuários.
	 * 
	 * @return List<Role>
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "users")
	public UsersResponse listUsers(final EmptyRequest param, @WebParam(header = true) final CredentialsType credentialsType) throws WebServiceException {

		this.checkAuthentication(credentialsType);
		this.checkPermission(credentialsType, "user", Constants.DEFAULT_OPERATION_SHOW);
		final UsersResponse result = new UsersResponse();

		try {
			final List<User> users = this.getUserService().findAll();

			result.setUsers(users);

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	@WebMethod
	@WebResult(name = "requested")
	public boolean requestConfirmationCode(final SimpleUserNameRequest param) throws WebServiceException {
		return false;
	}

	@WebMethod
	@WebResult(name = "reseted")
	public boolean resetPassword(final ResetPasswordRequest param) throws WebServiceException {
		boolean result = false;

		try {
			this.getUserService().resetPassword(param.getUsername(), param.getConfirmationCode());
			result = true;

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	/**
	 * Obtém os detalhes de um papel.
	 * 
	 * @param application
	 * @param role
	 * @return User
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "role")
	public Role roleDetail(final RoleNameRequest param, @WebParam(header = true) final CredentialsType credentialsType) throws WebServiceException {

		this.checkAuthentication(credentialsType);
		this.checkPermission(credentialsType, "role", Constants.DEFAULT_OPERATION_SHOW);
		org.rasea.core.entity.Role role = null;

		try {
			final org.rasea.core.entity.Application app = this.getApplication(param.getApplicationName());
			role = this.getRole(app, param.getRoleName());

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return role;
	}

	/**
	 * @param application
	 * @param role
	 * @param resource
	 * @return
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "operations")
	public OperationsResponse roleOperationsOnResource(final ResourceRoleRequest param, @WebParam(header = true) final CredentialsType credentialsType)
			throws WebServiceException {

		// TODO Implementar método preocupando-se com a segurança.
		return null;
	}

	/**
	 * Retorna um conjunto de autorizações de um papel para uma dada aplicação.
	 * 
	 * @param application
	 * @param role
	 * @return List<Permission>
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "permissions")
	public PermissionsResponse rolePermissions(final RoleNameRequest param, @WebParam(header = true) final CredentialsType credentialsType)
			throws WebServiceException {

		this.checkAuthentication(credentialsType);
		this.checkPermission(credentialsType, "authorization", Constants.DEFAULT_OPERATION_SHOW);

		final PermissionsResponse result = new PermissionsResponse();

		try {
			final org.rasea.core.entity.Application app = this.getApplication(param.getApplicationName());
			final org.rasea.core.entity.Role role = this.getRole(app, param.getRoleName());

			final List<org.rasea.core.entity.Permission> permissions = this.getPermissionService().find(role);

			result.setPermissions(permissions);
		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	/**
	 * Retorna os papéis que não estão associados a um usuário para uma dada
	 * aplicação.
	 * 
	 * @param application
	 * @param username
	 * @return List<Role>
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "roles")
	public RolesResponse unassignedRoles(final UserNameRequest param, @WebParam(header = true) final CredentialsType credentialsType)
			throws WebServiceException {

		// TODO Implementar método preocupando-se com a segurança.
		return null;
	}

	/**
	 * Obtém os detalhes de um usuário.
	 * 
	 * @param username
	 * @return User
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "user")
	public User userDetail(final SimpleUserNameRequest param, @WebParam(header = true) final CredentialsType credentialsType) throws WebServiceException {

		this.checkAuthentication(credentialsType);
		if (!credentialsType.getUsername().equals(param.getUsername())) {
			this.checkPermission(credentialsType, "user", Constants.DEFAULT_OPERATION_SHOW);
		}

		User result = null;

		try {
			result = this.getUser(param.getUsername());

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	/**
	 * @param application
	 * @param username
	 * @param resource
	 * @param header
	 * @return
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "operations")
	public OperationsResponse userOperationsOnResource(final UserResourceRequest param, @WebParam(header = true) final CredentialsType credentialsType)
			throws WebServiceException {

		// TODO Implementar método preocupando-se com a segurança.
		return null;
	}

	/**
	 * Retorna um conjunto de autorizações de um usuário para uma dada
	 * aplicação.
	 * 
	 * @param application
	 * @param username
	 * @return List<Permission>
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "permissions")
	public PermissionsResponse userPermissions(final UserNameRequest param, @WebParam(header = true) final CredentialsType credentialsType)
			throws WebServiceException {

		this.checkAuthentication(credentialsType);
		if (!credentialsType.getUsername().equals(param.getUsername())) {
			this.checkPermission(credentialsType, "authorization", Constants.DEFAULT_OPERATION_SHOW);
		}

		final PermissionsResponse result = new PermissionsResponse();

		try {
			final Application app = this.getApplication(param.getApplicationName());
			final User usr = this.getUser(param.getUsername());
			final List<Permission> permissions = this.getPermissionService().find(app, usr);

			result.setPermissions(permissions);

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}
}
