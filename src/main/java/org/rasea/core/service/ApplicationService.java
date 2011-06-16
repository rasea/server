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
package org.rasea.core.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.rasea.core.domain.Application;
import org.rasea.core.exception.ApplicationAlreadyExistsException;
import org.rasea.core.exception.ApplicationDoesNotExistException;
import org.rasea.core.manager.ApplicationManager;

public class ApplicationService implements Serializable {

	private static final long serialVersionUID = 2750329357752203004L;
	
	@Inject
	private ApplicationManager manager;

	public void create(final Application app) throws ApplicationAlreadyExistsException  {
		
		if (containsName(app.getName())) {
			throw new ApplicationAlreadyExistsException();
		}
		
		manager.create(app);
	}

	public boolean containsName(final String name) {
		return manager.containsName(name);
	}

	public void delete(final Application app) throws ApplicationDoesNotExistException {
		
		if (!containsName(app.getName())) {
			throw new ApplicationDoesNotExistException();
		}
		
		manager.delete(app);
	}

}
