package org.rasea.ui.edit;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.service.MemberService;
import org.rasea.core.service.RoleService;
import org.rasea.core.service.UserService;
import org.rasea.extensions.entity.User;
import org.rasea.ui.annotation.Home;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractEdit;

@Name("roleEdit")
@Title("org.rasea.resource.role")
@Home(listView = "/listRole.xhtml", editView = "/editRole.xhtml")
public class RoleEdit extends AbstractEdit<Role> {

	private static final long serialVersionUID = -8469641313880870329L;

	private List<User> allUsers;

	@In
	private Application currentApplication;

	@In
	private MemberService memberService;

	@In
	private RoleService roleService;

	@In
	private UserService userService;

	@Override
	public void checkUpdatePermission() {
		if (!(Identity.instance().hasPermission(this.getName(),
				this.getUpdateOperation()) || Identity.instance()
				.hasPermission("member", this.getUpdateOperation()))) {
			super.checkUpdatePermission();
		}
	}

	@Override
	public Role createInstance() {
		final Role role = new Role(this.currentApplication);
		role.setMembers(new ArrayList<User>());
		role.setEnabled(true);

		return role;
	}

	public List<User> getAllUsers() {
		try {
			if (this.allUsers == null) {
				this.allUsers = this.userService.findAll();
			}

		} catch (final Exception cause) {
			this.performExceptionHandling(cause);
		}

		return this.allUsers;
	}

	@Override
	public String handlePersist() throws AbstractApplicationException {
		if (Identity.instance().hasPermission(this.getName(),
				this.getCreateOperation())) {
			this.roleService.insert(this.getInstance());
		}

		if (Identity.instance().hasPermission("member",
				this.getUpdateOperation())) {
			this.memberService.update(this.currentApplication, this
					.getInstance());
		}

		return this.getListView();
	}

	@Override
	public String handleRemove() throws AbstractApplicationException {
		this.roleService.delete(this.getInstance());

		return this.getListView();
	}

	@Override
	public String handleUpdate() throws AbstractApplicationException {
		if (Identity.instance().hasPermission(this.getName(),
				this.getCreateOperation())) {
			this.roleService.update(this.getInstance());
		}

		if (Identity.instance().hasPermission("member",
				this.getUpdateOperation())) {
			this.memberService.update(this.currentApplication, this
					.getInstance());
		}

		return this.getListView();
	}

	@Override
	public Role loadInstance() throws Exception {
		final Role role = this.roleService.load((Long) this.getId());
		final List<User> members = this.memberService.find(role);
		role.setMembers(members);

		return role;
	}
}
