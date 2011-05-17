package org.rasea.ui.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.rasea.ui.util.AbstractDetail;

/**
 * Anotação usada no componente de apresentação Home.
 * 
 * @author cleverson.sacramento
 * @see AbstractDetail
 */
@Target(value = { ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Home {
	/**
	 * Nome da tela de edição.
	 * 
	 * @return Nome da tela de edição.
	 */
	String editView();
	
	/**
	 * Nome da tela de listagem.
	 * 
	 * @return Nome da tela de listagem.
	 */
	String listView();
}
