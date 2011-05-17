package org.rasea.ui.action;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.management.IdentityManager;
import org.rasea.ui.annotation.Title;

@Name("changePasswordAction")
@Title("org.rasea.resource.changePassword")
public class ChangePasswordAction {

	@In
	private IdentityManager identityManager;

	@In
	private Identity identity;

	@In
	private Credentials credentials;

	private String login;

	private String password;

	private String newPassword;

	public String changePassword() {
		try {
			if (this.identityManager.authenticate(this.login, this.password)) {
				this.credentials.setUsername(this.login);
				this.credentials.setPassword(this.password);

				this.identity.login();
				this.identityManager.changePassword(this.login, this.newPassword);
				this.identity.logout();

				FacesMessages.instance().clear();
				FacesMessages.instance().add("Senha alterada com sucesso!");
			} else {
				FacesMessages.instance().clear();
				FacesMessages.instance().addFromResourceBundle("org.jboss.seam.loginFailed");
			}
			return "/login.xhtml";

		} catch (final Throwable cause) {
			FacesMessages.instance().clear();
			FacesMessages.instance().add("Houve um problema ao tentar alterar sua senha!");
		}
		return "/changePassword.xhtml";
	}

	public String getLogin() {
		return this.login;
	}

	public String getNewPassword() {
		return this.newPassword;
	}

	public String getPassword() {
		return this.password;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
