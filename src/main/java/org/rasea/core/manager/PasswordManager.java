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
 * License along with this library; if not, see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
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

				final Address from = new InternetAddress(this.settings.getAdmin().getEmail(), "Administrador de Seguran\u00E7a");

				message.setFrom(from);
				message.setSubject("Altera\u00E7\u00E3o de dados");
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
