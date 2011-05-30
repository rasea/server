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

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityExistsException;
import javax.persistence.OptimisticLockException;

import org.hibernate.exception.ConstraintViolationException;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.framework.EntityHome;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.util.Constants;
import org.rasea.ui.annotation.Home;
import org.rasea.ui.exception.PersistConstraintException;
import org.rasea.ui.exception.RemoveConstraintException;
import org.rasea.ui.exception.UncaughtException;
import org.rasea.ui.exception.UpdateConstraintException;

/**
 * <p>
 * As classes Home devem ser utilizadas para facilitar o desenvolvimento de
 * telas do tipo CRUD. A nomenclatura "Home" é utilizada pelo JBoss Seam
 * Framework e está documentada na referência oficial da ferramenta.
 * </p>
 * <p>
 * Quando começamos a utilizar o Seam, e as classes Home, percebemos que, apesar
 * de abstrair bastante código, ainda digitávamos uma porção de código
 * repetitivo. Daí então, criamos uma abstração Home e colocamos o código
 * repetido das classes concretas dentro da abstração. Uma parte deste código
 * precisava de parâmetros, criamos uma anotação para isso, que é lida na classe
 * pai de todos os nossos Home croncretos.
 * </p>
 * <p>
 * Criamos na abstração esta classe, que herda do EntityHome do Seam. Tomamos
 * como base o EntityHome, por ele tratar com EntityManager. Entretanto,
 * percebemos que podíamos melhorar algumas impementações da classe EntityHome
 * do Seam Framework. Com isso, sobrescrevemos alguns métodos, alguns deles
 * copiamos código-fonte e melhoramos (update, perist e remove). As classes
 * concretas devem evitar herdar diretamente desta classe. O ideal que se
 * utilize uma de suas especializações que atenda melhor a sua estratégia de
 * Home.
 * </p>
 * 
 * @author cleverson.sacramento
 * @param <E>
 *            Entidade a ser gerenciada pelo Home para telas CRUD.
 * @see EntityHome
 * @see Home
 */
@Scope(ScopeType.PAGE)
@TransactionManagement(TransactionManagementType.BEAN)
public abstract class AbstractEdit<E> extends AbstractDetail<E> {

	private static final long serialVersionUID = 5092490236195631942L;

	private E instance;

	public void checkCreatePermission() {
		this.checkPermission(this.getCreateOperation());
	}

	public void checkDeletePermission() {
		this.checkPermission(this.getDeleteOperation());
	}

	public void checkUpdatePermission() {
		this.checkPermission(this.getUpdateOperation());
	}

	protected E createInstance() {
		E instance = null;

		try {
			instance = this.getEntityClass().newInstance();

		} catch (final InstantiationException cause) {
			// TODO
			cause.printStackTrace();

		} catch (final IllegalAccessException cause) {
			// TODO
			cause.printStackTrace();
		}

		return instance;
	}

	public String getCreatedMessage() {
		return Constants.MESSAGE_KEY_PREFIX + "_created";
	}

	public String getCreateOperation() {
		return Constants.DEFAULT_OPERATION_INSERT;
	}

	public String getDeletedMessage() {
		return Constants.MESSAGE_KEY_PREFIX + "_deleted";
	}

	public String getDeleteOperation() {
		return Constants.DEFAULT_OPERATION_DELETE;
	}

	@Override
	public E getInstance() {
		if (this.instance == null) {
			this.checkShowPermission();

			if (this.isManaged()) {
				this.instance = super.getInstance();
			} else {
				this.instance = this.createInstance();
			}
		}

		return this.instance;
	}

	public String getUpdatedMessage() {
		return Constants.MESSAGE_KEY_PREFIX + "_updated";
	}

	public String getUpdateOperation() {
		return Constants.DEFAULT_OPERATION_UPDATE;
	}

	/**
	 * Quando você não estiver satisfeito com a implementação padrão do método
	 * persist(), sobrescreva este método. O método persist() faz muito mais
	 * coisa, tal como: ingressar numa transação, definir a mensagem padrão de
	 * sucesso, informar que a transação foi bem sucedida. Como você não vai
	 * querer se preocupar com tudo isso novamente, sobrescreva este método
	 * aqui, quando for necessário!
	 * 
	 * @return URL da página para onde será redirecionado.
	 */
	protected abstract String handlePersist() throws AbstractApplicationException;

	/**
	 * Quando você não estiver satisfeito com a implementação padrão do método
	 * remove(), sobrescreva este método. O método remove() faz muito mais
	 * coisa, tal como: ingressar numa transação, definir a mensagem padrão de
	 * sucesso, informar que a transação foi bem sucedida. Como você não vai
	 * querer se preocupar com tudo isso novamente, sobrescreva este método
	 * aqui, quando for necessário!
	 * 
	 * @return URL da página para onde será redirecionado.
	 */
	protected abstract String handleRemove() throws AbstractApplicationException;

