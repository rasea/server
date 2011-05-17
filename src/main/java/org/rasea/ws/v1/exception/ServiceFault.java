package org.rasea.ws.v1.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "errorCode", "source", "additionalInfo" })
public class ServiceFault {
	
	private int errorCode;
	
	private String source;
	
	private String additionalInfo;
	
	public ServiceFault() {
	}
	
	public String getAdditionalInfo() {
		return this.additionalInfo;
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}
	
	public String getSource() {
		return this.source;
	}
	
	public void setAdditionalInfo(final String value) {
		this.additionalInfo = value;
	}
	
	public void setErrorCode(final int value) {
		this.errorCode = value;
	}
	
	public void setSource(final String value) {
		this.source = value;
	}
	
}
