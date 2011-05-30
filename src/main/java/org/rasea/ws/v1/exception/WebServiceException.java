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
