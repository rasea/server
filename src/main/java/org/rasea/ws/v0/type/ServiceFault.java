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
 * License along with this library; if not, see <http://gnu.org/licenses>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.rasea.ws.v0.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://rasea.org/commons", name = "raseaServiceFault", propOrder = { "errorCode", "source", "additionalInfo" })
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
