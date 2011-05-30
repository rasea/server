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
package org.rasea.ui.util;

import java.io.InputStream;

import org.rasea.core.util.Constants;

/**
 * @author cleverson.sacramento
 * @param <E>
 */
public abstract class AbstractUpload extends AbstractViewHelper {

	private static final long serialVersionUID = 8639991730424377201L;

	private InputStream data;

	public void checkImportPermission() {
		this.checkPermission(this.getImportOperation());
	}

	public InputStream getData() {
		return this.data;
	}

	public String getImportedMessage() {
		return Constants.MESSAGE_KEY_PREFIX + "_sent";
	}

	public String getImportOperation() {
		return Constants.DEFAULT_OPERATION_IMPORT;
	}

	abstract protected String handleSend() throws Exception;

	public String send() {
		String result = this.getBackView();

		try {
			this.checkImportPermission();
			result = this.handleSend();
			this.getStatusMessages().addFromResourceBundle(this.getImportedMessage());

		} catch (final Exception e) {
			result = null;
			this.performExceptionHandling(e);
		}

		return result;
	}

	public void setData(final InputStream data) {
		this.data = data;
	}
}
