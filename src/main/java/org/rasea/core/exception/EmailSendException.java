package org.rasea.core.exception;

public class EmailSendException extends AbstractBusinessException {

	private static final long serialVersionUID = 6686756958620326968L;

	public EmailSendException(final String message) {
		super(message);
	}

	public EmailSendException(final String message, final Throwable cause) {
		super(message, cause);
	}

	@Override
	public Severity getSeverity() {
		return Severity.WARN;
	}
}
