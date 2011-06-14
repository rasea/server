package org.rasea.core.util;

import br.gov.frameworkdemoiselle.util.Beans;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class Mailer {

	private final static String SENDER_EMAIL_ADDRESS = "no-reply@rasea.org";

	private Mailer() {
	}

	public static void send(String to) {
		Destination destination = new Destination().withToAddresses(to);
		Message message = null;

		SendEmailRequest request = new SendEmailRequest(SENDER_EMAIL_ADDRESS, destination, message);
		AmazonSimpleEmailServiceAsync service = Beans.getReference(AmazonSimpleEmailServiceAsync.class);
		service.sendEmailAsync(request);
	}
}
