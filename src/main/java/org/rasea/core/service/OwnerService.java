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
package org.rasea.core.service;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.rasea.core.configuration.Settings;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Owner;
import org.rasea.core.exception.DuplicatedException;
import org.rasea.core.exception.IntegrityException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.manager.OwnerManager;
import org.rasea.core.util.ServiceValidator;
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("ownerService")
public class OwnerService extends AbstractService {

	private static final long serialVersionUID = -8875803399834729567L;

	@In
	private Application defaultApplication;

	@In
	private OwnerManager ownerManager;

	@In
	private ServiceValidator serviceValidator;

	@In
	private Settings settings;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Application application, final User user) throws RequiredException, IntegrityException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());
		this.serviceValidator.validateRequired("user", user);
		this.serviceValidator.validateRequired("user.username", user.getName());

		final Owner owner = this.ownerManager.load(application, user);

		if (owner != null) {
			if (owner.getApplication().equals(this.defaultApplication) && this.settings.getAdmin().getUsername().equals(user.getName())) {
				throw new IntegrityException("Não é possível excluir o usuário administrador " + this.settings.getAdmin().getUsername());
			}

			this.ownerManager.delete(application, user);
		}
	}

	public List<User> find(final Application application) throws RequiredException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());

		return this.ownerManager.find(application);
	}

	public List<Application> find(final User user) throws RequiredException {
		this.serviceValidator.validateRequired("user", user);
		this.serviceValidator.validateRequired("user.username", user.getName());

		return this.ownerManager.find(user);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Application application, final User user) throws RequiredException, DuplicatedException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());
		this.serviceValidator.validateRequired("user", user);
		this.serviceValidator.validateRequired("user.username", user.getName());

		if (this.ownerManager.load(application, user) == null) {
			this.ownerManager.insert(application, user);
		}
	}

	public Owner load(final Application application, final User user) {
		return this.ownerManager.load(application, user);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final Application application) throws RequiredException, DuplicatedException, IntegrityException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());

		final List<User> selected = new ArrayList<User>();

		if (application.getOwners() != null) {
			for (final User user : application.getOwners()) {
				selected.add(user);
			}
		}

		final List<User> current = this.find(application);

		final List<User> toDelete = new ArrayList<User>(current);
		toDelete.removeAll(selected);

		for (final User user : toDelete) {
			this.delete(application, user);
		}

		final List<User> toInsert = new ArrayList<User>(selected);
		toInsert.removeAll(current);

		for (final User user : toInsert) {
			this.insert(application, user);
		}
	}

	public void update(final User user, final List<Application> ownership) throws RequiredException, DuplicatedException, IntegrityException {
		this.serviceValidator.validateRequired("user", user);
		this.serviceValidator.validateRequired("user.username", user.getName());

		final List<Application> selected = new ArrayList<Application>();

		if (ownership != null) {
			for (final Application application : ownership) {
				selected.add(application);
			}
		}

		final List<Application> current = this.find(user);

		final List<Application> toDelete = new ArrayList<Application>(current);
		toDelete.removeAll(selected);

		for (final Application application : toDelete) {
			this.delete(application, user);
		}

		final List<Application> toInsert = new ArrayList<Application>(selected);
		toInsert.removeAll(current);

		for (final Application application : toInsert) {
			this.insert(application, user);
		}
	}

}
