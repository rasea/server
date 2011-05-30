/*
 * Rasea Server
 * 
 * Copyright (c) 2008, Rasea <http://rasea.org>. All rights reserved.
 *
 * Rasea Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see <http://gnu.org/licenses>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
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

		ejbql.append(" select new org.rasea.extensions.entity.User(lower(o.username)) ");
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
