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
package org.rasea.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Embeddable
public class MemberPk implements Serializable { // NOPMD by cleverson on
	// 05/12/09 15:22

	private static final long serialVersionUID = -18840240135129128L;

	@ManyToOne
	@JoinColumn(name = "ROLE_ID", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ForeignKey(name = "FK_MEMBER_ROLE")
	@Index(name = "IDX_MEMBER_ROLE")
	private Role role;

	@Index(name = "IDX_MEMBER_USERNAME")
	@Column(name = "USERNAME", nullable = false)
	private String username;

	@Override
	public boolean equals(final Object obj) { // NOPMD by cleverson on 05/12/09
		// 15:22
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MemberPk)) {
			return false;
		}
		final MemberPk other = (MemberPk) obj;
		if (this.role == null) {
			if (other.role != null) {
				return false;
			}
		} else if (!this.role.equals(other.role)) {
			return false;
		}
		if (this.username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!this.username.equals(other.username)) {
			return false;
		}
		return true;
	}

	public Role getRole() {
		return this.role;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1; // NOPMD by cleverson on 05/12/09 15:22
		result = prime * result + (this.role == null ? 0 : this.role.hashCode());
		result = prime * result + (this.username == null ? 0 : this.username.hashCode());
		return result;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
}
