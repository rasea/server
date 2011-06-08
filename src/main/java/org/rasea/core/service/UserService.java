package org.rasea.core.service;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.rasea.core.domain.User;
import org.rasea.core.manager.UserManager;

import br.gov.frameworkdemoiselle.mail.Mail;
import br.gov.frameworkdemoiselle.transaction.Transactional;

public class UserService implements Serializable {

	private static final long serialVersionUID = 2097626878174583664L;

	@Inject
	private UserManager manager;

	@Inject
	private Instance<Mail> mail;

	@Transactional
	public void createAccount(User user) {
		user.setCreation(new Date());
		manager.createAccount(user);

		sendMail(user.getEmail(), "título teste", "corpo teste");
	}

	public void sendMail(String to, String subject, String body) {
		//		mail.get().to(to).from("raseatestmail@gmail.com").subject(subject).body().text(body).send();
	}
}
