package org.rasea.core.manager;

import org.rasea.core.domain.User;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;

public class UserManager extends AbstractSimpleDBManager<User> {

	/**
	 * Retorna o usuário com base no seu e-mail.
	 * 
	 * @param email
	 * @return
	 */
	public User findByEmail(String email) {
		User user = null;

		// FIXME: essa expressão SQL-like não tá funfando!
		final String expr = "select * from `" + getDomainName() + "` where email = '" + email + "'";

		SelectRequest request = new SelectRequest(expr);
		SelectResult result = getSimpleDB().select(request);

		if (result != null) {
			for (Item item : result.getItems()) {
				user = new User();

				user.setLogin(item.getName());
				user = fillAttributes(user, item.getAttributes());

				// TODO: o campo "email" será único no domínio, ver como informar no SimpleDB
				break;
			}
		}

		return user;
	}

	@Override
	protected void fillAttribute(User user, final String name, final String value) {
		if ("name".equals(name)) {
			user.setName(value);
		} else if ("email".equals(name)) {
			user.setEmail(value);
		}
	}

}
