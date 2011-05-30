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

import org.rasea.core.entity.Application;
import org.rasea.core.util.Constants;
import org.rasea.ws.v0.exception.WebServiceException;
import org.rasea.ws.v0.type.Operation;
import org.rasea.ws.v0.type.Permission;
import org.rasea.ws.v0.type.RaseaServiceHeader;
import org.rasea.ws.v0.type.Role;
import org.rasea.ws.v0.type.TypeParser;
import org.rasea.ws.v0.type.User;

/**
 * Conformance (Default wrapper bean names): In the absence of customizations,
 * the wrapper request bean class MUST be named the same as the method and the
 * wrapper response bean class MUST be named the same as the method with a
 * “Response” suffix. The first letter of each bean name is capitalized to
 * follow Java class naming conventions.
 * <p>
 * Webservice usado para o asserções de autorização e segurança.
 * </p>
 * <p>
 * Os xsds que definem os documentos de requisição e resposta para este serviço
 * serão usados junto com a elaboração de webservice wsdl2java. Até esta data o
 * serviço foi gerado usando a abordagem java2wsdl, recebendo portanto, a
 * annotação explícita para document-style do tipo wrapped em seus parâmetros de
 * entrada e saída.<br>
 * Logo, os documentos de requisição e respostas são gerados pelo servidor.
 * </p>
 * 
 * @author paulosuzart@gmail.com
 * @since 30/12/2008 <br>
 */
@WebService(targetNamespace = "http://rasea.org/ws/AccessControl", name = "AccessControl", serviceName = "AccessControlService")
@SOAPBinding(parameterStyle = ParameterStyle.WRAPPED, style = Style.DOCUMENT, use = Use.LITERAL)
public class AccessControlService extends AbstractWebService {

