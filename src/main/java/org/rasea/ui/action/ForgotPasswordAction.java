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
package org.rasea.ui.action;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.rasea.core.service.UserService;
import org.rasea.ui.annotation.Title;

@Name("forgotPasswordAction")
@Title("org.rasea.resource.forgotPassword")
public class ForgotPasswordAction {

	@In
	private UserService userService;

	private String login;

	public void forgotPassword() {
		try {
			userService.resetPassword(login, "TODO");

			FacesMessages.instance().clear();
			FacesMessages.instance().add("Senha enviada com sucesso!");

		} catch (final Throwable cause) {
			FacesMessages.instance().clear();
			FacesMessages.instance().add("Houve um problema inesperado ao tentar recuperar sua senha!");
		}
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}
}
