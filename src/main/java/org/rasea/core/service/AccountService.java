package org.rasea.core.service;

import java.io.Serializable;
import java.util.Calendar;

import javax.inject.Inject;

import org.rasea.core.domain.Account;
import org.rasea.core.manager.AccountManager;

public class AccountService implements Serializable {

	private static final long serialVersionUID = 2097626878174583664L;

	@Inject
	private AccountManager accountManager;

//	@Inject
//	private UserManager userManager;

	// @Inject
	// private Instance<Mail> mail;

	// @Transactional
	public void createAccount(Account account) {
		
		account.setCreationDate(Calendar.getInstance().getTime());
		accountManager.createAccount(account);

//		userManager.createUser(user);
		
		// sendMail(user.getEmail(), "título teste", "corpo teste");
	}

	// public void sendMail(String to, String subject, String body) {
	// mail.get().to(to).from("raseatestmail@gmail.com").subject(subject).body().text(body).send();
	// }
}
