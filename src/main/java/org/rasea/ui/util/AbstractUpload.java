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
