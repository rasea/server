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

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.rasea.core.domain.Application;
import org.rasea.core.exception.ApplicationAlreadyExistsException;
import org.rasea.core.service.ApplicationService;

import br.gov.frameworkdemoiselle.message.MessageContext;

@Named
@RequestScoped
public class NewApplicationController extends AbstractController {

	private static final long serialVersionUID = 7252813694049446973L;

	@Inject
	private MessageContext messageContext;

	@Inject
	private ApplicationService service;

	@NotNull
	@Length(min = 1, message = "{required.field}")
	private String name;

	@NotNull
	@Length(min = 1, message = "{required.field}")
	private String displayName;

	private String url;

	public String createApp() throws ApplicationAlreadyExistsException {

		Application app = new Application(name);
		app.setDisplayName(displayName);
		app.setUrl(url);

		service.create(app);

		messageContext.add("Aplicação criada com sucesso.");

		return "pretty:index";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
