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
package org.rasea.core.util;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.rasea.core.configuration.Settings;

@Name("org.jboss.seam.mail.mailSession")
@Install(precedence = Install.APPLICATION)
@Scope(ScopeType.APPLICATION)
public class SettingsMailSession extends org.jboss.seam.mail.MailSession {

	private static final long serialVersionUID = 1L;

	@In(create = true)
	private Settings settings;

	@Override
	public org.jboss.seam.mail.MailSession create() {
		this.setSessionJndiName(null);
		this.setHost(this.settings.getMail().getHost());
		this.setPort(this.settings.getMail().getPort());
		this.setUsername(this.settings.getMail().getUsername());
		this.setPassword(this.settings.getMail().getPassword());
		this.setTls(this.settings.getMail().isTls());

		return super.create();
	}
}