	/**
	 * Retorna os papeis de um usuário para uma dada aplicação.
	 * 
	 * @param application
	 * @param username
	 * @return List<Role>
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "roles")
	public List<Role> assignedRoles(@WebParam(name = "application") final String application, @WebParam(name = "username") final String username,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

		this.checkAuthentication(header);
		if (!header.getUsername().equals(username)) {
			this.checkPermission(header, "role", Constants.DEFAULT_OPERATION_SHOW);
		}

		final List<Role> result = new ArrayList<Role>();

		try {
			final org.rasea.core.entity.Application app = this.getApplication(application);

			final org.rasea.extensions.entity.User usr;
			usr = this.getUser(username);

			for (final org.rasea.core.entity.Role role : this.getMemberService().find(app, usr)) {
				result.add(TypeParser.parse(role));
			}

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
	public List<User> assignedUsers(@WebParam(name = "application") final String application, @WebParam(name = "role") final String role,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

		this.checkAuthentication(header);
		this.checkPermission(header, "role", Constants.DEFAULT_OPERATION_SHOW);

		final List<User> result = new ArrayList<User>();
		try {
			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Role serRole = this.getRole(app, role);

			for (final org.rasea.extensions.entity.User user : this.getMemberService().find(serRole)) {
				result.add(TypeParser.parse(user));
			}

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
	 * @param username
	 * @param password
	 * @return boolean
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "authenticated")
	public boolean authenticate(@WebParam(name = "username") final String username, @WebParam(name = "password") final String password)
			throws WebServiceException {
		boolean ret = false;

		try {
			ret = this.getUserService().authenticate(username, password);

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
	public boolean changePassword(@WebParam(name = "username") final String username, @WebParam(name = "password") final String password,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

		this.checkAuthentication(header);
		if (!header.getUsername().equals(username)) {
			this.checkPermission(header, "user", Constants.DEFAULT_OPERATION_UPDATE);
		}

		try {
			this.getUser(username);
			this.getUserService().changePassword(username, password);

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return true;
	}

	/**
	 * Retorna todos os papeis de uma dada aplicação.
	 * 
	 * @param application
	 * @return List<Role>
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "roles")
	public List<Role> listRoles(@WebParam(name = "application") final String application,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

		this.checkAuthentication(header);
		this.checkPermission(header, "role", Constants.DEFAULT_OPERATION_SHOW);
		final List<Role> result = new ArrayList<Role>();

		try {
			final Application app = this.getApplication(application);

			for (final org.rasea.core.entity.Role role : this.getRoleService().find(app)) {
				result.add(TypeParser.parse(role));
			}

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
	public List<User> listUsers(@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

		this.checkAuthentication(header);
		this.checkPermission(header, "user", Constants.DEFAULT_OPERATION_SHOW);
		final List<User> result = new ArrayList<User>();

		try {
			for (final org.rasea.extensions.entity.User user : this.getUserService().findAll()) {
				result.add(TypeParser.parse(user));
			}
		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	@WebMethod
	@WebResult(name = "requested")
	public boolean requestConfirmationCode(@WebParam(name = "username") final String username) throws WebServiceException {
		return false;
	}

	@WebMethod
	@WebResult(name = "reseted")
	public boolean resetPassword(@WebParam(name = "username") final String username,
			@WebParam(name = "confirmationCode") final String confirmationCode) throws WebServiceException {
		boolean result = false;

		try {
			this.getUserService().resetPassword(username, confirmationCode);
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
	public Role roleDetail(@WebParam(name = "application") final String application, @WebParam(name = "role") final String role,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

		this.checkAuthentication(header);
		this.checkPermission(header, "role", Constants.DEFAULT_OPERATION_SHOW);

		Role result = null;
		org.rasea.core.entity.Role serRole = null;

		try {
			final org.rasea.core.entity.Application app = this.getApplication(application);
			serRole = this.getRole(app, role);
			result = TypeParser.parse(serRole);

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}

	/**
	 * Indica se o papel de uma determinada aplicação está ativo.
	 * 
	 * @param application
	 * @param role
	 * @param header
	 * @return
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "enabled")
	public boolean roleEnabled(@WebParam(name = "application") final String application, @WebParam(name = "role") final String role,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

		this.checkAuthentication(header);
		this.checkPermission(header, "role", Constants.DEFAULT_OPERATION_SHOW);
		boolean enabled = false;

		try {
			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Role strRole = this.getRole(app, role);
			enabled = strRole.isEnabled();

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return enabled;
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
	public List<Operation> roleOperationsOnResource(@WebParam(name = "application") final String application,
			@WebParam(name = "role") final String role, @WebParam(name = "resource") final String resource,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

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
	public List<Permission> rolePermissions(@WebParam(name = "application") final String application, @WebParam(name = "role") final String role,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		this.checkAuthentication(header);
		this.checkPermission(header, "authorization", Constants.DEFAULT_OPERATION_SHOW);

		final List<Permission> result = new ArrayList<Permission>();

		try {
			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Role roleEnt = this.getRole(app, role);

			final List<org.rasea.core.entity.Permission> permissions = this.getPermissionService().find(roleEnt);

			for (final org.rasea.core.entity.Permission permission : permissions) {
				result.add(TypeParser.parse(permission));
			}

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
	public List<User> unassignedRoles(@WebParam(name = "application") final String application, @WebParam(name = "username") final String username,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

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
	public User userDetail(@WebParam(name = "username") final String username,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

		this.checkAuthentication(header);
		if (!header.getUsername().equals(username)) {
			this.checkPermission(header, "user", Constants.DEFAULT_OPERATION_SHOW);
		}

		org.rasea.extensions.entity.User usr = null;

		try {
			usr = this.getUser(username);

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return TypeParser.parse(usr);
	}

	/**
	 * Indica se o usuário está ativo.
	 * 
	 * @param username
	 * @param header
	 * @return
	 * @throws WebServiceException
	 */
	@WebMethod
	@WebResult(name = "enabled")
	public boolean userEnabled(@WebParam(name = "username") final String username,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

		this.checkAuthentication(header);
		if (!header.getUsername().equals(username)) {
			this.checkPermission(header, "user", Constants.DEFAULT_OPERATION_SHOW);
		}

		boolean enabled = false;

		try {
			final org.rasea.extensions.entity.User usr = this.getUser(username);
			enabled = usr.isEnabled();

		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return enabled;
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
	public List<Operation> userOperationsOnResource(@WebParam(name = "application") final String application,
			@WebParam(name = "username") final String username, @WebParam(name = "resource") final String resource,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {

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
	public List<Permission> userPermissions(@WebParam(name = "application") final String application,
			@WebParam(name = "username") final String username, @WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header)
			throws WebServiceException {

		this.checkAuthentication(header);
		if (!header.getUsername().equals(username)) {
			this.checkPermission(header, "authorization", Constants.DEFAULT_OPERATION_SHOW);
		}

		List<Permission> result = null;

		try {
			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.extensions.entity.User usr = this.getUser(username);
			final List<org.rasea.core.entity.Permission> permissions = this.getPermissionService().find(app, usr);

			result = new ArrayList<Permission>();

			Permission perm;
			for (final org.rasea.core.entity.Permission permission : permissions) {
				perm = TypeParser.parse(permission);
				result.add(perm);
			}
		} catch (final Exception cause) {
			this.handleException(cause);
		}

		return result;
	}
}
