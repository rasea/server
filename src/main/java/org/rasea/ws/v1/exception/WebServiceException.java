package org.rasea.ws.v1.exception;

import javax.xml.ws.WebFault;

import org.jboss.seam.annotations.ApplicationException;

@WebFault(name = "serviceFault")
@ApplicationException(rollback = true, end = false)
public class WebServiceException extends Exception {
	
	private static final long serialVersionUID = 3220644093150893803L;
	
	private final ServiceFault faultInfo;
	
	public WebServiceException(final String message, final ServiceFault faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}
	
	public ServiceFault getFaultInfo() {
		return this.faultInfo;
	}
	
}
