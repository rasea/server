package org.rasea.ws.v1.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.rasea.ws.v1.type.UserType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "users" })
public class UsersResponse {

	@XmlElement(name = "user", required = true)
	private List<UserType> users;

	public List<UserType> getUsers() {
		return this.users;
	}

	public void setUsers(final List<UserType> users) {
		this.users = users;
	}
}
