package org.rasea.ui.upload;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Data;
import org.rasea.core.service.DataService;
import org.rasea.core.util.Constants;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractUpload;

@Name("applicationUpload")
@Title("org.rasea.resource.application")
public class ApplicationUpload extends AbstractUpload {

	private static final long serialVersionUID = -9114028152102034033L;

	@In
	private DataService dataService;

	@In
	@SuppressWarnings("unused")
	@Out(required = false, scope = ScopeType.SESSION)
	private List<Application> ownerships; // NOPMD

	@Override
	public String getBackView() {
		return "/listApplication.xhtml";
	}

	@Override
	protected String handleSend() {
		this.checkPermission(Constants.DEFAULT_OPERATION_IMPORT);

		try {
			final JAXBContext context = JAXBContext.newInstance("org.rasea.core.entity");
			final Unmarshaller unmarshaller = context.createUnmarshaller();
			final Data data = (Data) unmarshaller.unmarshal(this.getData());

			this.dataService.update(data);
			this.ownerships = null;

		} catch (final Exception cause) {
			this.performExceptionHandling(cause);
		}

		return this.getBackView();
	}
}
