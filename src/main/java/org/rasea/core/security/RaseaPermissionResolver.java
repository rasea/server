package org.rasea.core.security;

import static org.jboss.seam.ScopeType.SESSION;
import static org.jboss.seam.security.management.IdentityManager.PERMISSION_UPDATE;
import static org.jboss.seam.security.management.IdentityManager.USER_PERMISSION_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.management.IdentityManager;
import org.jboss.seam.security.permission.PermissionResolver;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Permission;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.exception.StoreException;
import org.rasea.core.service.ApplicationService;
import org.rasea.core.service.PermissionService;
import org.rasea.core.service.UserService;
import org.rasea.extensions.entity.User;

@Name("raseaPermissionStore")
@Scope(SESSION)
public class RaseaPermissionResolver implements PermissionResolver {

	@In
	private PermissionService permissionService;

	@In
	private ApplicationService applicationService;

	@In
	private UserService userService;

	@In
	private Credentials credentials;

	@Logger
	private Log log;

	private List<PermissionCache> cache = new ArrayList<PermissionCache>();

	private Application application;

	@Observer("org.jboss.seam.security.loginSuccessful")
	public void load() {
		try {
			application = applicationService.find("rasea-server");
			User user = userService.load(credentials.getUsername());

			List<Permission> permisssions = permissionService.find(application, user);

			String resource, operation;
			for (Permission permission : permisssions) {
				resource = permission.getResource().getName();
				operation = permission.getOperation().getName();

				cache.add(new PermissionCache(resource, operation));
				log.info("application #0: allow operation #1 on #2 to #3", application.getName(), permission
						.getOperation().getName(), permission.getResource().getName(), user.getName());
			}
			
			cache.add(new PermissionCache(USER_PERMISSION_NAME, PERMISSION_UPDATE));

		} catch (RequiredException cause) {
			throw new RuntimeException(cause);

		} catch (StoreException cause) {
			throw new RuntimeException(cause);
		}
	}

	@Override
	public boolean hasPermission(Object resource, String operation) {
		PermissionCache permission = new PermissionCache(resource.toString(), operation);
		return cache.contains(permission);
	}

	@Override
	public void filterSetByAction(Set<Object> targets, String action) {
	}
}
