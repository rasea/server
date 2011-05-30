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

public class StoreException extends AbstractBusinessException {

	private static final long serialVersionUID = 366566845050479193L;

	public StoreException(final String message) {
		super(message);
	}

	public StoreException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public StoreException(final Throwable cause) {
		// TODO Lançar mensagem do resource bundle e capturar na tela usando o
		// #{messages[...]}. E quando essa exceção não for para a tela, e sim
		// para um serviço, por exemplo?
		this("Falha na manipulação dos dados de usuário: " + cause.getMessage(), cause);
	}

	@Override
	public Severity getSeverity() {
		return Severity.FATAL;
	}
}
