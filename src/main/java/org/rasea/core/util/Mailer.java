/*
 * Rasea Server
 * 
 * Copyright (c) 2008, Rasea <http://rasea.org>. All rights reserved.
 *
 * Rasea Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see <http://gnu.org/licenses>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
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
		// FIXME: substituir esse endereço hardcoded
		content.setData(String.format("http://rasea.elasticbeanstalk.com/activate/%s/%s",
				account.getUsername(), account.getActivationCode()));

		Body body = new Body();
		body.setText(content);

		Message message = new Message(subject, body);

		SendEmailRequest request = new SendEmailRequest(SENDER_EMAIL_ADDRESS, to, message);
		AmazonSimpleEmailServiceAsync service = Beans.getReference(AmazonSimpleEmailServiceAsync.class);
		service.sendEmail(request);
	}

	public void notifyPasswordResetRequest(Account account) {
		Destination to = new Destination().withToAddresses(account.getEmail());

		Content subject = new Content();
		subject.setCharset("UTF-8");
		subject.setData("Reinicialização da senha");

		Content content = new Content();
		content.setCharset("UTF-8");
		// FIXME: substituir esse endereço hardcoded
		content.setData(String.format("http://localhost:8080/rasea-server/reset/%s/%s",
				account.getUsername(), account.getActivationCode()));

		Body body = new Body();
		body.setText(content);

		Message message = new Message(subject, body);

		SendEmailRequest request = new SendEmailRequest(SENDER_EMAIL_ADDRESS, to, message);
		AmazonSimpleEmailServiceAsync service = Beans.getReference(AmazonSimpleEmailServiceAsync.class);
		service.sendEmail(request);
	}
}
