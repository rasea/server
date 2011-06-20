/*
 * Rasea Server
 * 
 * Copyright (c) 2008, Rasea <http://rasea.org>. All rights reserved.
 *
 * Rasea Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see <http://gnu.org/licenses>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.rasea.core.manager;

import static java.lang.String.format;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.rasea.core.annotation.Domain;

import br.gov.frameworkdemoiselle.util.Beans;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.util.DateUtils;

public abstract class AbstractSimpleDBManager<T> implements Serializable {

	private static final long serialVersionUID = -5003752373120758712L;

	private AmazonSimpleDB simpleDB;

	private Class<T> clz;

	private final String domainName;

	protected final DateUtils dateUtils = new DateUtils();

	@SuppressWarnings("unchecked")
	public AbstractSimpleDBManager() {
		clz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

		Domain domain = clz.getAnnotation(Domain.class);
		if (domain != null) {
			domainName = domain.name();
		} else {
			String message = format("A classe %s deve ser anotada com @%s", getClass().getCanonicalName(),
					Domain.class.getSimpleName());
			throw new RuntimeException(message);
		}
	}

	@PostConstruct
	@SuppressWarnings("unused")
	private void init() {
	}

	protected AmazonSimpleDB getSimpleDB() {
		if (simpleDB == null) {
			simpleDB = Beans.getReference(AmazonSimpleDB.class);

			CreateDomainRequest request = new CreateDomainRequest(getDomainName());
			simpleDB.createDomain(request);
		}
		return simpleDB;
	}

	protected String getDomainName() {
		return domainName;
	}

	protected String getDomainName(final String sufix) {
		return domainName + "_" + sufix;
	}

	protected Date parseDateValue(final String value) {
		Date date = null;
		if (value != null && !"".equals(value)) {
			try {
				date = dateUtils.parseIso8601Date(value);
			} catch (ParseException e) {
			}
		}
		return date;
	}

	protected T fillAttributes(T object, List<Attribute> attributes) {

		if (attributes == null || attributes.isEmpty())
			return null;

		for (Attribute attr : attributes)
			fillAttribute(object, attr.getName(), attr.getValue());

		return object;
	}

	protected abstract void fillAttribute(T object, final String name, final String value);

}
