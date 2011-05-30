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

public abstract class AbstractInvalidPropertyException extends AbstractBusinessException {

	private static final long serialVersionUID = -4106751350465520500L;

	private final String property;

	public AbstractInvalidPropertyException(final String message, final String property) {
		super(message);
		this.property = property;
	}

	public AbstractInvalidPropertyException(final String message, final String property, final String... params) {
		super(message, params);
		this.property = property;
	}

	public AbstractInvalidPropertyException(final String message, final String property, final Throwable cause) {
		super(message, cause);
		this.property = property;
	}

	public String getProperty() {
		return this.property;
	}

}
