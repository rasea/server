package org.rasea.core.manager;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.async.Asynchronous;
import org.rasea.core.configuration.Settings;
import org.rasea.core.exception.EmailSendException;
import org.rasea.core.util.SettingsMailSession;
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("passwordManager")
public class PasswordManager extends AbstractManager {

	private static final long serialVersionUID = 362103060695904961L;

	@In
	private Settings settings;

	private String getContent(final User user) {
		final StringBuffer result = new StringBuffer();

		result.append("login: ");
		result.append(user.getName());
		result.append("\n");
		result.append("senha: ");
		result.append(user.getPassword());

		return result.toString();
	}

	@Asynchronous
	public void sent(final User user) throws EmailSendException {
		try {

			// TODO: Retirei esta verificação pois estava gerando erro na
			// aplicação.
			// this.validateMailSettings();
			final List<String> mails = new ArrayList<String>();

			if (user.getEmail() != null) {
				mails.add(user.getEmail());
			}

			if (user.getAlternateEmail() != null) {
				mails.add(user.getEmail());
			}

			if (!mails.isEmpty()) {
				final Session mailSession = (Session) Component.getInstance(SettingsMailSession.class, true);
				final MimeMessage message = new MimeMessage(mailSession);
				message.setFrom(new InternetAddress(this.settings.getAdmin().getEmail()));

				for (final String element : mails) {
					final Address toAddress = new InternetAddress(element);
					message.addRecipient(Message.RecipientType.TO, toAddress);
				}

				final Address from = new InternetAddress(this.settings.getAdmin().getEmail(), "Administrador de Segurança");

				message.setFrom(from);
				message.setSubject("Alteração de dados");
				message.setContent(this.getContent(user), "text/plain");
				Transport.send(message);

			} else {
				// TODO logar que o email não foi enviado pois o usuario nao
				// possui emails cadastrados em nivel de WARN.
				throw new EmailSendException("XXXXX");
			}

		} catch (final Exception cause) {
			throw new EmailSendException("Unable to send mail to user " + user.getName(), cause);
		}
	}
}
