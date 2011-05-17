package org.rasea.ui.list;

import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Operation;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.service.OperationService;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractList;

@Name("operationList")
@Title("org.rasea.resource.operation")
public class OperationList extends AbstractList<Operation> {

	private static final long serialVersionUID = 7755843532064068899L;

	@In
	private Application currentApplication;

	@In
	private OperationService operationService;

	@Override
	protected List<Operation> handleResultList()
			throws AbstractApplicationException {
		return this.operationService.findByFilter(this.currentApplication, this
				.getSearchString());
	}

	@Override
	protected void handleUpdate() throws AbstractApplicationException {
		this.operationService.update(this.getDataModelSelection());
	}
}
