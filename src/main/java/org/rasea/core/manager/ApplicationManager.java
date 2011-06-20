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

import br.gov.frameworkdemoiselle.util.Strings;

import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;

public class ApplicationManager extends AbstractSimpleDBManager<Application> {

	private static final long serialVersionUID = -6271982641758818905L;

	public void create(final Application app) {
		final List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>();
		attrs.add(new ReplaceableAttribute("displayName", app.getDisplayName(), true));
		if (!Strings.isEmpty(app.getUrl()))
			attrs.add(new ReplaceableAttribute("url", app.getUrl(), true));
		final String owner = app.getOwners().iterator().next();
		attrs.add(new ReplaceableAttribute("owners", owner, true));
		getSimpleDB().putAttributes(new PutAttributesRequest(getDomainName(), app.getName(), attrs));
	}

	public Application findByName(final String name) {
		Application app = null;
		final GetAttributesResult result = getSimpleDB().getAttributes(
				new GetAttributesRequest(getDomainName(), name));
		if (result != null) {
			app = new Application(name);
			app = fillAttributes(app, result.getAttributes());
		}
		return app;
	}

	public boolean containsName(final String name) {
		final GetAttributesRequest request = new GetAttributesRequest(
				getDomainName(), name).withAttributeNames("displayName");
		final GetAttributesResult result = getSimpleDB().getAttributes(request);
		return (result != null && !result.getAttributes().isEmpty());
	}

	public void delete(final Application app) {
		getSimpleDB().deleteAttributes(new DeleteAttributesRequest(getDomainName(), app.getName()));
	}

	public List<Application> getApplicationsFromOwner(final String owner) {
		List<Application> list = null;

		final String expr = "select * from `" + getDomainName() + "` where owners = '" + owner + "'";
		final SelectRequest request = new SelectRequest(expr);
		final SelectResult result = getSimpleDB().select(request);

		if (result != null) {
			for (Item item : result.getItems()) {
				Application app = new Application(item.getName());
				app = fillAttributes(app, item.getAttributes());
				if (list == null)
					list = new ArrayList<Application>();
				list.add(app);
			}
		}
		return list;
	}

	@Override
	protected void fillAttribute(final Application app, final String name, final String value) {
		if ("displayName".equals(name)) {
			app.setDisplayName(value);
		} else if ("url".equals(name)) {
			app.setUrl(value);
		} else if ("owners".equals(name)) {
			app.getOwners().add(value);
		}
	}
}
