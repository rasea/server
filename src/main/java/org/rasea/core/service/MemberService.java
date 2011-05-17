package org.rasea.core.service;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.rasea.core.entity.Application;
import org.rasea.core.entity.Member;
import org.rasea.core.entity.Role;
import org.rasea.core.exception.DuplicatedException;
import org.rasea.core.exception.RequiredException;
import org.rasea.core.exception.StoreException;
import org.rasea.core.manager.MemberManager;
import org.rasea.core.util.ServiceValidator;
import org.rasea.extensions.entity.User;

@AutoCreate
@Name("memberService")
public class MemberService extends AbstractService {

	private static final long serialVersionUID = -6374358679134276985L;

	@In
	private MemberManager memberManager;

	@In
	private ServiceValidator serviceValidator;

	@In
	private UserService userService;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void delete(final Role role, final User user) {
		if (this.load(role, user) != null) {
			this.memberManager.delete(role, user);
		}
	}

	public List<Role> find(final Application application, final User user) throws RequiredException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());
		this.serviceValidator.validateRequired("user", user);
		this.serviceValidator.validateRequired("user.username", user.getName());

		return this.memberManager.find(application, user);
	}

	public List<User> find(final Role role) throws RequiredException, StoreException {
		this.serviceValidator.validateRequired("role", role);
		this.serviceValidator.validateRequired("role.id", role.getId());

		final List<User> members = this.memberManager.find(role);
		User member;

		for (final User user : members) {
			member = this.userService.load(user.getName());

			if (member == null) {
				this.delete(role, user);
			}
		}

		return members;
	}

	public List<Role> find(final User user) throws RequiredException {
		this.serviceValidator.validateRequired("user", user);
		this.serviceValidator.validateRequired("user.username", user.getName());

		return this.memberManager.find(user);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void insert(final Role role, final User user) throws RequiredException, DuplicatedException {
		this.serviceValidator.validateRequired("role", role);
		this.serviceValidator.validateRequired("role.id", role.getId());
		this.serviceValidator.validateRequired("user", user);
		this.serviceValidator.validateRequired("user.username", user.getName());

		if (this.memberManager.load(role, user) != null) {
			throw new DuplicatedException("role,user", user.getName() + "," + role.getName());
		}

		if (this.load(role, user) == null) {
			this.memberManager.insert(role, user);
		}
	}

	public Member load(final Role role, final User user) {
		return this.memberManager.load(role, user);
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final Application application, final Role role) throws RequiredException, DuplicatedException, StoreException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());
		this.serviceValidator.validateRequired("role", role);
		this.serviceValidator.validateRequired("role.id", role.getId());

		final List<User> selected = new ArrayList<User>();

		if (role.getMembers() != null) {
			for (final User user : role.getMembers()) {
				selected.add(user);
			}
		}

		final List<User> current = this.find(role);

		final List<User> toDelete = new ArrayList<User>(current);
		toDelete.removeAll(selected);

		for (final User user : toDelete) {
			this.delete(role, user);
		}

		final List<User> toInsert = new ArrayList<User>(selected);
		toInsert.removeAll(current);

		for (final User user : toInsert) {
			this.insert(role, user);
		}
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void update(final Application application, final User user, final List<Role> memberOf) throws RequiredException, DuplicatedException {
		this.serviceValidator.validateRequired("application", application);
		this.serviceValidator.validateRequired("application.id", application.getId());
		this.serviceValidator.validateRequired("user", user);
		this.serviceValidator.validateRequired("user.username", user.getName());

		final List<Role> selected = new ArrayList<Role>();

		if (memberOf != null) {
			for (final Role role : memberOf) {
				selected.add(role);
			}
		}

		final List<Role> current = this.find(application, user);

		final List<Role> toDelete = new ArrayList<Role>(current);
		toDelete.removeAll(selected);

		for (final Role role : toDelete) {
			this.delete(role, user);
		}

		final List<Role> toInsert = new ArrayList<Role>(selected);
		toInsert.removeAll(current);

		for (final Role role : toInsert) {
			this.insert(role, user);
		}
	}
}
