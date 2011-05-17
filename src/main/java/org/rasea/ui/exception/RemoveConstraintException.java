package org.rasea.ui.exception;

import org.jboss.seam.annotations.ApplicationException;
import org.rasea.core.exception.AbstractBusinessException;
import org.rasea.core.exception.Severity;


@ApplicationException(rollback = true, end = false)
public class RemoveConstraintException extends AbstractBusinessException {
	
	public RemoveConstraintException() {
		super("br.com.avansys.fw.exception.removeConstraintException");
	}
	
	private static final long serialVersionUID = 4613338410607815300L;
	
	@Override
	public Severity getSeverity() {
		return Severity.ERROR;
	}
}
