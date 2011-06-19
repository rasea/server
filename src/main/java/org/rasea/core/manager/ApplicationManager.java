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
package org.rasea.core.manager;

import java.util.ArrayList;
import java.util.List;

import org.rasea.core.domain.Application;

import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

public class ApplicationManager extends AbstractSimpleDBManager<Application> {

	private static final long serialVersionUID = -6271982641758818905L;

	public void create(final Application app) {
		final List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>();
		attrs.add(new ReplaceableAttribute("displayName", app.getDisplayName(), true));

		getSimpleDB().putAttributes(new PutAttributesRequest(getDomainName(), app.getName(), attrs));
	}

	public Application findByName(final String name) {
		Application app = null;

		final GetAttributesResult result = getSimpleDB().getAttributes(new GetAttributesRequest(getDomainName(), name));

		if (result != null) {
			app = new Application(name);
			app = fillAttributes(app, result.getAttributes());
		}

		return app;
	}

	public boolean containsName(final String name) {
		final GetAttributesRequest request = new GetAttributesRequest(getDomainName(), name).withAttributeNames("active");
		final GetAttributesResult result = getSimpleDB().getAttributes(request);

		return (result != null && !result.getAttributes().isEmpty());
	}

	public void delete(final Application app) {
		getSimpleDB().deleteAttributes(new DeleteAttributesRequest(getDomainName(), app.getName()));
	}

	@Override
	protected void fillAttribute(final Application app, final String name, final String value) {
		if ("displayName".equals(name)) {
			app.setDisplayName(value);
			//		} else if ("active".equals(name)) {
			//			app.setActive(new Boolean(value));
		}
	}
}
