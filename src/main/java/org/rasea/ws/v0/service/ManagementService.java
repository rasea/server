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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.rasea.core.entity.Authorization;
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Resource;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.util.Constants;
import org.rasea.ws.v0.exception.ExceptionFactory;
import org.rasea.ws.v0.exception.WebServiceException;
import org.rasea.ws.v0.type.Permission;
import org.rasea.ws.v0.type.RaseaServiceHeader;
import org.rasea.ws.v0.type.Role;
import org.rasea.ws.v0.type.TypeParser;
import org.rasea.ws.v0.type.User;

@WebService(targetNamespace = "http://rasea.org/ws/Management", name = "Management", serviceName = "ManagementService")
@SOAPBinding(parameterStyle = ParameterStyle.WRAPPED, style = Style.DOCUMENT, use = Use.LITERAL)
public class ManagementService extends AbstractWebService {

	@WebMethod
	public void addRole(@WebParam(name = "application") final String application, @WebParam(name = "role") final Role role,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "role", Constants.DEFAULT_OPERATION_INSERT);

			final org.rasea.core.entity.Application app = this.getApplication(application);

			// TODO Fazer o parse com o TypeParser
			final org.rasea.core.entity.Role srRole = new org.rasea.core.entity.Role();
			srRole.setName(role.getName());
			srRole.setDisplayName(role.getDescription());
			srRole.setApplication(app);
			srRole.setEnabled(true);
			this.getRoleService().insert(srRole);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void addUser(@WebParam(name = "user") final User user, @WebParam(name = "password") final String password,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "user", Constants.DEFAULT_OPERATION_INSERT);

			final org.rasea.extensions.entity.User usr = TypeParser.parse(user);
			usr.setPassword(password);
			usr.setEnabled(true);
			this.getUserService().insert(usr);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void assignUser(@WebParam(name = "application") final String application, @WebParam(name = "username") final String username,
			@WebParam(name = "role") final String role, @WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header)
			throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "member", Constants.DEFAULT_OPERATION_UPDATE);

			final org.rasea.extensions.entity.User usr = this.getUser(username);
			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Role serRole = this.getRole(app, role);
			this.getMemberService().insert(serRole, usr);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deassignUser(@WebParam(name = "application") final String application, @WebParam(name = "username") final String username,
			@WebParam(name = "role") final String role, @WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header)
			throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "member", Constants.DEFAULT_OPERATION_UPDATE);

			final org.rasea.extensions.entity.User usr = this.getUser(username);
			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Role serRole = this.getRole(app, role);
			this.getMemberService().delete(serRole, usr);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deleteRole(@WebParam(name = "application") final String application, @WebParam(name = "role") final String role,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "role", Constants.DEFAULT_OPERATION_DELETE);

			final org.rasea.core.entity.Application app = this.getApplication(application);
			final org.rasea.core.entity.Role srvRole = this.getRole(app, role);
			this.getRoleService().delete(srvRole);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void deleteUser(@WebParam(name = "username") final String username,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "user", Constants.DEFAULT_OPERATION_DELETE);

			final org.rasea.extensions.entity.User user = this.getUser(username);
			this.getUserService().delete(user);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void disableRole(@WebParam(name = "application") final String application, @WebParam(name = "role") final String role,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		// TODO Auto-generated method stub

	}

	@WebMethod
	public void disableUser(@WebParam(name = "username") final String username,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		// TODO Auto-generated method stub

	}

	@WebMethod
	public void enableRole(@WebParam(name = "application") final String application, @WebParam(name = "role") final String role,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		// TODO Auto-generated method stub

	}

	@WebMethod
	public void enableUser(@WebParam(name = "username") final String username,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		// TODO Auto-generated method stub

	}

	@WebMethod
	public void grantPermission(@WebParam(name = "application") final String application, @WebParam(name = "permission") final Permission permission,
			@WebParam(name = "role") final String role, @WebParam(name = "allowed") final boolean allowed,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "authorization", Constants.DEFAULT_OPERATION_UPDATE);

			final org.rasea.core.entity.Application app = this.getApplication(application);
			final Resource resource = this.getResource(app, permission.getResource());
			final Operation operation = this.getOperation(app, permission.getOperation());
			final org.rasea.core.entity.Permission perm = new org.rasea.core.entity.Permission(resource, operation);
			final org.rasea.core.entity.Role rol = this.getRole(app, role);

			final Authorization authorization = new Authorization(perm, rol, allowed);
			this.getAuthorizationService().insert(authorization);

		} catch (final Exception cause) {
			this.handleException(cause);
		}
	}

	@WebMethod
	public void revokePermission(@WebParam(name = "application") final String application,
			@WebParam(name = "permission") final Permission permission, @WebParam(name = "role") final String role,
			@WebParam(header = true, name = "raseaServiceHeader") final RaseaServiceHeader header) throws WebServiceException {
		try {
			this.checkAuthentication(header);
			this.checkPermission(header, "authorization", Constants.DEFAULT_OPERATION_UPDATE);

			final org.rasea.core.entity.Application app = this.getApplication(application);
			final Resource resource = this.getResource(app, permission.getResource());
			final Operation operation = this.getOperation(app, permission.getOperation());
			final org.rasea.core.entity.Permission perm = new org.rasea.core.entity.Permission(resource, operation);
			final org.rasea.core.entity.Role rol = this.getRole(app, role);

			final Authorization authorization = new Authorization(perm, rol, false);
			this.getAuthorizationService().delete(authorization);

		} catch (final RequiredException cause) {
			throw ExceptionFactory.create(cause);
		}
	}
}