	/**
	 * Quando você não estiver satisfeito com a implementação padrão do método
	 * update(), sobrescreva este método. O método update() faz muito mais
	 * coisa, tal como: ingressar numa transação, definir a mensagem padrão de
	 * sucesso, informar que a transação foi bem sucedida. Como você não vai
	 * querer se preocupar com tudo isso novamente, sobrescreva este método
	 * aqui, quando for necessário!
	 * 
	 * @return URL da página para onde será redirecionado.
	 */
	protected abstract String handleUpdate() throws AbstractApplicationException;

	/**
	 * Indica se está na funcionalidade de edição (true) ou de novo registro
	 * (false).
	 * 
	 * @see org.jboss.seam.framework.EntityHome#isManaged()
	 * @return Está na funcionalidade de edição (true) ou de novo registro
	 *         (false)?
	 */
	public boolean isManaged() {
		return this.getId() != null && !"".equals(this.getId());
	}

	@Override
	protected void performExceptionHandling(final Exception cause) throws UncaughtException {
		if (cause instanceof RequiredException) {
			this.getStatusMessages().addToControlFromResourceBundle(((RequiredException) cause).getProperty(),
					"javax.faces.component.UIInput.REQUIRED");
		} else if (cause instanceof UpdateConstraintException) {
			this.getStatusMessages().addFromResourceBundle(cause.getMessage());
		} else if (cause instanceof RemoveConstraintException) {
			this.getStatusMessages().addFromResourceBundle(cause.getMessage());
		} else if (cause instanceof PersistConstraintException) {
			this.getStatusMessages().addFromResourceBundle(cause.getMessage());
		} else if (cause instanceof OptimisticLockException) {
			this.getStatusMessages().addFromResourceBundle("javax.persistence.OptimisticLock");
		} else {
			super.performExceptionHandling(cause);
		}
	}

	/**
	 * Não sobrescreva este método! Se você está querendo sobrescrevê-lo,
	 * provavelmente você deve sobrescrever o método handle. Esta implementação
	 * é uma cópia quase perfeita do EntityHome do Seam, com alguns ajustes.
	 * 
	 * @see #handlePersist()
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 * @return URL da página para onde será redirecionado.
	 */
	@Transactional
	public String persist() {
		String result = this.getEditView();

		try {
			this.checkCreatePermission();
			try {
				result = this.handlePersist();
				this.getStatusMessages().addFromResourceBundle(this.getCreatedMessage());

			} catch (final EntityExistsException e) {
				throw new PersistConstraintException();

			} catch (final ConstraintViolationException e) {
				throw new PersistConstraintException();
			}

		} catch (final Exception e) {
			result = null;
			this.performExceptionHandling(e);
		}

		return result;
	}

	/**
	 * Não sobrescreva este método! Se você está querendo sobrescrevê-lo,
	 * provavelmente você deve sobrescrever o método handle. Esta implementação
	 * é uma cópia quase perfeita do EntityHome do Seam, com alguns ajustes.
	 * 
	 * @see #handleRemove()
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 * @return URL da página para onde será redirecionado.
	 */
	@Transactional
	public String remove() {
		String result = this.getEditView();

		try {
			this.checkDeletePermission();
			try {
				result = this.handleRemove();
				this.getStatusMessages().addFromResourceBundle(this.getDeletedMessage());

			} catch (final EntityExistsException e) {
				throw new RemoveConstraintException();

			} catch (final ConstraintViolationException e) {
				throw new RemoveConstraintException();
			}

		} catch (final Exception e) {
			result = null;
			this.performExceptionHandling(e);
		}

		return result;
	}

	/**
	 * Não sobrescreva este método! Se você está querendo sobrescrevê-lo,
	 * provavelmente você deve sobrescrever o método handle. Esta implementação
	 * é uma cópia quase perfeita do EntityHome do Seam, com alguns ajustes.
	 * 
	 * @see #handleUpdate()
	 * @see org.jboss.seam.framework.EntityHome#update()
	 * @return URL da página para onde será redirecionado.
	 */
	@Transactional
	public String update() {
		String result = this.getEditView();

		try {
			this.checkUpdatePermission();
			try {
				result = this.handleUpdate();
				this.getStatusMessages().addFromResourceBundle(this.getUpdatedMessage());

			} catch (final EntityExistsException e) {
				throw new UpdateConstraintException();

			} catch (final ConstraintViolationException e) {
				throw new UpdateConstraintException();
			}

		} catch (final Exception e) {
			result = null;
			this.performExceptionHandling(e);
		}

		return result;
	}

}
