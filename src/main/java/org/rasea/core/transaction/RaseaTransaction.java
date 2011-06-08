package org.rasea.core.transaction;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.hibernate.Session;

import br.gov.frameworkdemoiselle.transaction.Transaction;

@Alternative
@RequestScoped
public class RaseaTransaction implements Transaction {

	private static final long serialVersionUID = 4429485617126274180L;

	private boolean markedRollback = false;

	@Inject
	private Session session;

	@Override
	public boolean isActive() {
		return session.getTransaction().isActive();
	}

	@Override
	public boolean isMarkedRollback() {
		return markedRollback;
	}

	@Override
	public void begin() {
		session.getTransaction().begin();
	}

	@Override
	public void commit() {
		session.getTransaction().commit();
	}

	@Override
	public void rollback() {
		session.getTransaction().rollback();
	}

	@Override
	public void setRollbackOnly() {
		markedRollback = true;
	}
}
