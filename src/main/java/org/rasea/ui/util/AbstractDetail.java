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
package org.rasea.ui.util;

import org.jboss.seam.util.Strings;
import org.rasea.core.util.Constants;
import org.rasea.ui.annotation.Home;

/**
 * @author cleverson.sacramento
 */
public abstract class AbstractDetail<E> extends AbstractViewHelper {

	private static final long serialVersionUID = -1951887429348423792L;

	private String editView;

	private Class<E> entityClass;

	private Object id;

	private E instance;

	private String listView;

	private String capitalize(final String string) {
		String result = null;

		if (!Strings.isEmpty(string)) {
			result = string.toUpperCase().charAt(0) + (string.length() > 1 ? string.substring(1) : "");

		}

		return result;
	}

	public void checkShowPermission() {
		this.checkPermission(this.getShowOperation());
	}

	protected void clearInstance() {
		this.instance = null;
		this.id = null;
	}

	public String getEditView() {
		if (this.editView == null) {
			final Home annotation = this.getClass().getAnnotation(Home.class);

			if (annotation != null) {
				this.editView = annotation.editView();
			} else {
				this.editView = "/edit" + this.capitalize(this.getName()) + ".xhtml";
			}
		}

		return this.editView;
	}

	/**
	 * Obtém o Class referente é entidade gerenciada. Este método foi
	 * sobrescrito, para que a abstração busque o parâmetro gen�rico
	 * corretamente.
	 * 
	 * @see org.jboss.seam.framework.Home#getEntityClass()
	 * @return Class referente é entidade gerenciada.
	 */
	@SuppressWarnings("unchecked")
	protected Class<E> getEntityClass() {
		if (this.entityClass == null) {
			this.entityClass = (Class<E>) this.getTypeArgument(0);
		}

		return this.entityClass;
	}

	public Object getId() {
		return this.id;
	}

	public E getInstance() {
		if (this.instance == null) {
			try {
				this.checkShowPermission();
				this.instance = this.loadInstance();

			} catch (final Exception cause) {
				this.performExceptionHandling(cause);
			}
		}

		return this.instance;
	}

	public String getListView() {
		if (this.listView == null) {
			final Home annotation = this.getClass().getAnnotation(Home.class);

			if (annotation != null) {
				this.listView = annotation.listView();
			} else {
				this.listView = "/list" + this.capitalize(this.getName()) + ".xhtml";
			}
		}

		return this.listView;
	}

	public String getShowOperation() {
		return Constants.DEFAULT_OPERATION_SHOW;
	}

	/**
	 * Carrega a instância da entidade com base no id recebido. Cada Home filho
	 * deve implementar a sua estratégia.
	 * 
	 * @see org.jboss.seam.framework.EntityHome#getId()
	 * @return Instância da entidade.
	 */
	protected abstract E loadInstance() throws Exception;

	public void setId(final Object id) {
		this.id = id;
		this.getInstance();
	}

	protected void setInstance(final E instance) {
		this.instance = instance;
	}
}
