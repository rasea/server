package org.rasea.ui.controller;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.rasea.core.domain.Credentials;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;
import br.gov.frameworkdemoiselle.security.SecurityContext;

@Named
@ViewScoped
public class SignInController implements Serializable {

	private static final long serialVersionUID = 6714304822734340771L;

	@Inject
	private SecurityContext context;

	@Inject
	private Credentials credentials;

	@NotNull
	private String loginOrEmail;

	@NotNull
	private String password;

	public void login() {

		// TODO Caso seja passado o e-mail, deve-se obter o usuário pelo e-mail e
		// passar o seu login para autenticação.
		credentials.setUsernameOrEmail(loginOrEmail);
		credentials.setPassword(password);
		context.login();

		// return "pretty:index";
	}

	//	private boolean isEmail() {
	//		return false;
	//	}

	public String getLoginOrEmail() {
		return loginOrEmail;
	}

	public void setLoginOrEmail(String loginOrEmail) {
		this.loginOrEmail = loginOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
