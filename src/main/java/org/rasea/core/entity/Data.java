package org.rasea.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rasea-data", namespace = "http://rasea.org/server/data")
@XmlType(propOrder = { "applications" })
public class Data {
	
	@XmlElement(name = "application", required = true)
	private List<Application> applications;
	
	@XmlAttribute(required = true)
	private String version;
	
	@XmlAttribute(required = true)
	private Date generated;
	
	public void addApplication(final Application application) {
		if (this.applications == null) {
			this.applications = new ArrayList<Application>();
		}
		
		this.applications.add(application);
	}
	
	public List<Application> getApplications() {
		return this.applications;
	}
	
	public Date getGenerated() {
		return this.generated;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public void setApplications(final List<Application> applications) {
		this.applications = applications;
	}
	
	public void setGenerated(final Date generated) {
		this.generated = generated;
	}
	
	public void setVersion(final String version) {
		this.version = version;
	}
}
