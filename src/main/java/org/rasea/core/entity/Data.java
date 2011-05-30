/*
 * Rasea Server
 * 
 * Copyright (c) 2008, Rasea <http://rasea.org>. All rights reserved.
 *
 * Rasea Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
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
