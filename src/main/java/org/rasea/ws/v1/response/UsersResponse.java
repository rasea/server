package org.rasea.ws.v1.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.extensions.entity.User;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "users" })
public class UsersResponse {
	
	@XmlElement(name = "user", required = true)
	private List<User> users;
	
	public List<User> getUsers() {
		return this.users;
	}
	
	public void setUsers(final List<User> users) {
		this.users = users;
	}
}
