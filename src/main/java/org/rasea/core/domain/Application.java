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
import java.util.HashSet;
import java.util.Set;

import org.rasea.core.annotation.Domain;
import org.rasea.core.annotation.ItemName;

@Domain(name = "APPLICATIONS")
public class Application implements Serializable {

	private static final long serialVersionUID = -8689163688844199051L;

	@ItemName
	private final String name;

	private String displayName;
	
	private String url;
	
	private Set<String> owners;

	private Long accessCount;
	
	public Application(String name) {
		this.name = name;
		this.owners = new HashSet<String>();
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<String> getOwners() {
		return owners;
	}

	public void setOwners(Set<String> owners) {
		this.owners = owners;
	}

	public Long getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
	}

	@Override
	public String toString() {
		return "Application [name=" + name + ", displayName=" + displayName +
				", url=" + url + ", owners=" + owners + ", accessCount=" + accessCount + "]";
	}

}
