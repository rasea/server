package org.rasea.core.manager;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Authorization;
import org.rasea.core.entity.Permission;
import org.rasea.core.entity.Resource;
import org.rasea.core.entity.Role;
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("authorizationManager")
public class AuthorizationManager extends AbstractManager {

	private static final long serialVersionUID = 4573063515425145597L;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Authorization authorization) {
		final Authorization persisted = this.find(authorization.getPermission(), authorization.getRole());
		this.getEntityManager().remove(persisted);
		this.getEntityManager().flush();
	}

	@SuppressWarnings("unchecked")
	public List<Authorization> find(final Application application) {
		final StringBuffer ejbql = new StringBuffer(100);

		ejbql.append(" select a ");
		ejbql.append("   from Authorization a ");
		ejbql.append("  where a.permission.resource.application.id = :appId ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("appId", application.getId());

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Authorization> find(final Application application, final User user, final boolean allowed) {

		final StringBuffer ejbql = new StringBuffer(250);

		ejbql.append(" select a ");
		ejbql.append("   from Authorization a ");
		ejbql.append("  where a.role in ( ");
		ejbql.append("        select m.role ");
		ejbql.append("          from Member m ");
		ejbql.append("         where lower(m.username) = lower(:username) )");
		ejbql.append("    and a.allowed = :allowed ");
		ejbql.append("    and a.permission.resource.application.id = :appId ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("username", user.getName());
		query.setParameter("allowed", allowed);
		query.setParameter("appId", application.getId());

		return query.getResultList();
	}

	public Authorization find(final Permission permission, final Role role) {
		final StringBuffer ejbql = new StringBuffer(150);

		ejbql.append("  from Authorization a ");
		ejbql.append(" where a.permission.resource.id = :resourceId ");
		ejbql.append("   and a.permission.operation.id = :operationId ");
		ejbql.append("   and a.role.id = :roleId ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("resourceId", permission.getResource().getId());
		query.setParameter("operationId", permission.getOperation().getId());
		query.setParameter("roleId", role.getId());

		Authorization result;

		try {
			result = (Authorization) query.getSingleResult();
		} catch (final NoResultException cause) {
			result = null;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Authorization> find(final Resource resource) {
		final Query query = this.getEntityManager().createQuery("from Authorization a where a.permission.resource.id = :resourceId");

		query.setParameter("resourceId", resource.getId());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Authorization> find(final Role role) {
		final StringBuffer ejbql = new StringBuffer(60);

		ejbql.append("  from Authorization a ");
		ejbql.append(" where a.role.id = :roleId ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("roleId", role.getId());

		List<Authorization> result;

		try {
			result = query.getResultList();
		} catch (final NoResultException cause) {
			result = null;
		}

		return result;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Authorization authorization) {
		this.getEntityManager().persist(authorization);
		this.getEntityManager().flush();
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final Authorization authorization) {
		this.getEntityManager().merge(authorization);
		this.getEntityManager().flush();
	}
}
