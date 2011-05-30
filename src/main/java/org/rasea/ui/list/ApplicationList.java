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
package org.rasea.ui.list;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.document.ByteArrayDocumentData;
import org.jboss.seam.document.DocumentData;
import org.jboss.seam.document.DocumentStore;
import org.jboss.seam.document.DocumentData.DocumentType;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Data;
import org.rasea.core.exception.AbstractApplicationException;
import org.rasea.core.service.ApplicationService;
import org.rasea.core.service.DataService;
import org.rasea.core.util.Constants;
import org.rasea.ui.annotation.Title;
import org.rasea.ui.util.AbstractList;

@Name("applicationList")
@Title("org.rasea.resource.application")
public class ApplicationList extends AbstractList<Application> {

	private static final long serialVersionUID = 8995959908572472084L;

	@In
	private ApplicationService applicationService;

	@In
	private DataService dataService;

	public String exportApplication() {
		String result = null;

		try {
			final Data data = this.dataService.find(this.getDataModelSelection());
			result = this.exportXML(this.getXMLFilename(data), data);

		} catch (final Exception cause) {
			this.performExceptionHandling(cause);
		}

		return result;
	}

	public String exportApplications() {
		String result = null;

		try {
			final Data data = this.dataService.find(this.handleResultList());
			result = this.exportXML(this.getXMLFilename(data), data);

		} catch (final Exception cause) {
			this.performExceptionHandling(cause);
		}

		return result;
	}

	public String exportXML(final String filename, final Data data) {
		this.checkPermission(Constants.DEFAULT_OPERATION_EXPORT);
		String url = null;

		try {
			final DocumentStore documentStore = DocumentStore.instance();

			final JAXBContext context = JAXBContext.newInstance("org.rasea.core.entity");
			final Marshaller marshaller = context.createMarshaller();

			final ByteArrayOutputStream output = new ByteArrayOutputStream();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.marshal(data, output);

			final String docId = documentStore.newId();
			final DocumentData document = new ByteArrayDocumentData(filename, this.getDocumentType(), output.toByteArray());

			documentStore.saveData(docId, document);

			url = documentStore.preferredUrlForContent(document.getBaseName(), document.getDocumentType().getExtension(), docId);

		} catch (final Exception cause) {
			this.performExceptionHandling(cause);
		}

		return url;
	}

	public DocumentType getDocumentType() {
		return new DocumentType("xml", "application/x-download");
	}

	public String getExportOperation() {
		return Constants.DEFAULT_OPERATION_EXPORT;
	}

	public String getImportOperation() {
		return Constants.DEFAULT_OPERATION_IMPORT;
	}

	private String getXMLFilename(final Data data) {
		String filename = null;

		if (data != null && data.getApplications() != null && data.getApplications().size() == 1) {
			filename = data.getApplications().get(0).getName();

		} else if (data != null && data.getApplications() != null && data.getApplications().size() > 1) {
			filename = "applications";

		} else {
			filename = "empty";
		}

		return filename + "-data";
	}

	@Override
	protected List<Application> handleResultList() throws AbstractApplicationException {
		return this.applicationService.findByFilter(this.getSearchString());
	}

	@Override
	protected void handleUpdate() throws AbstractApplicationException {
		this.applicationService.update(this.getDataModelSelection());
	}
}
