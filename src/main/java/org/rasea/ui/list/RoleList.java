package org.rasea.ui.list;

import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.service.RoleService;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractList;

@Name("roleList")
@Title("org.rasea.resource.role")
public class RoleList extends AbstractList<Role> {

	private static final long serialVersionUID = 8196809827368355031L;

	@In
	private Application currentApplication;

	@In
	private RoleService roleService;

	@Override
	protected List<Role> handleResultList() throws AbstractApplicationException {
		return this.roleService.findByFilter(this.currentApplication, this
				.getSearchString());
	}

	@Override
	protected void handleUpdate() throws AbstractApplicationException {
		this.roleService.update(this.getDataModelSelection());
	}
}
