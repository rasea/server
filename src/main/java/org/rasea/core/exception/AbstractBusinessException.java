package org.rasea.core.exception;

import org.jboss.seam.annotations.ApplicationException;

/**
 * <p>
 * As aplicações devem utilizar esta exceção apenas na camada de negócio, caso
 * haja alguma validação de negócio que impeça a conclusão bem sucedida da
 * transação. Sempre que uma exceção deste tipo for lançada, a abstração tratará
 * e exibirá uma mensagem associada a chave passada como parâmetro. A chave
 * informada deverá existir no arquivo <i>messages.poperties</i>. O tratamento
 * desta exceção causará o rollback na transação.
 * </p>
 * <p>
 * Por exemplo, caso seja lançada a seguinte exceção:
 * </p>
 * 
 * <pre>
 * throw new MyBusinessException();
 * </pre>
 * <p>
 * Na tela aparecerá a seguite mensagem associada à chave no
 * <i>messages.poperties</i>:
 * </p>
 * 
 * <pre>
 * meu.teste=Minha mensagem de negócio
 * </pre>
 * <p>
 * OBS: <b>A conversão da exceção em mensagem de tela é feita nas classe
 * Home</b>. A mensagem sempre será global, e nunca associada a um determinado
 * componente de tela, tal como uma caixa de texto, por exemplo. Caso você
 * deseje fazer isso aconselha-se criar na sua aplicação exceções específicas
 * (herdando de ViewException) para cada validação que você quer tratar
 * separadamente. Por exemplo:
 * </p>
 * 
 * <pre>
 * try{
 *     ...
 * } catch (final DataNascimentoException cause) {
 *     new MeuViewException(cause.getMessage()).addField(&quot;meuCampo&quot;);
 * }
 * </pre>
 * 
 * @author cleverson.sacramento
 */
@ApplicationException(rollback = true, end = false)
public abstract class AbstractBusinessException extends AbstractApplicationException {
	
	private static final long serialVersionUID = 2736907546570442106L;
	
	public AbstractBusinessException(final String message) {
		super(message);
	}
	
	public AbstractBusinessException(final String message, final String... params) {
		super(message, params);
	}
	
	public AbstractBusinessException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
}
