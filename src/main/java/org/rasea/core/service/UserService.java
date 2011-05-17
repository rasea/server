package org.rasea.core.service;

import java.util.List;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.core.SeamResourceBundle;
import org.jboss.seam.util.Strings;
import org.rasea.core.configuration.Settings;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.DuplicatedException;
import org.rasea.core.exception.EmailSendException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.exception.StoreException;
import org.rasea.core.manager.PasswordManager;
import org.rasea.core.util.ServiceValidator;
import org.rasea.extensions.entity.User;
import org.rasea.extensions.manager.UserManager;
import org.rasea.extensions.manager.UserManagerException;

@AutoCreate
@Name("userService")
public class UserService extends AbstractService {

	private static final long serialVersionUID = 8040065964200315945L;

	@In
	private User defaultUser;

	@In
	private MemberService memberService;

	@In
	private PasswordManager passwordManager;

	@In
	private ServiceValidator serviceValidator;

	@In
	private Settings settings;

	@In
	private UserManager userManager;

	public boolean authenticate(final String username, final String password) throws RequiredException, StoreException {
		this.serviceValidator.validateRequired("username", username);
		this.serviceValidator.validateRequired("password", password);

		boolean authenticated = false;

		if (this.settings.getAdmin().getUsername().equals(username)) {
			authenticated = this.settings.getAdmin().getPassword().equals(password);
		} else {
			try {
				authenticated = this.userManager.authenticate(username, password);

			} catch (final UserManagerException cause) {
				this.handleIdentityManagerException(cause);
			}
		}

		return authenticated;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void changePassword(final String username, final String password) throws RequiredException, StoreException, EmailSendException {

		this.validateReadOnly();

		this.serviceValidator.validateRequired("username", username);
		this.serviceValidator.validateRequired("password", password);

		try {
			this.userManager.changePassword(username, password);

		} catch (final UserManagerException cause) {
			this.handleIdentityManagerException(cause);
		}

		// TODO Colocar o envio de senha por e-mail para funcionar.
		// try {
		final User user = this.load(username);
		user.setPassword(password);
		// TODO passwordManager.sent: isso aqui não deveria ser a chamada de um
		// service?
		this.passwordManager.sent(user);

		// } catch (final EmailSendException e) {
		// TODO Logar em nível de ERRO a falha no envio.
		// e.printStackTrace();
		// }
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final User user) throws RequiredException, StoreException {

		this.validateReadOnly();

		this.serviceValidator.validateRequired("user", user);
		this.serviceValidator.validateRequired("user.username", user.getName());

		for (final Role role : this.memberService.find(user)) {
			this.memberService.delete(role, user);
		}

		try {
			this.userManager.delete(user);

		} catch (final UserManagerException cause) {
			this.handleIdentityManagerException(cause);
		}
	}

	public List<User> findAll() throws StoreException {
		List<User> result = null;

		try {
			result = this.userManager.findAll();
			result.remove(new User(this.settings.getAdmin().getUsername()));
			result.add(this.defaultUser);

		} catch (final UserManagerException cause) {
			this.handleIdentityManagerException(cause);
		}

		return result;
	}

	public List<User> findByFilter(final String filter) throws StoreException {
		List<User> result = null;

		if (filter == null) {
			result = this.findAll();

		} else {
			try {
				result = this.userManager.findByFilter(filter);

			} catch (final UserManagerException cause) {
				this.handleIdentityManagerException(cause);
			}
		}

		return result;
	}

	private String generatePassword() {
		final byte b[] = new byte[12];

		for (int i = 0; i < 2; i++) {
			b[i] = (byte) this.initRandom('a', 'z');
		}

		for (int i = 2; i < 4; i++) {
			b[i] = (byte) this.initRandom('0', '9');
		}

		for (int i = 4; i < 6; i++) {
			b[i] = (byte) this.initRandom('A', 'Z');
		}

		for (int i = 6; i < 8; i++) {
			b[i] = (byte) this.initRandom('0', '9');
		}

		for (int i = 8; i < 10; i++) {
			b[i] = (byte) this.initRandom('a', 'z');
		}

		for (int i = 10; i < 12; i++) {
			b[i] = (byte) this.initRandom('A', 'Z');
		}

		return new String(b);
	}

	private void handleIdentityManagerException(final UserManagerException cause) throws StoreException {
		throw new StoreException(cause);
	}

	private int initRandom(final int lo, final int hi) {
		final java.util.Random rn = new java.util.Random();
		final int n = hi - lo + 1;

		int i = rn.nextInt() % n;
		if (i < 0) {
			i = -i;
		}

		return lo + i;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final User user) throws RequiredException, DuplicatedException, StoreException, EmailSendException {

		this.validateReadOnly();

		this.serviceValidator.validateRequired("user", user);
		this.serviceValidator.validateRequired("user.username", user.getName());
		this.serviceValidator.validateDuplicateName(user);

		String password = user.getPassword();

		if (Strings.isEmpty(password)) {
			password = this.generatePassword();
		}

		try {
			this.userManager.insert(user);

		} catch (final UserManagerException cause) {
			this.handleIdentityManagerException(cause);
		}

		this.changePassword(user.getName(), password);

		// TODO Corrigir isso aqui
		// if (user.getMemberOf() != null) {
		// for (final Role role : user.getMemberOf()) {
		// this.memberService.insert(role, user);
		// }
		// }
	}

	public User load(final String username) throws RequiredException, StoreException {
		this.serviceValidator.validateRequired("username", username);

		User user = null;

		if (this.settings.getAdmin().getUsername().equals(username)) {
			user = this.defaultUser;
		} else {
			try {
				user = this.userManager.load(username);

			} catch (final UserManagerException cause) {
				this.handleIdentityManagerException(cause);
			}
		}

		return user;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void resetPassword(final String username, final String confirmationCode) throws RequiredException, StoreException, EmailSendException {

		this.validateReadOnly();

		final String newPassword = this.generatePassword();
		this.changePassword(username, newPassword);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final User user) throws RequiredException, StoreException {

		this.validateReadOnly();

		this.serviceValidator.validateRequired("user", user);
		this.serviceValidator.validateRequired("user.username", user.getName());

		try {
			this.userManager.update(user);

		} catch (final UserManagerException cause) {
			this.handleIdentityManagerException(cause);
		}
	}

	private void validateReadOnly() throws StoreException {
		if (this.settings == null) {
			this.settings = (Settings) Component.getInstance(Settings.class);
		}
		if (this.settings.getStore().isReadonly()) {
			throw new StoreException(SeamResourceBundle.getBundle().getString("org.rasea.core.ReadOnly"));
		}
	}
}
