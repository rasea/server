package org.rasea.core.exception;


public class NotSupportedException extends AbstractBusinessException {
	
	private static final long serialVersionUID = 1L;
	
	private final String invalidVersion;
	
	public NotSupportedException(final String invalidVersion) {
		super("Versão do arquivo não suportada: " + invalidVersion);
		this.invalidVersion = invalidVersion;
	}
	
	public String getInvalidVersion() {
		return this.invalidVersion;
	}
	
	@Override
	public Severity getSeverity() {
		return Severity.INFO;
	}
}
