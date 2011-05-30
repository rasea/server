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
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("applicationManager")
public class ApplicationManager extends AbstractManager {

	private static final long serialVersionUID = -4087671544839819616L;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Application application) {
		final Query query = this.getEntityManager().createQuery("delete Application a where a.id = :applicationId");

		query.setParameter("applicationId", application.getId());

		query.executeUpdate();
		this.getEntityManager().flush();
	}

	public Application find(final String name) {
		final Query query = this.getEntityManager().createQuery("from Application a where a.name = :name");

		query.setParameter("name", name);

		Application result;

		try {
			result = (Application) query.getSingleResult();
		} catch (final NoResultException cause) {
			result = null;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Application> find(final User owner) {
		final StringBuffer ejbql = new StringBuffer(150);
		ejbql.append(" select o.application ");
		ejbql.append("   from Owner o ");
		ejbql.append("  where lower(o.username) = lower(:username) ");
		ejbql.append("  order by o.application.displayName ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("username", owner.getName());

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Application> findAll() {
		final StringBuffer ejbql = new StringBuffer(50);
		ejbql.append("   from Application a ");
		ejbql.append("  order by a.displayName ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Application> findByFilter(final String filter) {
		final StringBuffer ejbql = new StringBuffer(150);
		ejbql.append("  from Application a ");
		ejbql.append(" where lower(a.name) like :filter ");
		ejbql.append("    or lower(a.displayName) like :filter ");
		ejbql.append(" order by a.displayName ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("filter", "%" + filter + "%");

		return query.getResultList();
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Application application) {
		this.getEntityManager().persist(application);
		this.getEntityManager().flush();
	}

	public Application load(final Long id) {
		return this.getEntityManager().find(Application.class, id);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final Application application) {
		this.getEntityManager().merge(application);
		this.getEntityManager().flush();
	}
}
