package org.rasea.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "R_OPERATION", uniqueConstraints = { @UniqueConstraint(columnNames = { "APPLICATION_ID", "NAME" }) })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "operation", propOrder = { "name", "displayName" })
@SuppressWarnings("serial")
public final class Operation implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	@XmlTransient
	private Long id;

	@NotNull
	@Length(max = 100)
	@Column(name = "NAME")
	@XmlID
	@XmlElement(required = true)
	private String name;

	@NotNull
	@Length(max = 255)
	@Column(name = "DESCRIPTION")
	@Index(name = "IDX_OPERATION_DESCRIPTION")
	@XmlElement(required = true)
	private String displayName;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "APPLICATION_ID")
	@ForeignKey(name = "FK_OPERATION_APPLICATION")
	@XmlTransient
	private Application application;

	public Operation() {
		super();
	}

	public Operation(final Application application) {
		this.application = application;
	}

	public Operation(final Application application, final String name) {
		this(application);
		this.name = name;
	}

	public Operation(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Operation other = (Operation) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Application getApplication() {
		return this.application;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	public void setApplication(final Application application) {
		this.application = application;
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
