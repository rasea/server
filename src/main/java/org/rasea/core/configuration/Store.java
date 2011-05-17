package org.rasea.core.configuration;

import java.io.Serializable;

import org.rasea.core.annotation.Property;

public class Store implements Serializable {

	private static final long serialVersionUID = -3346754658680241871L;

	@Property(key = "store.type", defaultValue = "default")
	private StoreType storeType;

	@Property(key = "store.readonly", defaultValue = "false")
	private boolean readonly;

	public StoreType getStoreType() {
		return this.storeType;
	}

	public boolean isReadonly() {
		return this.readonly;
	}

	public void setReadonly(final boolean readonly) {
		this.readonly = readonly;
	}

	public void setStoreType(final StoreType storeType) {
		this.storeType = storeType;
	}
}
