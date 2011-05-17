package org.rasea.core.exception;


/**
 * Classe base das exceções geradas pelas classes de negocio e view helpers. Não
 * estenda essa classe, estenda BusinessException para exceções de negócio ou
 * ViewException para exceções das views.
 * 
 * @author abraao.isvi
 */
public abstract class AbstractApplicationException extends Exception {
	
	private static final long serialVersionUID = 3841786348242982957L;
	
	/**
	 * Parâmetros para a mensagem.
	 */
	private String[] params;
	
	/**
	 * Cria uma exceção com base em uma mensagem.
	 * 
	 * @param message
	 *            Mensagem associada.
	 */
	public AbstractApplicationException(final String message) {
		super(message);
	}
	
	/**
	 * Cria uma exceção com base em uma mensagem parametrizada.
	 * 
	 * @param message
	 *            Mensagem parametrizada.
	 * @param params
	 *            Parâmetros da mensagem;
	 */
	public AbstractApplicationException(final String message, final String... params) {
		super(message);
		this.params = params.clone();
	}
	
	/**
	 * Cria uma exceção com base em uma mensagem e uma causa.
	 * 
	 * @param message
	 *            Mensagem associada.
	 * @param cause
	 *            Causa do erro.
	 */
	public AbstractApplicationException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Obtém os parâmetros da mensagem.
	 * 
	 * @return Parâmetros da mensagem.
	 */
	public String[] getParams() {
		String[] params = null;
		if (this.params != null) {
			params = this.params.clone();
		}
		return params;
	}
	
	public abstract Severity getSeverity();
}
