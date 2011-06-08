package org.rasea.core.manager;

import java.io.Serializable;

import javax.inject.Inject;

import org.rasea.core.domain.User;

import com.amazonaws.services.simpledb.AmazonSimpleDB;

public class UserManager implements Serializable {

	private static final long serialVersionUID = 2097626878174583664L;

	@Inject
	private AmazonSimpleDB db;

	/**
	 * Cria a conta do usuário persistindo o id, name, email, password e activation que já estão preenchidos no objeto
	 * passado por parâmetro.
	 * 
	 * @param user
	 */
	// @Transactional
	public void createAccount(User user) {
	}

	/**
	 * Retorna o usuário com base no seu e-mail.
	 * 
	 * @param email
	 * @return
	 */
	// @Transactional
	public User findByEmail(String email) {
		return null;
	}

	/**
	 * Retorna o usuário com base no seu id
	 * 
	 * @param login
	 * @return
	 */
	// @Transactional
	public User findByLogin(String login) {
		return null;
	}
}
