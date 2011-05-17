package org.rasea.ui.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.rasea.ui.util.AbstractDetail;
import org.rasea.ui.util.AbstractList;

/**
 * Anotação usada em componentes que auxiliam views.
 * 
 * @author cleverson.sacramento
 * @see AbstractDetail
 * @see AbstractList
 */
@Target(value = { ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Title {
	
	/**
	 * Descrição da tela.
	 * 
	 * @return Nome da tela.
	 */
	String value();
}
