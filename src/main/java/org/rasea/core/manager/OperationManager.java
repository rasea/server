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

import org.rasea.core.domain.Operation;

import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

public class OperationManager extends AbstractSimpleDBManager<Operation> {

	private static final long serialVersionUID = -5382170734829460106L;

	public void create(final Operation oper) {
		final List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>();
		attrs.add(new ReplaceableAttribute("displayName", oper.getDisplayName(), true));
		getSimpleDB().putAttributes(new PutAttributesRequest(
				getDomainName(oper.getApplication()), oper.getName(), attrs));
	}

	public void delete(final Operation oper) {
		getSimpleDB().deleteAttributes(new DeleteAttributesRequest(
				getDomainName(oper.getApplication()), oper.getName()));
	}

	public boolean exists(final Operation oper) {
		final GetAttributesRequest request = new GetAttributesRequest(
				getDomainName(oper.getApplication()), oper.getName()).withAttributeNames("displayName");
		final GetAttributesResult result = getSimpleDB().getAttributes(request);
		return (result != null && !result.getAttributes().isEmpty());
	}

	@Override
	protected void fillAttribute(final Operation app, final String name, final String value) {
		if ("displayName".equals(name)) {
			app.setDisplayName(value);
		}
	}
}
