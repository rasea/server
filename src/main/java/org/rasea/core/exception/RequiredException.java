package org.rasea.core.exception;

import org.jboss.seam.annotations.ApplicationException;

@ApplicationException(rollback = true, end = false)
public class RequiredException extends AbstractInvalidPropertyException {
	
	private static final long serialVersionUID = 4613338410607815300L;
	
	private static final String MESSAGE_KEY = "br.com.avansys.Required";
	
	public RequiredException(final String property) {
		super(MESSAGE_KEY, property);
	}
	
	@Override
	public Severity getSeverity() {
		return Severity.ERROR;
	}
}
