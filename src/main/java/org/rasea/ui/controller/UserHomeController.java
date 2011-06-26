package org.rasea.ui.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.frameworkdemoiselle.security.SecurityContext;

@Named
@RequestScoped
public class UserHomeController extends AbstractUserController {

	private static final long serialVersionUID = 4469250648226206663L;

	@Inject
	private SecurityContext securityContext;

	@Override
	protected String getUsername() {
		return securityContext.getUser().toString();
	}
}
