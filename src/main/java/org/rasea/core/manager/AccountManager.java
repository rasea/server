package org.rasea.core.manager;

import java.util.ArrayList;
import java.util.List;

import org.rasea.core.domain.Account;

import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;

public class AccountManager extends AbstractSimpleDBManager<Account> {

	public void create(final Account account) {
		final List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>();
		attrs.add(new ReplaceableAttribute("email", account.getEmail(), true));
		if (account.getPhotoUrl() != null)
			attrs.add(new ReplaceableAttribute("photoUrl", account.getPhotoUrl(), true));
		attrs.add(new ReplaceableAttribute("password", account.getPassword(), true));
		attrs.add(new ReplaceableAttribute("activationCode", account.getActivationCode(), true));

		final String creationDateString = dateUtils.formatIso8601Date(account.getCreationDate());
		attrs.add(new ReplaceableAttribute("creationDate", creationDateString, true));

		getSimpleDB().putAttributes(new PutAttributesRequest(getDomainName(), account.getUsername(), attrs));
	}

	public void activate(final Account account) {
		final List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>();

		final String activationDateString = dateUtils.formatIso8601Date(account.getActivationDate());
		attrs.add(new ReplaceableAttribute("activationDate", activationDateString, true));

		getSimpleDB().putAttributes(new PutAttributesRequest(getDomainName(), account.getUsername(), attrs));
	}

	public Account findByUsername(final String username) {
		Account account = null;

		final GetAttributesResult result = getSimpleDB().getAttributes(new GetAttributesRequest(getDomainName(), username));

		if (result != null) {
			account = new Account();
			account.setUsername(username);
			account = fillAttributes(account, result.getAttributes());
		}

		return account;
	}

	public Account findByEmail(final String email) {
		Account account = null;

		final String expr = "select * from `" + getDomainName() + "` where email = '" + email + "'";

		final SelectRequest request = new SelectRequest(expr);
		final SelectResult result = getSimpleDB().select(request);

		if (result != null) {
			for (Item item : result.getItems()) {
				account = new Account();

				account.setUsername(item.getName());
				account = fillAttributes(account, item.getAttributes());

				// TODO: o campo "email" será único no domínio, ver como informar no SimpleDB
				break;
			}
		}

		return account;
	}

	public boolean containsUsername(final String username) {
		final GetAttributesRequest request = new GetAttributesRequest(getDomainName(), username).withAttributeNames("email");
		final GetAttributesResult result = getSimpleDB().getAttributes(request);
		
		return (result != null && !result.getAttributes().isEmpty());
	}

	public boolean containsEmail(final String email) {
		final String expr = "select ItemName() from `" + getDomainName() + "` where email = '" + email + "'";
		
		final SelectRequest request = new SelectRequest(expr);
		final SelectResult result = getSimpleDB().select(request);

		return (result != null && !result.getItems().isEmpty());
	}

	public void delete(final Account account) {
		getSimpleDB().deleteAttributes(new DeleteAttributesRequest(getDomainName(), account.getUsername()));
	}

	@Override
	protected void fillAttribute(final Account account, final String name, final String value) {
		if ("email".equals(name)) {
			account.setEmail(value);
		} else if ("photoUrl".equals(name)) {
			account.setPhotoUrl(value);
		} else if ("password".equals(name)) {
			account.setPassword(value);
		} else if ("creationDate".equals(name)) {
			account.setCreationDate(parseDateValue(value));
		} else if ("activationDate".equals(name)) {
			account.setActivationDate(parseDateValue(value));
		} else if ("activationCode".equals(name)) {
			account.setActivationCode(value);
		}
	}

}
