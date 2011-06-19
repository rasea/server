package org.rasea.core.security;

import br.gov.frameworkdemoiselle.exception.ApplicationException;
import br.gov.frameworkdemoiselle.message.SeverityType;

@ApplicationException(severity = SeverityType.ERROR)
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -8401992701473152206L;

	public AuthenticationException(Throwable cause) {
		super(cause);
	}
}
