package org.rasea.ui.controller;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.rasea.core.domain.User;
import org.rasea.core.security.Credentials;
import org.rasea.core.service.UserService;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;
import br.gov.frameworkdemoiselle.security.SecurityContext;

@Named
@ViewScoped
public class SignUpController implements Serializable {

	private static final long serialVersionUID = -2528453695349940601L;

	@Inject
	private SecurityContext context;

	@Inject
	private Credentials credentials;

	@Inject
	private UserService service;

	// @NotNull
	private String username;

	// @Email
	// @NotNull
	private String email;

	// @NotNull
	private String password;

	// @NotNull
	private String confirmPassword;

	// @Transactional
	public void createAccount() {
		User user = new User();
		user.setLogin(username);
		user.setEmail(email);
		user.setPassword(password);
		service.createAccount(user);

		credentials.setUsername(username);
		credentials.setPassword(password);
		context.login();

		// return "pretty:index";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
