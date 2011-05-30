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
package org.rasea.core.exception;

import org.jboss.seam.annotations.ApplicationException;
import org.jboss.seam.core.SeamResourceBundle;

@ApplicationException(rollback = false, end = false)
public class DuplicatedException extends AbstractInvalidPropertyException {

	private static final long serialVersionUID = 7056401969969390916L;

	private static final String MESSAGE_KEY = "br.com.avansys.Duplicated";

	private final String message;

	private final String value;

	public DuplicatedException(final String property, final String value) {
		super(DuplicatedException.MESSAGE_KEY, property, property, value);
		this.value = value;

		String message = SeamResourceBundle.getBundle().getString(DuplicatedException.MESSAGE_KEY);
		message = message.replaceAll("\\{attr\\}", property);
		message = message.replaceAll("\\{value\\}", value);

		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public Severity getSeverity() {
		return Severity.ERROR;
	}

	public String getValue() {
		return this.value;
	}

}
