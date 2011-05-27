package org.rasea.ui.action;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.management.IdentityManager;
import org.rasea.ui.annotation.Title;

@Name("forgotPasswordAction")
@Title("org.rasea.resource.forgotPassword")
public class ForgotPasswordAction {

	private String login;

	@In
	private IdentityManager identityManager;

	public void forgotPassword() {
		//FIXME Rever apos a retirada do agente
//		try {
//			final RaseaIdentityStore identityStore = (RaseaIdentityStore) this.identityManager.getIdentityStore();
//
//			identityStore.resetPassword(this.login, "TODO");
//			FacesMessages.instance().clear();
//			FacesMessages.instance().add("Senha enviada com sucesso!");
//
//		} catch (final Throwable cause) {
//			FacesMessages.instance().clear();
//			FacesMessages.instance().add("Houve um problema inesperado ao tentar recuperar sua senha!");
//		}
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}
}
