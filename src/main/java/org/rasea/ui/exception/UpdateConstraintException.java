package org.rasea.ui.exception;

import org.jboss.seam.annotations.ApplicationException;
import org.rasea.core.exception.AbstractBusinessException;
import org.rasea.core.exception.Severity;


@ApplicationException(rollback = true, end = false)
public class UpdateConstraintException extends AbstractBusinessException {
	
	public UpdateConstraintException() {
		super("br.com.avansys.fw.exception.updateConstraintException");
	}
	
	private static final long serialVersionUID = 4613338410607815300L;
	
	@Override
	public Severity getSeverity() {
		return Severity.ERROR;
	}
}
