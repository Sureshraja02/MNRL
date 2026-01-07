package com.fisglobal.fsg.dip.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class BaseDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString.append(this.getClass().getName()).append("[");
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			try {
				String name = field.getName();
				Object value;
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				value = field.isAnnotationPresent(Sensitive.class) ? getMaskedValue(field.get(this)) : field.get(this);
				toString.append(name).append("=").append(value).append(", ");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				
			}
		}
		toString = new StringBuilder(toString.toString().replaceAll(",\\s*$", ""));
		toString.append("]");
		return toString.toString();
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	private String getMaskedValue(Object input) {
		if (input == null)
			return null;
		return "****";
	}

}
