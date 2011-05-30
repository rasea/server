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
package org.rasea.ui.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.security.Identity;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.exception.AbstractBusinessException;
import org.rasea.core.exception.AbstractInvalidPropertyException;
import org.rasea.core.exception.DuplicatedException;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.exception.UncaughtException;
import org.rasea.ui.exception.ViewException;

@Scope(ScopeType.PAGE)
public abstract class AbstractViewHelper implements Serializable {

	private static final long serialVersionUID = 4504633446192418929L;

	private String backView;

	private String name;

	@In
	private StatusMessages statusMessages;

	private String title;

	/**
	 * Verifica a permissão do usuário para executar ações de uma tela. Se
	 * quiser saber mais é respeito, leia sobre o Sem Security.
	 * 
	 * @param action
	 *            Ação que será verificada para responder a pergunta: o usuário
	 *            pode acessar a tela executando a ação?
	 */
	protected void checkPermission(final String action) {
		Identity.instance().checkPermission(this.getName(), action);
	}

	public String getBackView() {
		return this.backView;
	}

	public String getName() {
		if (this.name == null) {
			final Name annotation = this.getClass().getAnnotation(Name.class);

			if (annotation != null) {
				this.name = annotation.value();

				this.name = this.name.replaceAll("List$", "");
				this.name = this.name.replaceAll("Edit$", "");
				this.name = this.name.replaceAll("Matrix$", "");
				this.name = this.name.replaceAll("Upload$", "");
			}
		}

		return this.name;
	}

	public StatusMessages getStatusMessages() {
		return this.statusMessages;
	}

	public String getTitle() {
		if (this.title == null) {
			final Title annotation = this.getClass().getAnnotation(Title.class);

			if (annotation != null) {
				this.title = annotation.value();
			}
		}

		return this.title;
	}

	protected Class<?> getTypeArgument(final int pos) {
		final Type type = this.getClass().getGenericSuperclass();
		final ParameterizedType paramType = (ParameterizedType) type;

		return (Class<?>) paramType.getActualTypeArguments()[pos];
	}

	/**
	 * Quando você não estiver satisfeito com a implementação padrão do
	 * tratamento de exceção, sobrescreva este método. O método trataExcecao()
	 * aplica um tratamento padrão que é o de exibir uma mensagem na tela
	 * relacionada com a exceção lançada.
	 * 
	 * @see #performExceptionHandling(Exception)
	 */
	public void handleException(final AbstractApplicationException cause) throws AbstractApplicationException {
		throw cause;
	}

	/**
	 * <p>
	 * Faz o tratamento de exceções de RuntimeException, tratando-as
	 * devidamente. As exceções BusinessException serão sempre convertidas em
	 * mensagens genéricas na tela, e devem ser lançadas apenas pelas classes da
	 * camada de negócio.
	 * </p>
	 * <p>
	 * Caso a exceção não seja tratada, ou caso ela não seja uma
	 * RuntimeException, o tratamento será dado pelo Seam. O mapeamento de
	 * exceções do Seam fica no arquivo de configuração <i>pages.xml</i>.
	 * </p>
	 * 
	 * @param cause
	 *            Exceção a ser tratada.
	 * @throws RuntimeException
	 *             Lança a exceção novamente caso ela não consiga ser tratada.
	 * @see AbstractBusinessException
	 */
	protected void performExceptionHandling(final Exception cause) throws UncaughtException {

		if (cause instanceof DuplicatedException) {
			this.statusMessages.addToControlFromResourceBundle(((DuplicatedException) cause).getProperty(), cause.getMessage());

		} else if (cause instanceof AbstractApplicationException) {
			try {
				this.handleException((AbstractApplicationException) cause);
			} catch (final ViewException e) {
				this.takeCareOf(e);
			} catch (final AbstractInvalidPropertyException e) {
				this.takeCareOf(e);
			} catch (final AbstractBusinessException e) {
				this.takeCareOf(e);
			} catch (final AbstractApplicationException e) {
				throw new UncaughtException(e);
			}
		} else {
			throw new UncaughtException(cause);
		}
	}

	public void setBackView(final String backView) {
		this.backView = backView;
	}

	private void takeCareOf(final AbstractBusinessException exception) {
		this.getStatusMessages().addFromResourceBundle(Severity.valueOf(exception.getSeverity().name()), exception.getMessage(),
				(Object[]) exception.getParams());
	}

	private void takeCareOf(final AbstractInvalidPropertyException exception) {
		this.getStatusMessages().addToControlFromResourceBundle(exception.getProperty(), Severity.valueOf(exception.getSeverity().name()),
				exception.getMessage(), (Object[]) exception.getParams());
	}

	private void takeCareOf(final ViewException exception) {
		if (exception.getFields() != null) {
			for (final Object field : exception.getFields()) {
				this.getStatusMessages().addToControlFromResourceBundle(field.toString(), Severity.valueOf(exception.getSeverity().name()),
						exception.getMessage(), (Object[]) exception.getParams());
			}
		} else {
			this.getStatusMessages().addFromResourceBundle(Severity.valueOf(exception.getSeverity().name()), exception.getMessage(),
					(Object[]) exception.getParams());
		}
	}
}
