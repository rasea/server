package org.rasea.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.rasea.extensions.entity.User;

@Converter
@BypassInterceptors
@Name("userConverter")
public class UserConverter implements javax.faces.convert.Converter {

	public Object getAsObject(final FacesContext facesContext,
			final UIComponent component, final String value) {
		return new User(value);
	}

	public String getAsString(final FacesContext facesContext,
			final UIComponent component, final Object value) {
		return ((User) value).getName();
	}
}
