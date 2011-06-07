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
package org.rasea.ws.v1.exception;

import org.rasea.core.exception.DuplicatedException;
import org.rasea.core.exception.EmailSendException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.exception.StoreException;

public final class ExceptionFactory {

	public static WebServiceFault create(final DuplicatedException cause) {

		final WebServiceFaultBean fault = new WebServiceFaultBean();
		fault.setErrorCode(ErrorCode.DUPLICATE_NAME);
		fault.setAdditionalInfo(cause.getMessage());
		fault.setSource(cause.getValue());

		return new WebServiceFault(cause.getMessage(), fault);
	}

	public static WebServiceFault create(final EmailSendException cause) {

		final WebServiceFaultBean fault = new WebServiceFaultBean();
		fault.setErrorCode(ErrorCode.INVALID_EMAIL);
		fault.setAdditionalInfo(cause.getMessage());
		fault.setSource("user e-mail");

		return new WebServiceFault(cause.getMessage(), fault);
	}

	public static WebServiceFault create(final RequiredException cause) {

		final WebServiceFaultBean fault = new WebServiceFaultBean();
		fault.setErrorCode(ErrorCode.REQUIRED);
		fault.setAdditionalInfo(cause.getMessage());
		fault.setSource(cause.getProperty());

		return new WebServiceFault(cause.getMessage(), fault);
	}

	public static WebServiceFault create(final StoreException cause) {

		final WebServiceFaultBean fault = new WebServiceFaultBean();
		fault.setErrorCode(ErrorCode.STORE_ERROR);
		fault.setAdditionalInfo(cause.getMessage());
		fault.setSource("credencials store");

		return new WebServiceFault(cause.getMessage(), fault);
	}

	public static WebServiceFault createAuthenticationFailed(final String username) {
		final WebServiceFaultBean fault = new WebServiceFaultBean();
		fault.setErrorCode(ErrorCode.AUTHENTICATION_FAILED);
		fault.setSource(username);

		return new WebServiceFault("authentication failed", fault);
	}

	public static WebServiceFault createHeaderRequired() {
		final WebServiceFaultBean fault = new WebServiceFaultBean();
		fault.setErrorCode(ErrorCode.REQUIRED);
		fault.setSource("header");

		return new WebServiceFault("header is required", fault);
	}

	public static WebServiceFault createNotFound(final String property, final String value) {
		final WebServiceFaultBean fault = new WebServiceFaultBean();
		fault.setErrorCode(ErrorCode.NOT_FOUND);
		fault.setSource(property);

		return new WebServiceFault(String.format("%s \"%s\" not found", property, value), fault);
	}

	public static WebServiceFault createPermissionDenied(final String authenticatedUser, final String username) {

		final WebServiceFaultBean fault = new WebServiceFaultBean();
		fault.setErrorCode(ErrorCode.PERMISSION_DENIED);
		fault.setSource(String.format("%s,%s", authenticatedUser, username));

		return new WebServiceFault(String.format("authenticated user %s cant access this service to request info about another user %s",
				authenticatedUser, username), fault);

	}

	public static WebServiceFault createPermissionDenied(final String username, final String application, final String resource,
			final String operation) {

		final WebServiceFaultBean fault = new WebServiceFaultBean();
		fault.setErrorCode(ErrorCode.PERMISSION_DENIED);
		fault.setSource(String.format("%s,%s,%s,%s", username, application, resource, operation));

		return new WebServiceFault(String.format("permission denied [%s,%s,%s] to %s", application, resource, operation, username), fault);
	}

	public static WebServiceFault createUnknown(final Exception cause) {

		final WebServiceFaultBean fault = new WebServiceFaultBean();
		fault.setErrorCode(ErrorCode.UNKNOWN);
		fault.setAdditionalInfo(cause.getMessage());
		fault.setSource("unknown");

		return new WebServiceFault(cause.getMessage(), fault);
	}

	private ExceptionFactory() {
	}
}
