package org.rasea.ui.util;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.util.Constants;

@Scope(ScopeType.PAGE)
@TransactionManagement(TransactionManagementType.BEAN)
public abstract class AbstractMatrix<E, ROW, COL, CELL> extends AbstractDetail<E> {
	
	private static final long serialVersionUID = 1132362460318376503L;
	
	private List<COL> auxCol;
	
	private List<ROW> auxRow;
	
	private List<CELL> cells;
	
	private List<COL> columns;
	
	private List<ROW> rows;
	
	@Override
	public void checkShowPermission() {
		this.checkPermission(this.getShowOperation());
	}
	
	public void checkUpdatePermission() {
		this.checkPermission(this.getUpdateOperation());
	}
	
	public List<CELL> getCells() {
		if (this.cells == null) {
			try {
				this.cells = this.loadCells();
				
			} catch (final Exception cause) {
				this.performExceptionHandling(cause);
			}
		}
		
		return this.cells;
	}
	
	public List<COL> getColumns() {
		if (this.columns == null) {
			try {
				this.columns = this.loadCols();
				
			} catch (final Exception cause) {
				this.performExceptionHandling(cause);
			}
		}
		
		return this.columns;
	}
	
	public List<ROW> getRows() {
		if (this.rows == null) {
			try {
				this.rows = this.loadRows();
				
			} catch (final Exception cause) {
				this.performExceptionHandling(cause);
			}
		}
		
		return this.rows;
	}
	
	public String getUpdatedMessage() {
		return "br.com.avansys.Entity_updated";
	}
	
	public String getUpdateOperation() {
		return Constants.DEFAULT_OPERATION_UPDATE;
	}
	
	protected abstract void handlePersist(ROW row, COL col, CELL cell) throws Exception;
	
	protected abstract void handleRemove(ROW row, COL col, CELL cell) throws Exception;
	
	protected abstract void handleUpdate(ROW row, COL col, CELL cell) throws Exception;
	
	abstract protected void handleUpdateCol(COL col) throws AbstractApplicationException;
	
	abstract protected void handleUpdateRow(ROW row) throws AbstractApplicationException;
	
	protected abstract CELL loadCell(ROW row, COL col) throws Exception;
	
	private List<CELL> loadCells() throws Exception {
		this.checkShowPermission();
		final List<CELL> result = new ArrayList<CELL>();
		
		this.auxRow = new ArrayList<ROW>();
		this.auxCol = new ArrayList<COL>();
		
		for (final ROW row : this.getRows()) {
			for (final COL col : this.getColumns()) {
				final CELL cell = this.loadCell(row, col);
				
				result.add(cell);
				
				this.auxRow.add(row);
				this.auxCol.add(col);
			}
		}
		
		return result;
	}
	
	protected abstract List<COL> loadCols() throws Exception;
	
	protected abstract List<ROW> loadRows() throws Exception;
	
	@Transactional
	public String update() {
		ROW row;
		COL col;
		CELL cell;
		CELL persisted;
		
		try {
			this.checkUpdatePermission();
			
			for (int i = 0; i < this.getCells().size(); i++) {
				cell = this.getCells().get(i);
				row = this.auxRow.get(i);
				col = this.auxCol.get(i);
				
				persisted = this.loadCell(row, col);
				
				if (persisted == null && cell != null) {
					this.handlePersist(row, col, cell);
					
				} else if (persisted != null && cell == null) {
					this.handleRemove(row, col, persisted);
					
				} else if (persisted != null && cell != null && !persisted.equals(cell)) {
					this.handleUpdate(row, col, cell);
				}
			}
			
			this.getStatusMessages().addFromResourceBundle(this.getUpdatedMessage());
			
		} catch (final Exception cause) {
			this.performExceptionHandling(cause);
		}
		
		return this.getListView();
	}
	
	public void updateCol(final COL col) {
		try {
			this.handleUpdateCol(col);
			
		} catch (final Exception cause) {
			this.performExceptionHandling(cause);
		}
	}
	
	public void updateRow(final ROW row) {
		try {
			this.handleUpdateRow(row);
			
		} catch (final Exception cause) {
			this.performExceptionHandling(cause);
		}
	}
}
