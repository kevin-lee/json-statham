/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.lckymn.kevin.common.validation.AssertIt;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.core.JsonStatham;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-11-21)
 * @version 0.02 (2009-12-07) It is refactored.
 * @version 0.03 (2009-12-07) It is redesigned.
 */
public abstract class AbstractJsonStatham implements JsonStatham
{
	private static interface JsonFieldSetter
	{
		void setJsonField(JSONObject jsonObject, String key, Object value) throws JSONException;
	}

	private static final JsonFieldSetter JSON_FIELD_SETTER_FOR_OBJECT = new JsonFieldSetter()
	{
		@Override
		public void setJsonField(JSONObject jsonObject, String key, Object value) throws JSONException
		{
			jsonObject.put(key, value);
		}
	};

	private static final Map<Class<?>, JsonFieldSetter> FIELD_MAP;

	static
	{
		FIELD_MAP = new HashMap<Class<?>, JsonFieldSetter>();
		FIELD_MAP.put(int.class, JSON_FIELD_SETTER_FOR_OBJECT);
		FIELD_MAP.put(Integer.class, JSON_FIELD_SETTER_FOR_OBJECT);
		FIELD_MAP.put(long.class, JSON_FIELD_SETTER_FOR_OBJECT);
		FIELD_MAP.put(Long.class, JSON_FIELD_SETTER_FOR_OBJECT);
		FIELD_MAP.put(BigInteger.class, JSON_FIELD_SETTER_FOR_OBJECT);
		FIELD_MAP.put(float.class, JSON_FIELD_SETTER_FOR_OBJECT);
		FIELD_MAP.put(Float.class, JSON_FIELD_SETTER_FOR_OBJECT);
		FIELD_MAP.put(double.class, JSON_FIELD_SETTER_FOR_OBJECT);
		FIELD_MAP.put(BigDecimal.class, JSON_FIELD_SETTER_FOR_OBJECT);
		FIELD_MAP.put(boolean.class, JSON_FIELD_SETTER_FOR_OBJECT);
		FIELD_MAP.put(Boolean.class, JSON_FIELD_SETTER_FOR_OBJECT);
		FIELD_MAP.put(String.class, JSON_FIELD_SETTER_FOR_OBJECT);
	}

	protected abstract JSONObject newJSONObject();

	/**
	 * This implementation calls overridable {@link #newJSONObject()} method to get {@link JSONObject} for internal use.
	 * 
	 * @param target
	 *            the given target object to be converted to {@link JSONObject}.
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 */
	protected JSONObject createJsonObject(Object target) throws IllegalArgumentException, IllegalAccessException, JSONException
	{
		if (null == target)
		{
			return null;
		}

		Class<? extends Object> targetClass = target.getClass();
		AssertIt.isTrue(targetClass.isAnnotationPresent(JsonObject.class), "The target object is not JSON object. "
				+ "It must be annotated with com.lckymn.kevin.jsonstatham.annotation.JsonObject.");

		JSONObject jsonObject = newJSONObject();
		for (Field field : targetClass.getDeclaredFields())
		{
			if (!field.isAnnotationPresent(JsonField.class))
			{
				/* not JsonField so check next one. */
				continue;
			}
			field.setAccessible(true);
			String jsonFieldName = field.getAnnotation(JsonField.class)
					.name();
			Class<?> fieldType = field.getType();
			Object fieldValue = field.get(target);
			if (null == fieldValue)
			{
				fieldValue = JSONObject.NULL;
			}

			if (FIELD_MAP.containsKey(fieldType))
			{
				JsonFieldSetter jsonFieldSetter = FIELD_MAP.get(fieldType);
				jsonFieldSetter.setJsonField(jsonObject, jsonFieldName, fieldValue);
			}
			else if (fieldType.isAnnotationPresent(JsonObject.class))
			{
				jsonObject.put(jsonFieldName, (JSONObject.NULL.equals(fieldValue) ? fieldValue : createJsonObject(fieldValue)));
			}
			else
			{
				/* unknown type */
				throw new JsonStathamException("Unknown JSON object is entered.\n" + "[type: " + fieldType + "][value: " + fieldValue + "]");
			}
		}
		return jsonObject;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.JsonStatham#convertToJson(java.lang.Object)
	 */
	@Override
	public abstract String convertIntoJson(final Object target) throws JsonStathamException;

}