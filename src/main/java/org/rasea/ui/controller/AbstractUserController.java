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
package org.rasea.ui.controller;

import java.util.List;

import javax.inject.Inject;

import org.rasea.core.domain.Application;
import org.rasea.core.exception.AccountDoesNotExistsException;
import org.rasea.core.exception.EmptyUsernameException;
import org.rasea.core.exception.InvalidUsernameFormatException;
import org.rasea.core.service.ApplicationService;

public abstract class AbstractUserController extends AbstractController {

	private static final long serialVersionUID = -2882591526573519635L;

	@Inject
	private ApplicationService applicationService;

	private List<Application> applications;

	protected abstract String getUsername();

	public List<Application> getApplications() throws EmptyUsernameException, InvalidUsernameFormatException, AccountDoesNotExistsException {
		if (applications == null) {
			applications = applicationService.findByOwner(getUsername());
		}

		return applications;
	}
}
