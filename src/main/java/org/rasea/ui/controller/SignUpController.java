package org.rasea.ui.controller;

import java.io.Serializable;

import javax.inject.Inject;

import org.rasea.core.security.Credentials;

import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;

@ViewController
public class SignUpController implements Serializable {

	private static final long serialVersionUID = -2528453695349940601L;

	@Inject
	private SecurityContext context;

	@Inject
	private Credentials credentials;

	//	@NotNull
	private String username;

	//	@Email
	//	@NotNull
	private String email;

	//	@NotNull
	private String password;

	//	@NotNull
	private String confirmPassword;

	public void createAccount() {
		credentials.setUsername(username);
		credentials.setPassword(password);
		context.login();
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
