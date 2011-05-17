package org.rasea.ws.v1.exception;

import org.rasea.core.exception.DuplicatedException;
import org.rasea.core.exception.EmailSendException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.exception.StoreException;

public final class ExceptionFactory {

	public static WebServiceException create(final DuplicatedException cause) {

		final ServiceFault fault = new ServiceFault();
		fault.setErrorCode(ErrorCode.DUPLICATE_NAME);
		fault.setAdditionalInfo(cause.getMessage());
		fault.setSource(cause.getValue());

		return new WebServiceException(cause.getMessage(), fault);
	}

	public static WebServiceException create(final EmailSendException cause) {

		final ServiceFault fault = new ServiceFault();
		fault.setErrorCode(ErrorCode.INVALID_EMAIL);
		fault.setAdditionalInfo(cause.getMessage());
		fault.setSource("user e-mail");

		return new WebServiceException(cause.getMessage(), fault);
	}

	public static WebServiceException create(final RequiredException cause) {

		final ServiceFault fault = new ServiceFault();
		fault.setErrorCode(ErrorCode.REQUIRED);
		fault.setAdditionalInfo(cause.getMessage());
		fault.setSource(cause.getProperty());

		return new WebServiceException(cause.getMessage(), fault);
	}

	public static WebServiceException create(final StoreException cause) {

		final ServiceFault fault = new ServiceFault();
		fault.setErrorCode(ErrorCode.STORE_ERROR);
		fault.setAdditionalInfo(cause.getMessage());
		fault.setSource("credencials store");

		return new WebServiceException(cause.getMessage(), fault);
	}

	public static WebServiceException createAuthenticationFailed(final String username) {
		final ServiceFault fault = new ServiceFault();
		fault.setErrorCode(ErrorCode.AUTHENTICATION_FAILED);
		fault.setSource(username);

		return new WebServiceException("authentication failed", fault);
	}

	public static WebServiceException createHeaderRequired() {
		final ServiceFault fault = new ServiceFault();
		fault.setErrorCode(ErrorCode.REQUIRED);
		fault.setSource("header");

		return new WebServiceException("header is required", fault);
	}

	public static WebServiceException createNotFound(final String property, final String value) {
		final ServiceFault fault = new ServiceFault();
		fault.setErrorCode(ErrorCode.NOT_FOUND);
		fault.setSource(property);

		return new WebServiceException(String.format("%s \"%s\" not found", property, value), fault);
	}

	public static WebServiceException createPermissionDenied(final String authenticatedUser, final String username) {

		final ServiceFault fault = new ServiceFault();
		fault.setErrorCode(ErrorCode.PERMISSION_DENIED);
		fault.setSource(String.format("%s,%s", authenticatedUser, username));

		return new WebServiceException(String.format(
				"authenticated user %s cant access this service to request info about another user %s",
				authenticatedUser, username), fault);

	}

	public static WebServiceException createPermissionDenied(final String username, final String application,
			final String resource, final String operation) {

		final ServiceFault fault = new ServiceFault();
		fault.setErrorCode(ErrorCode.PERMISSION_DENIED);
		fault.setSource(String.format("%s,%s,%s,%s", username, application, resource, operation));

		return new WebServiceException(String.format("permission denied [%s,%s,%s] to %s", application, resource,
				operation, username), fault);
	}

	public static WebServiceException createUnknown(final Exception cause) {

		final ServiceFault fault = new ServiceFault();
		fault.setErrorCode(ErrorCode.UNKNOWN);
		fault.setAdditionalInfo(cause.getMessage());
		fault.setSource("unknown");

		return new WebServiceException(cause.getMessage(), fault);
	}

	private ExceptionFactory() {
	}
}
