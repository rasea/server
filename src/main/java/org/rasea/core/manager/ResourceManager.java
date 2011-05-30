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
import org.rasea.core.entity.Resource;

@AutoCreate
@Name("resourceManager")
public class ResourceManager extends AbstractManager {

	private static final long serialVersionUID = 7240576984663390976L;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Resource resource) {
		final Query query = this.getEntityManager().createQuery("delete Resource r where r.id = :resourceId");

		query.setParameter("resourceId", resource.getId());

		query.executeUpdate();
		this.getEntityManager().flush();
	}

	@SuppressWarnings("unchecked")
	public List<Resource> find(final Application application) {
		final StringBuffer ejbql = new StringBuffer(80);

		ejbql.append("  from Resource r ");
		ejbql.append(" where r.application.id = :applicationId ");
		ejbql.append(" order by r.name ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("applicationId", application.getId());

		return query.getResultList();
	}

	public Resource find(final Application application, final String name) {
		final StringBuffer ejbql = new StringBuffer(100);

		ejbql.append("  from Resource r ");
		ejbql.append(" where r.application.id = :applicationId ");
		ejbql.append("   and r.name = :name ");
		ejbql.append(" order by r.name ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("applicationId", application.getId());
		query.setParameter("name", name);

		Resource result;

		try {
			result = (Resource) query.getSingleResult();
		} catch (final NoResultException cause) {
			result = null;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Resource> findByFilter(final Application application, final String filter) {
		final StringBuffer ejbql = new StringBuffer(160);

		ejbql.append("  from Resource r ");
		ejbql.append(" where r.application.id = :applicationId ");
		ejbql.append("   and (lower(r.name) like :filter ");
		ejbql.append("    or  lower(r.displayName) like :filter) ");
		ejbql.append("  order by r.name ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("filter", "%" + filter + "%");
		query.setParameter("applicationId", application.getId());

		return query.getResultList();
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Resource resource) {
		this.getEntityManager().persist(resource);
		this.getEntityManager().flush();
	}

	public Resource load(final Long id) {
		final Query query = this.getEntityManager().createQuery("from Resource r where r.id = :id");

		query.setParameter("id", id);

		Resource result;

		try {
			result = (Resource) query.getSingleResult();
		} catch (final NoResultException cause) {
			result = null;
		}

		return result;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final Resource resource) {
		this.getEntityManager().merge(resource);
		this.getEntityManager().flush();
	}
}
