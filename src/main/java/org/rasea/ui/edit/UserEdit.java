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
package org.rasea.ui.edit;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.security.Identity;
import org.rasea.core.configuration.Settings;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.service.MemberService;
import org.rasea.core.service.OwnerService;
import org.rasea.core.service.UserService;
import org.rasea.extensions.entity.User;
import org.rasea.ui.annotation.Home;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractEdit;

@Name("userEdit")
@Title("org.rasea.resource.user")
@Home(listView = "/listUser.xhtml", editView = "/editUser.xhtml")
public class UserEdit extends AbstractEdit<User> {

	private static final long serialVersionUID = 3655685893144283688L;

	@In
	private Application currentApplication;

	@In
	private MemberService memberService;

	@In
	private OwnerService ownerService;

	private List<Role> memberOf;

	private List<Application> ownership;

	@In
	@SuppressWarnings("unused")
	@Out(required = false, scope = ScopeType.SESSION)
	private List<Application> ownerships;

	@In
	private Settings settings;

	@In
	private UserService userService;

	@Override
	public void checkUpdatePermission() {
		if (!(Identity.instance().hasPermission(this.getName(), this.getUpdateOperation())
				|| Identity.instance().hasPermission("member", this.getUpdateOperation()) || Identity.instance().hasPermission("owner",
				this.getUpdateOperation()))) {
			super.checkUpdatePermission();
		}
	}

	@Override
	public User createInstance() {
		final User user = new User();

		this.setMemberOf(new ArrayList<Role>());
		this.setOwnership(new ArrayList<Application>());
		user.setEnabled(true);

		return user;
	}

	public List<Role> getMemberOf() {
		return this.memberOf;
	}

	public List<Application> getOwnership() {
		return this.ownership;
	}

	@Override
	public String handlePersist() throws AbstractApplicationException {
		try {
			if (Identity.instance().hasPermission(this.getName(), this.getCreateOperation())) {
				this.userService.insert(this.getInstance());
			}

			if (Identity.instance().hasPermission("member", this.getUpdateOperation())) {
				this.memberService.update(this.currentApplication, this.getInstance(), this.getMemberOf());
			}

			if (Identity.instance().hasPermission("owner", this.getUpdateOperation())) {
				this.ownerService.update(this.getInstance(), this.getOwnership());
				this.ownerships = null;
			}

		} catch (final Exception cause) {
			this.performExceptionHandling(cause);
		}

		return this.getListView();
	}

	@Override
	public String handleRemove() throws AbstractApplicationException {
		this.userService.delete(this.getInstance());
		return this.getListView();
	}

	@Override
	public String handleUpdate() throws AbstractApplicationException {
		if (!this.settings.getStore().isReadonly() && Identity.instance().hasPermission(this.getName(), this.getUpdateOperation())) {
			this.userService.update(this.getInstance());
		}

		if (Identity.instance().hasPermission("member", this.getUpdateOperation())) {
			this.memberService.update(this.currentApplication, this.getInstance(), this.getMemberOf());
		}

		if (Identity.instance().hasPermission("owner", this.getUpdateOperation())) {
			this.ownerService.update(this.getInstance(), this.getOwnership());
			this.ownerships = null;
		}

		return this.getListView();
	}

	@Override
	public User loadInstance() throws Exception {
		final User user = this.userService.load((String) this.getId());

		final List<Role> memberOf = this.memberService.find(this.currentApplication, user);
		this.setMemberOf(memberOf);

		final List<Application> ownership = this.ownerService.find(user);
		this.setOwnership(ownership);

		return user;
	}

	public void setMemberOf(final List<Role> memberOf) {
		this.memberOf = memberOf;
	}

	public void setOwnership(final List<Application> ownership) {
		this.ownership = ownership;
	}
}
