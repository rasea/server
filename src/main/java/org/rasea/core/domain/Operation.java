package org.rasea.core.domain;

import java.io.Serializable;

import org.rasea.core.annotation.Domain;
import org.rasea.core.annotation.ItemName;
import org.rasea.core.annotation.Transient;

@Domain(name = "OPERATIONS")
public class Operation implements Serializable {

	private static final long serialVersionUID = -6776601526993229812L;

	@Transient
	private final String app;

	@ItemName
	private final String name;

	private String displayName;

	public Operation(String app, String name) {
		this.app = app;
		this.name = name;
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

	public String getApplication() {
		return app;
	}

}
