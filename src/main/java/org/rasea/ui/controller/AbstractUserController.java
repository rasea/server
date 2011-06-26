package org.rasea.ui.controller;

import java.util.List;

import javax.inject.Inject;

import org.rasea.core.domain.Application;
import org.rasea.core.exception.AccountDoesNotExistsException;
import org.rasea.core.exception.EmptyUsernameException;
import org.rasea.core.exception.InvalidUsernameFormatException;
import org.rasea.core.service.ApplicationService;

public abstract class AbstractUserController extends AbstractController {

	private static final long serialVersionUID = -2882591526573519635L;

	@Inject
	private ApplicationService applicationService;

	private List<Application> applications;

	public void loadApplications() throws EmptyUsernameException, InvalidUsernameFormatException, AccountDoesNotExistsException {
		applications = applicationService.findByOwner(getUsername());
	}

	protected abstract String getUsername();

	public List<Application> getApplications() throws EmptyUsernameException, InvalidUsernameFormatException, AccountDoesNotExistsException {
		if (applications == null) {
			loadApplications();
		}

		return applications;
	}
}
