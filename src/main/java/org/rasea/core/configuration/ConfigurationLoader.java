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
 * License along with this library; if not, see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.rasea.core.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.rasea.core.annotation.Property;
import org.rasea.core.exception.ConfigurationException;

/**
 * @author cleverson.sacramento
 */
public class ConfigurationLoader {

	private static final Logger logger = Logger.getLogger(ConfigurationLoader.class);

	private final PropertiesConfiguration configuration;

	public ConfigurationLoader() throws ConfigurationException {
		this(null);
	}

	public ConfigurationLoader(final String file) {
		PropertiesConfiguration configuration = null;

		if (file == null) {
			configuration = new PropertiesConfiguration();
			logger.info("Loading settings without a configuration file");

		} else {
			try {
				configuration = new PropertiesConfiguration(file);
				logger.info("Loading configuration file from " + configuration.getBasePath());

			} catch (final org.apache.commons.configuration.ConfigurationException cause) {
				configuration = new PropertiesConfiguration();

				logger.warn(cause.getMessage());
				logger.info("Loading settings without a configuration file");
			}
		}

		this.configuration = configuration;
	}

	private Object getBooleanProperty(final Object fieldValue) {
		return Boolean.parseBoolean((String) fieldValue);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object getEnumProperty(final Object pojo, final Field field, final Object fieldValue) throws ConfigurationException {
		try {
			final Class enumClass = Class.forName(field.getType().getCanonicalName());

			Object value = Enum.valueOf(enumClass, fieldValue.toString().toUpperCase());
			if (value == null) {
				value = Enum.valueOf(enumClass, fieldValue.toString().toLowerCase());
			}

			return value;

		} catch (final Exception cause) {
			final String message = "Failed to load enum property: " + pojo.getClass().getName() + "." + field.getName();
			throw new ConfigurationException(message, cause);
		}
	}

	private Object getProperty(final Object pojo, final Field field) throws IllegalArgumentException, IllegalAccessException {
		Object fieldValue = null;

		try {
			fieldValue = BeanUtils.getProperty(pojo, field.getName());

		} catch (final Exception cause) {
			if (!"serialVersionUID".equals(field.getName())) {
				final String message = "Failed to load property: " + pojo.getClass().getName() + "." + field.getName();
				this.log(message, field.getType().isLocalClass(), cause);
			}
		}

		return fieldValue;
	}

	@SuppressWarnings("unchecked")
	private Object getValue(final Property annotation) {
		Object fieldValue = null;
		String key = null;

		if (!"".equals(annotation.key())) {
			key = annotation.key();
		}

		fieldValue = this.configuration.getProperty(key);

		if (fieldValue instanceof ArrayList<?>) {
			final StringBuffer sb = new StringBuffer();
			final ArrayList<String> list = (ArrayList<String>) fieldValue;

			int i = 0;
			for (final String value : list) {
				sb.append(value);

				if (i < list.size() - 1) {
					sb.append(",");
				}

				i++;
			}

			fieldValue = sb.toString();
		}

		if (fieldValue == null && !"".equals(annotation.defaultValue())) {
			fieldValue = annotation.defaultValue();
		}

		return fieldValue;
	}

	private Object initFieldValue(final Field field) {
		Object fieldValue = null;

		try {
			fieldValue = field.getType().newInstance();

		} catch (final InstantiationException cause) {
			final String message = "Failed to instante property: " + field.getType().getName() + " has no default constructor";
			this.log(message, field.getType().isLocalClass());

		} catch (final IllegalAccessException cause) {
			logger.warn(cause.getMessage());
		}

		return fieldValue;
	}

	public void load(final Object pojo) throws ConfigurationException {
		try {
			this.populate(pojo);

		} catch (final Exception cause) {
			throw new ConfigurationException("An error occurred loading a configuration file", cause);
		}
	}

	private void log(final String message, final boolean isLocal) {
		this.log(message, isLocal, null);
	}

	private void log(final String message, final boolean isLocal, final Throwable cause) {
		if (isLocal) {
			logger.warn(message, cause);
		} else {
			logger.debug(message, cause);
		}
	}

	private void logLoadingParameter(final Property annotation, final Object pojo, final Field field) throws IllegalArgumentException,
			IllegalAccessException {

		Object logValue = this.getProperty(pojo, field);
		if (logValue == null) {
			logValue = "";
		}

		String type = "explicit";
		if (annotation.defaultValue().equals(logValue)) {
			type = "default";
		}

		if (field.getType().isEnum() && annotation.defaultValue().equalsIgnoreCase(logValue.toString())) {
			type = "default";
		}

		final StringBuffer sb = new StringBuffer(50);
		sb.append("Parameter loaded with ");
		sb.append(type);
		sb.append(" value: ");
		sb.append(annotation.key());
		sb.append("=");
		sb.append(logValue);

		logger.info(sb.toString());
	}

	private void populate(final Object pojo) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ConfigurationException,
			SecurityException, NoSuchFieldException {

		Property annotation;
		Object fieldValue;

		for (final Field field : pojo.getClass().getDeclaredFields()) {
			annotation = field.getAnnotation(Property.class);

			if (annotation == null) {
				fieldValue = this.getProperty(pojo, field);

				if (fieldValue == null && !field.getType().isPrimitive()) {
					fieldValue = this.initFieldValue(field);
					this.setProperty(pojo, field, fieldValue);
				}

				if (fieldValue != null) {
					this.populate(fieldValue);
				}

			} else {
				fieldValue = this.getValue(annotation);
				this.setProperty(pojo, field, fieldValue);
				this.logLoadingParameter(annotation, pojo, field);
			}
		}
	}

	private void setProperty(final Object pojo, final Field field, final Object fieldValue) throws IllegalAccessException, InvocationTargetException,
			ConfigurationException, NoSuchMethodException {

		if (Modifier.isFinal(field.getModifiers())) {
			final String message = "Failed to set property: " + pojo.getClass().getName() + "." + field.getType().getName() + " is final";
			this.log(message, field.getType().isLocalClass());

		} else {
			Object newValue = fieldValue;

			if (field.getType().isEnum()) {
				newValue = this.getEnumProperty(pojo, field, fieldValue);
			}

			if (field.getType().equals(Boolean.class) || this.getProperty(pojo, field) instanceof Boolean) {
				newValue = this.getBooleanProperty(fieldValue);
			}

			if (newValue == null || !newValue.equals(fieldValue)) {
				BeanUtilsBean.getInstance().getPropertyUtils().setSimpleProperty(pojo, field.getName(), newValue);

			} else {
				BeanUtils.setProperty(pojo, field.getName(), newValue);
			}
		}
	}
}
