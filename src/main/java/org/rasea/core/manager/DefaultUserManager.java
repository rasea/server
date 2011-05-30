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

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.rasea.extensions.entity.User;
import org.rasea.extensions.manager.UserManager;
import org.rasea.extensions.manager.UserManagerException;

public class DefaultUserManager implements UserManager {

	private static final long serialVersionUID = -3281541942832176581L;

	private transient final EntityManager entityManager;

	public DefaultUserManager() {
		this.entityManager = (EntityManager) Component.getInstance("entityManager", true);
	}

	public boolean authenticate(final String username, final String password) {
		final StringBuffer ejbql = new StringBuffer(120);

		ejbql.append(" select u.password ");
		ejbql.append("   from User u ");
		ejbql.append("  where u.enabled = true ");
		ejbql.append("    and lower(u.name) = lower(:username) ");

		final Query query = this.entityManager.createQuery(ejbql.toString());

		query.setParameter("username", username);
		String passwd = null;

		try {
			passwd = (String) query.getSingleResult();
		} catch (final NoResultException cause) {
			return false;
		}

		return passwd.equals(password);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void changePassword(final String username, final String password) throws UserManagerException {

		final StringBuffer ejbql = new StringBuffer(100);

		ejbql.append(" update User u ");
		ejbql.append("    set u.password = :password ");
		ejbql.append("  where lower(u.name) = lower(:username) ");

		final Query query = this.entityManager.createQuery(ejbql.toString());

		query.setParameter("username", username);
		query.setParameter("password", password);

		query.executeUpdate();
	}

	public String crypt(final String pass) throws UnsupportedEncodingException {
		// final sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		// final String ATT_ENCODING = "Unicode";
		//
		// // Agree with MS's ATTRIBUTE_CONSTRAINT
		// final String pwd = "\"" + pass + "\"";
		// final byte _bytes[] = pwd.getBytes(ATT_ENCODING);
		//
		// // strip unicode marker
		// final byte bytes[] = new byte[_bytes.length - 2];
		// System.arraycopy(_bytes, 2, bytes, 0, _bytes.length - 2);
		// final String base64 = encoder.encode(bytes);
		//
		// return base64;

		return null;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final User user) throws UserManagerException {

		final Query query = this.entityManager.createQuery("delete User u where lower(u.name) = lower(:name)");

		query.setParameter("name", user.getName());

		query.executeUpdate();
		this.entityManager.flush();
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		final Query query = this.entityManager.createQuery("from User u order by u.name");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> findByFilter(final String filter) throws UserManagerException {
		final StringBuffer ejbql = new StringBuffer(200);

		// lower(u.name) = lower(:username)
		ejbql.append("   from User u ");
		ejbql.append("  where lower(u.name) like lower(:filter) ");
		ejbql.append("     or u.displayName like :filter ");
		ejbql.append("     or u.email like :filter ");
		ejbql.append("     or u.alternateEmail like :filter ");
		ejbql.append("  order by u.displayName ");

		final Query query = this.entityManager.createQuery(ejbql.toString());

		query.setParameter("filter", "%" + filter + "%");

		return query.getResultList();
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final User user) throws UserManagerException {

		this.entityManager.persist(user);
		this.entityManager.flush();
	}

	public User load(final String name) {
		return this.entityManager.find(User.class, name);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final User user) throws UserManagerException {

		this.entityManager.merge(user);
		this.entityManager.flush();
	}
}
