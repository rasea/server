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
package org.rasea.core.exception;

public class NotSupportedException extends AbstractBusinessException {

	private static final long serialVersionUID = 1L;

	private final String invalidVersion;

	public NotSupportedException(final String invalidVersion) {
		super("Versão do arquivo não suportada: " + invalidVersion);
		this.invalidVersion = invalidVersion;
	}

	public String getInvalidVersion() {
		return this.invalidVersion;
	}

	@Override
	public Severity getSeverity() {
		return Severity.INFO;
	}
}
