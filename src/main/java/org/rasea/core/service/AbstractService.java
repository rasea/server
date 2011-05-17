package org.rasea.core.service;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;

@Scope(ScopeType.STATELESS)
public abstract class AbstractService implements Serializable { // NOPMD

	private static final long serialVersionUID = 9141129073073049178L;

}
