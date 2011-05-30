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
import org.rasea.core.entity.Role;

@AutoCreate
@Name("roleManager")
public class RoleManager extends AbstractManager {

	private static final long serialVersionUID = -934887992847520051L;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Role role) {
		final Query query = this.getEntityManager().createQuery("delete Role r where r.id = :roleId");

		query.setParameter("roleId", role.getId());

		query.executeUpdate();
		this.getEntityManager().flush();
	}

	@SuppressWarnings("unchecked")
	public List<Role> find(final Application application) {
		final Query query = this.getEntityManager().createQuery("from Role r where r.application.id = :applicationId order by r.displayName");

		query.setParameter("applicationId", application.getId());

		return query.getResultList();
	}

	public Role find(final Application application, final String name) {
		final StringBuffer ejbql = new StringBuffer(100);

		ejbql.append("  from Role r ");
		ejbql.append(" where r.application.id = :applicationId ");
		ejbql.append("   and r.name = :name ");
		ejbql.append(" order by r.name ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("applicationId", application.getId());
		query.setParameter("name", name);

		Role result;

		try {
			result = (Role) query.getSingleResult();
		} catch (final NoResultException cause) {
			result = null;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Role> find(final Resource resource) {
		final StringBuffer ejbql = new StringBuffer(130);

		ejbql.append(" select distinct a.role ");
		ejbql.append("   from Authorization a ");
		ejbql.append("  where a.permission.resource.id = :resourceId ");
		ejbql.append("  order by a.role.displayName ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());
		query.setParameter("resourceId", resource.getId());

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Role> findByFilter(final Application application, final String filter) {
		final StringBuffer ejbql = new StringBuffer(160);

		ejbql.append("  from Role r ");
		ejbql.append(" where r.application.id = :applicationId ");
		ejbql.append("   and (lower(r.name) like :filter ");
		ejbql.append("    or  lower(r.displayName) like :filter) ");
		ejbql.append("  order by r.displayName ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("filter", "%" + filter + "%");
		query.setParameter("applicationId", application.getId());

		return query.getResultList();
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Role role) {
		this.getEntityManager().persist(role);
		this.getEntityManager().flush();
	}

	public Role load(final Long id) {
		return this.getEntityManager().find(Role.class, id);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final Role role) {
		this.getEntityManager().merge(role);
		this.getEntityManager().flush();
	}
}
