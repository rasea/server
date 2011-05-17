package org.rasea.core.manager;

import java.io.Serializable;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Scope;

@Scope(ScopeType.STATELESS)
@TransactionManagement(TransactionManagementType.BEAN)
public abstract class AbstractManager implements Serializable {
	
	private static final long serialVersionUID = 8903634706721951238L;
	
	@In
	private EntityManager entityManager;
	
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}
}
