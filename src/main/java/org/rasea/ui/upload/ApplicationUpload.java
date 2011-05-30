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
package org.rasea.ui.upload;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Data;
import org.rasea.core.service.DataService;
import org.rasea.core.util.Constants;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractUpload;

@Name("applicationUpload")
@Title("org.rasea.resource.application")
public class ApplicationUpload extends AbstractUpload {

	private static final long serialVersionUID = -9114028152102034033L;

	@In
	private DataService dataService;

	@In
	@SuppressWarnings("unused")
	@Out(required = false, scope = ScopeType.SESSION)
	private List<Application> ownerships; // NOPMD

	@Override
	public String getBackView() {
		return "/listApplication.xhtml";
	}

	@Override
	protected String handleSend() {
		this.checkPermission(Constants.DEFAULT_OPERATION_IMPORT);

		try {
			final JAXBContext context = JAXBContext.newInstance("org.rasea.core.entity");
			final Unmarshaller unmarshaller = context.createUnmarshaller();
			final Data data = (Data) unmarshaller.unmarshal(this.getData());

			this.dataService.update(data);
			this.ownerships = null;

		} catch (final Exception cause) {
			this.performExceptionHandling(cause);
		}

		return this.getBackView();
	}
}
