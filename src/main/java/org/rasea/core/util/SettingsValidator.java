package org.rasea.core.util;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.rasea.core.configuration.Settings;

@AutoCreate
@Name("settingsValidator")
@Scope(ScopeType.APPLICATION)
public class SettingsValidator implements Serializable {
	
	private static final long serialVersionUID = 4857734302021002487L;
	
	@In
	private Settings settings;
	
	public boolean isAdminMailValid() {
		boolean ret = true;
		
		if (this.settings.getAdmin().getEmail() == null
				|| this.settings.getAdmin().getEmail().trim().length() == 0) {
			ret = false;
		}
		
		return ret;
	}
	
	public boolean isMailHostValid() {
		boolean ret = true;
		if (this.settings.getMail().getHost() == null
				|| this.settings.getMail().getHost().trim().length() == 0) {
			ret = false;
		}
		
		return ret;
	}
	
	public boolean isMailPasswordValid() {
		boolean ret = true;
		if (this.settings.getMail().getUsername() != null
				&& this.settings.getMail().getUsername().trim().length() > 0
				&& (this.settings.getMail().getPassword() == null || this.settings.getMail()
						.getPassword().trim().length() == 0)) {
			ret = false;
		}
		return ret;
	}
	
	public boolean isMailUserNameValid() {
		boolean ret = true;
		
		if (this.settings.getMail().getPassword() != null
				&& this.settings.getMail().getPassword().trim().length() > 0
				&& (this.settings.getMail().getUsername() == null || this.settings.getMail()
						.getUsername().trim().length() == 0)) {
			ret = false;
		}
		
		return ret;
	}
}
