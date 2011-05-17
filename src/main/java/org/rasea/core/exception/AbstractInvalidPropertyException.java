package org.rasea.core.exception;

public abstract class AbstractInvalidPropertyException extends AbstractBusinessException {

	private static final long serialVersionUID = -4106751350465520500L;

	private final String property;

	public AbstractInvalidPropertyException(final String message, final String property) {
		super(message);
		this.property = property;
	}

	public AbstractInvalidPropertyException(final String message, final String property, final String... params) {
		super(message, params);
		this.property = property;
	}

	public AbstractInvalidPropertyException(final String message, final String property, final Throwable cause) {
		super(message, cause);
		this.property = property;
	}

	public String getProperty() {
		return this.property;
	}

}
