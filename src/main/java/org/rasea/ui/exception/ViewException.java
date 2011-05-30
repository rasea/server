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
package org.rasea.ui.exception;

import java.util.ArrayList;
import java.util.List;

import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.exception.Severity;

/**
 * <p>
 * As aplicações devem utilizar esta exceção apenas na camada de apresentação,
 * caso haja alguma validação de tela que impeça a conclusão bem sucedida da
 * operação. Sempre que uma exceção deste tipo for lançada, a abstração tratará
 * e exibirá uma mensagem associada à chave passada como parâmetro. A chave
 * informada deverá existir no arquivo <i>messages.properties</i>. O tratamento
 * desta exceção causará a não execução da operação.
 * </p>
 * <p>
 * Por exemplo, caso seja lançada a seguinte exceção:
 * </p>
 * 
 * <pre>
 * throw new ViewException(&quot;meu.teste&quot;).addField(&quot;meuCampoDaTela&quot;);
 * </pre>
 * <p>
 * Na tela aparecerá a seguite mensagem associada à chave junto ao campo passado
 * em addField
 * <p>
 * <i>messages.properties</i>:
 * </p>
 * </p>
 * 
 * <pre>
 * meu.teste=Minha mensagem de negócio
 * </pre>
 * <p>
 * OBS: <b>A conversão da exceção em mensagem de tela é feita
 * automticamente</b>. Caso não seja vinculado um id de campo da tela para a
 * mensagem ela será exibida como global. </pre>
 * 
 * @author abraao.isvi
 */
public class ViewException extends AbstractApplicationException {

	private static final long serialVersionUID = -8626143185030994858L;

	private List<String> fields;

	private Severity severity;

	public ViewException(final Severity severity, final String message) {
		super(message);
		this.severity = severity;
	}

	public ViewException(final Severity severity, final String message, final String... params) {
		super(message, params);
		this.severity = severity;
	}

	public ViewException(final String message) {
		super(message);
	}

	public ViewException(final String message, final String... params) {
		super(message, params);
	}

	public ViewException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ViewException addField(final String field) {
		if (this.fields == null) {
			this.fields = new ArrayList<String>();
		}

		this.fields.add(field);
		return this;
	}

	public Object[] getFields() {
		Object[] ret = null;

		if (this.fields != null) {
			ret = this.fields.toArray();
		}
		return ret;
	}

	@Override
	public Severity getSeverity() {
		if (this.severity == null) {
			this.severity = Severity.ERROR;
		}
		return this.severity;
	}
}
