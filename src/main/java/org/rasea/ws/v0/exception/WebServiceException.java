package org.rasea.ws.v0.exception;

import javax.xml.ws.WebFault;

import org.jboss.seam.annotations.ApplicationException;
import org.rasea.ws.v0.type.ServiceFault;

@WebFault(name = "RaseaServiceException")
@ApplicationException(rollback = true, end = false)
public class WebServiceException extends Exception {
	
	private static final long serialVersionUID = 3220644093150893803L;
	
	private final ServiceFault faultInfo;
	
	public WebServiceException(final String message, final ServiceFault faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}
	
	public WebServiceException(final String message, final ServiceFault faultInfo,
			final Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}
	
	public ServiceFault getFaultInfo() {
		return this.faultInfo;
	}
	
}
