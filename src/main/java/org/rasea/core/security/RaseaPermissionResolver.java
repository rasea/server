package org.rasea.core.security;

import static org.jboss.seam.ScopeType.SESSION;

import java.util.Set;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.permission.PermissionResolver;

@Name("raseaPermissionStore")
@Scope(SESSION)
public class RaseaPermissionResolver implements PermissionResolver {

	@Override
	public boolean hasPermission(Object target, String action) {
		return true;
	}

	@Override
	public void filterSetByAction(Set<Object> targets, String action) {
	}
}
