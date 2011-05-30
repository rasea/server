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
