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
 * License along with this library; if not, see <http://www.gnu.org/licenses/>
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
import org.rasea.core.entity.Operation;
import org.rasea.core.entity.Permission;
import org.rasea.core.entity.Resource;

@AutoCreate
@Name("permissionManager")
public class PermissionManager extends AbstractManager {

	private static final long serialVersionUID = -5644186107201912796L;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Permission permission) {
		final StringBuffer ejbql = new StringBuffer(100);

		ejbql.append(" delete Permission p ");
		ejbql.append("  where p.resource.id = :resourceId ");
		ejbql.append("   and p.operation.id = :operationId ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("resourceId", permission.getResource().getId());
		query.setParameter("operationId", permission.getOperation().getId());

		query.executeUpdate();
		this.getEntityManager().flush();
	}

	@SuppressWarnings("unchecked")
	public List<Permission> find(final Application application) {
		final StringBuffer ejbql = new StringBuffer(80);

		ejbql.append("  from Permission p ");
		ejbql.append(" where p.resource.application.id = :applicationId ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());
		query.setParameter("applicationId", application.getId());

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Permission> find(final Resource resource) {
		final Query query = this.getEntityManager().createQuery("from Permission p where p.resource.id = :resourceId");

		query.setParameter("resourceId", resource.getId());

		return query.getResultList();
	}

	public Permission find(final Resource resource, final Operation operation) {
		final StringBuffer ejbql = new StringBuffer(100);

		ejbql.append("  from Permission p ");
		ejbql.append(" where p.resource.id = :resourceId ");
		ejbql.append("  and p.operation.id = :operationId ");

		final Query query = this.getEntityManager().createQuery(ejbql.toString());

		query.setParameter("resourceId", resource.getId());
		query.setParameter("operationId", operation.getId());

		Permission result;

		try {
			result = (Permission) query.getSingleResult();
		} catch (final NoResultException cause) {
			result = null;
		}

		return result;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Permission permission) {
		this.getEntityManager().persist(permission);
		this.getEntityManager().flush();
	}
}
