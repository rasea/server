package org.rasea.core.exception;

import org.jboss.seam.annotations.ApplicationException;
import org.jboss.seam.core.SeamResourceBundle;

@ApplicationException(rollback = false, end = false)
public class DuplicatedException extends AbstractInvalidPropertyException {

	private static final long serialVersionUID = 7056401969969390916L;

	private static final String MESSAGE_KEY = "br.com.avansys.Duplicated";

	private final String message;

	private final String value;

	public DuplicatedException(final String property, final String value) {
		super(DuplicatedException.MESSAGE_KEY, property, property, value);
		this.value = value;

		String message = SeamResourceBundle.getBundle().getString(DuplicatedException.MESSAGE_KEY);
		message = message.replaceAll("\\{attr\\}", property);
		message = message.replaceAll("\\{value\\}", value);

		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public Severity getSeverity() {
		return Severity.ERROR;
	}

	public String getValue() {
		return this.value;
	}

}
