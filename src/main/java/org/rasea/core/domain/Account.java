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
package org.rasea.core.domain;

import java.io.Serializable;
import java.util.Date;

import org.rasea.core.annotation.Domain;
import org.rasea.core.annotation.ItemName;
import org.rasea.core.annotation.Unique;

@Domain(name = "ACCOUNTS")
public class Account implements Serializable {

	private static final long serialVersionUID = -5630651623043896485L;

	@ItemName
	private final String username;

	@Unique
	private String email;

	private String fullname;
	
	private String photoUrl;

	private String password;

	private Date creationDate;

	private Date activationDate;

	private String activationCode;

	private String passwordResetCode;

	private Date lastLogin;
	
	public Account(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPasswordResetCode() {
		return passwordResetCode;
	}

	public void setPasswordResetCode(String passwordResetCode) {
		this.passwordResetCode = passwordResetCode;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Override
	public String toString() {
		return "Account [username=" + username + ", email=" + email + ", fullname=" + fullname + ", photoUrl="
				+ photoUrl + ", password=" + password + ", creationDate=" + creationDate + ", activationDate="
				+ activationDate + ", activationCode=" + activationCode + ", passwordResetCode=" + passwordResetCode
				+ ", lastLogin=" + lastLogin + "]";
	}
	
}
