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
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.rasea.core.entity.Application;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.exception.StoreException;
import org.rasea.core.service.ApplicationService;
import org.rasea.core.service.OwnerService;
import org.rasea.core.service.UserService;
import org.rasea.extensions.entity.User;
import org.rasea.ui.annotation.Home;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractEdit;

@Name("applicationEdit")
@Title("org.rasea.resource.application")
@Home(listView = "/listApplication.xhtml", editView = "/editApplication.xhtml")
public class ApplicationEdit extends AbstractEdit<Application> {

	private static final long serialVersionUID = -5058520340034598386L;

	private List<User> allUsers;

	@In
	private ApplicationService applicationService;

	@In
	private Credentials credentials;

	@In
	@Out(required = false)
	private Application currentApplication;

	@In
	private OwnerService ownerService;

	@In
	@SuppressWarnings("unused")
	@Out(required = false, scope = ScopeType.SESSION)
	private List<Application> ownerships;

	@In
	private UserService userService;

	@Override
	public void checkUpdatePermission() {
		if (!(Identity.instance().hasPermission(this.getName(), this.getUpdateOperation()) || Identity.instance().hasPermission("owner",
				this.getUpdateOperation()))) {
			super.checkUpdatePermission();
		}
	}

	@Override
	protected Application createInstance() {
		final Application instance = super.createInstance();

		try {
			final User owner = this.userService.load(this.credentials.getUsername());
			final List<User> owners = new ArrayList<User>();
			owners.add(owner);
			instance.setOwners(owners);

		} catch (final Exception cause) {
			this.performExceptionHandling(cause);
		}

		return instance;
	}

	public List<User> getAllUsers() {
		try {
			if (this.allUsers == null) {
				this.allUsers = this.userService.findAll();
			}

		} catch (final StoreException cause) {
			this.performExceptionHandling(cause);
		}

		return this.allUsers;
	}

	@Override
	protected String handlePersist() throws AbstractApplicationException {
		if (Identity.instance().hasPermission(this.getName(), this.getCreateOperation())) {
			this.applicationService.insert(this.getInstance());
			this.currentApplication = this.getInstance();
		}

		if (Identity.instance().hasPermission("owner", this.getUpdateOperation())) {
			this.ownerService.update(this.getInstance());
		}

		this.ownerships = null;
		return this.getListView();
	}

	@Override
	protected String handleRemove() throws AbstractApplicationException {
		this.applicationService.delete(this.getInstance());

		if (this.currentApplication.equals(this.getInstance())) {
			this.currentApplication = null;
		}
		this.ownerships = null;

		return this.getListView();
	}

	@Override
	protected String handleUpdate() throws AbstractApplicationException {
		if (Identity.instance().hasPermission(this.getName(), this.getUpdateOperation())) {
			this.applicationService.update(this.getInstance());
		}

		if (Identity.instance().hasPermission("owner", this.getUpdateOperation())) {
			this.ownerService.update(this.getInstance());
		}

		this.ownerships = null;
		return this.getListView();
	}

	@Override
	public Application loadInstance() throws Exception {
		final Application application = this.applicationService.load((Long) this.getId());
		final List<User> owners = this.ownerService.find(application);
		application.setOwners(owners);

		return application;
	}
}
