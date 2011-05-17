package org.rasea.core.manager;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Member;
import org.rasea.core.entity.Role;
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("memberManager")
public class MemberManager extends AbstractManager {

	private static final long serialVersionUID = 6992206731221008109L;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Role role, final User user) {
		final StringBuffer ejbql = new StringBuffer(100);

		ejbql.append(" delete Member ");
		ejbql.append("  where lower(username) = lower(:username) ");
		ejbql.append("    and role.id = :roleId ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("username", user.getName());
		query.setParameter("roleId", role.getId());

		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Role> find(final Application application, final User user) {
		final StringBuffer ejbql = new StringBuffer(160);

		ejbql.append(" select m.role ");
		ejbql.append("   from Member m ");
		ejbql.append("  where lower(m.username) = lower(:username) ");
		ejbql.append("    and m.role.application.id = :applicationId ");
		ejbql.append("  order by m.role.name ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("username", user.getName());
		query.setParameter("applicationId", application.getId());

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> find(final Role role) {
		final StringBuffer ejbql = new StringBuffer(120);

		ejbql.append(" select new User(lower(m.username)) ");
		ejbql.append("   from Member m ");
		ejbql.append("  where m.role.id = :roleId ");
		ejbql.append("  order by m.username ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());
		query.setParameter("roleId", role.getId());

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Role> find(final User user) {
		final StringBuffer ejbql = new StringBuffer(120);

		ejbql.append(" select m.role ");
		ejbql.append("   from Member m ");
		ejbql.append("  where lower(m.username) = lower(:username) ");
		ejbql.append("  order by m.role.name ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("username", user.getName());

		return query.getResultList();
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Role role, final User user) {
		this.getEntityManager().persist(new Member(user.getName(), role));
		this.getEntityManager().flush();
	}

	public Member load(final Role role, final User user) {
		final StringBuffer ejbql = new StringBuffer(160);

		ejbql.append(" select m ");
		ejbql.append("   from Member m ");
		ejbql.append("  where lower(m.username) = lower(:username) ");
		ejbql.append("    and m.role.application.id = :applicationId ");
		ejbql.append("    and m.role.id = :roleId ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("username", user.getName());
		query.setParameter("applicationId", role.getApplication().getId());
		query.setParameter("roleId", role.getId());

		Member result;

		try {
			result = (Member) query.getSingleResult();
		} catch (final NoResultException cause) {
			result = null;
		}

		return result;
	}
}
