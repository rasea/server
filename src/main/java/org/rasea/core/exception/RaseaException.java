package org.rasea.core.exception;

import br.gov.frameworkdemoiselle.exception.ApplicationException;
import br.gov.frameworkdemoiselle.message.SeverityType;

@ApplicationException(severity = SeverityType.ERROR)
public abstract class RaseaException extends RuntimeException {

	private static final long serialVersionUID = 764003727790783341L;

}
