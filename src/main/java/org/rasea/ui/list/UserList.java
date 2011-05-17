package org.rasea.ui.list;

import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.service.UserService;
import org.rasea.extensions.entity.User;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractList;

@Name("userList")
@Title("org.rasea.resource.user")
public class UserList extends AbstractList<User> {

	private static final long serialVersionUID = -2590213516229601914L;

	@In
	private UserService userService;

	@Override
	protected List<User> handleResultList() throws AbstractApplicationException {
		return this.userService.findByFilter(this.getSearchString());
	}

	@Override
	protected void handleUpdate() throws AbstractApplicationException {
		this.userService.update(this.getDataModelSelection());
	}
}
