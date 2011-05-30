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

import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.model.DataModel;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.DataModels;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.util.Constants;

/**
 * @author cleverson.sacramento
 * @param <E>
 */
@Scope(ScopeType.PAGE)
@TransactionManagement(TransactionManagementType.BEAN)
public abstract class AbstractList<E> extends AbstractViewHelper {

	private static final long serialVersionUID = -4791833905174038622L;

	private DataModel dataModel;

	private Class<E> entityClass;

	private String searchString;

	public void checkShowPermission() {
		this.checkPermission(this.getShowOperation());
	}

	protected void clearDataModel() {
		this.dataModel = null;
	}

	public DataModel getDataModel() {
		if (this.dataModel == null) {
			this.checkShowPermission();
			List<E> resultList = null;

			try {
				resultList = this.handleResultList();

			} catch (final Exception cause) {
				this.performExceptionHandling(cause);
			}

			if (resultList == null) {
				resultList = new ArrayList<E>();
			}

			this.dataModel = DataModels.instance().getDataModel(resultList);
		}

		return this.dataModel;
	}

	@SuppressWarnings("unchecked")
	public E getDataModelSelection() {
		return (E) this.getDataModel().getRowData();
	}

	@SuppressWarnings("unchecked")
	protected Class<E> getEntityClass() {
		if (this.entityClass == null) {
			this.entityClass = (Class<E>) this.getTypeArgument(0);
		}

		return this.entityClass;
	}

	public Integer getResultCount() {
		return this.getDataModel().getRowCount();
	}

	public String getSearchString() {
		return this.searchString;
	}

	public String getShowOperation() {
		return Constants.DEFAULT_OPERATION_SHOW;
	}

	protected abstract List<E> handleResultList() throws AbstractApplicationException;

	abstract protected void handleUpdate() throws AbstractApplicationException;

	public void refresh() {
		this.clearDataModel();
	}

	public void search() {
		this.clearDataModel();
	}

	public void setSearchString(final String searchString) {
		this.searchString = searchString;
	}

	public void update() {
		try {
			this.handleUpdate();

		} catch (final Exception cause) {
			this.performExceptionHandling(cause);
		}
	}
}
