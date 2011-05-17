package org.rasea.core.manager;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Owner;
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("ownerManager")
public class OwnerManager extends AbstractManager {

	private static final long serialVersionUID = -4054157317857389580L;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Application application, final User user) {
		final StringBuffer ejbql = new StringBuffer(120);

		ejbql.append(" delete Owner o ");
		ejbql.append("  where lower(o.username) = lower(:username) ");
		ejbql.append("    and o.application.id = :applicationId ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("username", user.getName());
		query.setParameter("applicationId", application.getId());

		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<User> find(final Application application) {
		final StringBuffer ejbql = new StringBuffer(120);

		ejbql.append(" select new User(lower(o.username)) ");
		ejbql.append("   from Owner o ");
		ejbql.append("  where o.application.id = :applicationId ");
		ejbql.append("  order by o.username ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());
		query.setParameter("applicationId", application.getId());

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Application> find(final User user) {
		final StringBuffer ejbql = new StringBuffer(120);

		ejbql.append(" select o.application ");
		ejbql.append("   from Owner o ");
		ejbql.append("  where lower(o.username) = lower(:username) ");
		ejbql.append("  order by o.application.name ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("username", user.getName());

		return query.getResultList();
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Application application, final User user) {
		this.getEntityManager().persist(new Owner(user.getName(), application));
		this.getEntityManager().flush();
	}

	public Owner load(final Application application, final User user) {
		final StringBuffer ejbql = new StringBuffer(120);

		ejbql.append(" select o ");
		ejbql.append("   from Owner o ");
		ejbql.append("  where lower(o.username) = lower(:username) ");
		ejbql.append("    and o.application.id = :applicationId ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("username", user.getName());
		query.setParameter("applicationId", application.getId());

		Owner result;

		try {
			result = (Owner) query.getSingleResult();
		} catch (final NoResultException cause) {
			result = null;
		}

		return result;
	}
}
