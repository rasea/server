package org.rasea.core.util;

import java.io.Serializable;

import br.gov.frameworkdemoiselle.util.Beans;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
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

	public void send(String to) {
		Destination destination = new Destination().withToAddresses(to);
		Message message = null;

		SendEmailRequest request = new SendEmailRequest(SENDER_EMAIL_ADDRESS, destination, message);
		AmazonSimpleEmailServiceAsync service = Beans.getReference(AmazonSimpleEmailServiceAsync.class);
		service.sendEmailAsync(request);
	}
}
