package org.rasea.ws.v0.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://rasea.org/commons", name = "raseaServiceFault", propOrder = {
		"errorCode", "source", "additionalInfo" })
public class ServiceFault {
	
	private int errorCode;
	
	private String source;
	
	private Object additionalInfo;
	
	public ServiceFault() {
	}
	
	public Object getAdditionalInfo() {
		return this.additionalInfo;
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}
	
	public String getSource() {
		return this.source;
	}
	
	public void setAdditionalInfo(final Object value) {
		this.additionalInfo = value;
	}
	
	public void setErrorCode(final int value) {
		this.errorCode = value;
	}
	
	public void setSource(final String value) {
		this.source = value;
	}
	
}
