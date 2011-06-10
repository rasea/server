package org.rasea.core.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.rasea.core.domain.Account;

import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

public class AccountManager extends AbstractSimpleDBManager<Account> {

	/**
	 * Cria a conta do usuário persistindo o id, name, email, password e
	 * activation que já estão preenchidos no objeto passado por parâmetro.
	 * 
	 * @param account
	 */
	public void createAccount(final Account account) {
		final List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>();
		attrs.add(new ReplaceableAttribute("password", account.getPassword(), true));
		attrs.add(new ReplaceableAttribute("act_code", account.getActivationCode(), true));

		final String creationDateString = dateUtils.formatIso8601Date(account.getCreationDate());
		attrs.add(new ReplaceableAttribute("cre_date", creationDateString, true));

		getSimpleDB().putAttributes(new PutAttributesRequest(getDomainName(), account.getLogin(), attrs));
	}

	/**
	 * Retorna a conta do usuário com base no seu login.
	 * 
	 * @param login
	 * @return account
	 */
	public Account findByLogin(final String login) {
		Account account = null;

		GetAttributesResult result = getSimpleDB().getAttributes(
				new GetAttributesRequest(getDomainName(), login));
		
		if (result != null) {
			account = new Account();
			account.setLogin(login);
			account = fillAttributes(account, result.getAttributes());
		}

		return account;
	}

	public void deleteAccount(final Account account) {
		getSimpleDB().deleteAttributes(new DeleteAttributesRequest(getDomainName(), account.getLogin()));
	}

	public void activateAccount(final Account account) {
		final List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>();

		final Date currentTimestamp = Calendar.getInstance().getTime();
		final String stringTimestamp = dateUtils.formatIso8601Date(currentTimestamp);
		attrs.add(new ReplaceableAttribute("act_date", stringTimestamp, true));

		getSimpleDB().putAttributes(new PutAttributesRequest(getDomainName(), account.getLogin(), attrs));
	}

	@Override
	protected void fillAttribute(final Account account, final String name, final String value) {
		if ("password".equals(name)) {
			account.setPassword(value);
		} else if ("cre_date".equals(name)) {
			account.setCreationDate(parseDateValue(value));
		} else if ("act_date".equals(name)) {
			account.setActivationDate(parseDateValue(value));
		} else if ("act_code".equals(name)) {
			account.setActivationCode(value);
		}
	}
	
}
