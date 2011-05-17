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
