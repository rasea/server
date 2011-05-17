package org.rasea.ui.exception;

import org.jboss.seam.annotations.ApplicationException;

@ApplicationException(rollback = false, end = false)
public class UncaughtException extends RuntimeException {
	
	private static final long serialVersionUID = 4306111818767272185L;
	
	public UncaughtException(final Throwable cause) {
		super(cause);
	}
}
