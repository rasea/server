package org.rasea.ui.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;

public abstract class AbstractController implements Serializable {

	private static final long serialVersionUID = -7673211922078469250L;

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
}
