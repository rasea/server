package org.rasea.core.manager;

import org.rasea.core.domain.User;

import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;

public class UserManager extends AbstractSimpleDBManager<User> {

	/**
	 * Retorna o usuário com base no seu login.
	 * 
	 * @param login
	 * @return user
	 */
	public User findByLogin(final String login) {
		User user = null;

		GetAttributesResult result = getSimpleDB().getAttributes(
				new GetAttributesRequest(getDomainName(), login));
		
		if (result != null) {
			user = new User();
			user.setLogin(login);
			user = fillAttributes(user, result.getAttributes());
		}

		return user;
	}

	/**
	 * Retorna o usuário com base no seu e-mail.
	 * 
	 * @param email
	 * @return
	 */
	public User findByEmail(final String email) {
		User user = null;

		// FIXME: essa expressão SQL-like não tá funfando!
		final String expr = "select * from `" + getDomainName() + "` where email = '" + email + "'";

		final SelectRequest request = new SelectRequest(expr);
		final SelectResult result = getSimpleDB().select(request);

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
	protected void fillAttribute(final User user, final String name, final String value) {
		if ("name".equals(name)) {
			user.setName(value);
		} else if ("email".equals(name)) {
			user.setEmail(value);
		}
	}

}
