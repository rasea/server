package org.rasea.core.util;

import java.io.Serializable;

import org.rasea.core.domain.Account;

import br.gov.frameworkdemoiselle.util.Beans;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class Mailer implements Serializable {

	private static final long serialVersionUID = -832633498104451866L;

	private final static String SENDER_EMAIL_ADDRESS = "no-reply@rasea.org";

	private static Mailer instance;

	private Mailer() {
	}

	public static synchronized Mailer getInstance() {
		if (instance == null) {
			instance = new Mailer();
		}

		return instance;
	}

	public void notifyAccountActivation(Account account) {
		Destination to = new Destination().withToAddresses(account.getEmail());

		Content subject = new Content();
		subject.setCharset("UTF-8");
		subject.setData("Ativação da conta");

		Content content = new Content();
		content.setCharset("UTF-8");
		content.setData(String.format("username: %s\nactivationcode: %s", account.getUsername(),
				account.getActivationCode()));

		Body body = new Body();
		body.setText(content);

		Message message = new Message(subject, body);

		SendEmailRequest request = new SendEmailRequest(SENDER_EMAIL_ADDRESS, to, message);
		AmazonSimpleEmailServiceAsync service = Beans.getReference(AmazonSimpleEmailServiceAsync.class);
		service.sendEmailAsync(request);
	}
}
