package org.rasea.core.exception;

public class StoreException extends AbstractBusinessException {
	
	private static final long serialVersionUID = 366566845050479193L;
	
	public StoreException(final String message) {
		super(message);
	}
	
	public StoreException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public StoreException(final Throwable cause) {
		// TODO Lançar mensagem do resource bundle e capturar na tela usando o
		// #{messages[...]}. E quando essa exceção não for para a tela, e sim
		// para um serviço, por exemplo?
		this("Falha na manipulação dos dados de usuário: " + cause.getMessage(), cause);
	}
	
	@Override
	public Severity getSeverity() {
		return Severity.FATAL;
	}
}
