package org.rasea.core.exception;

public class ConfigurationException extends Exception {
	
	private static final long serialVersionUID = -3449435107777106369L;
	
	public ConfigurationException(final String message) {
		super(message);
	}
	
	public ConfigurationException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
