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
