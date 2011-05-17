package org.rasea.core.exception;


public class IntegrityException extends AbstractBusinessException {
	
	private static final long serialVersionUID = -5876826112548125121L;
	
	public IntegrityException(final String message) {
		super(message);
	}
	
	@Override
	public Severity getSeverity() {
		return Severity.INFO;
	}
	
}
