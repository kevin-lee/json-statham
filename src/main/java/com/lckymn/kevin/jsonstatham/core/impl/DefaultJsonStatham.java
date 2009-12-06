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
 */
public final class DefaultJsonStatham implements JsonStatham
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

	private final boolean indented;
	private final int indentationSize;

	/**
	 * Constructs {@link DefaultJsonStatham} object with indented = false & indentationSize = 0.
	 */
	public DefaultJsonStatham()
	{
		this(false, 0);
	}

	public DefaultJsonStatham(boolean indented, int indentationSize)
	{
		this.indented = indented;
		this.indentationSize = indentationSize;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.JsonStatham#convertToJson(java.lang.Object)
	 */
	@Override
	public String convertToJson(final Object target) throws JsonStathamException
	{
		try
		{
			AssertIt.isNotNull(target, "The given object is null.");
			JSONObject jsonObject = createJsonObject(target);

			return (null == jsonObject ? null : (indented ? jsonObject.toString(indentationSize) : jsonObject.toString()));
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UnsupportedOperationException(e);
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UnsupportedOperationException(e);
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UnsupportedOperationException(e);
		}
	}

	private JSONObject createJsonObject(Object target) throws IllegalArgumentException, IllegalAccessException, JSONException
	{
		if (null == target)
		{
			return null;
		}

		Class<? extends Object> targetClass = target.getClass();
		AssertIt.isTrue(targetClass.isAnnotationPresent(JsonObject.class), "The target object is not JSON object. "
				+ "It must be annotated with com.lckymn.kevin.jsonstatham.annotation.JsonObject.");

		JSONObject jsonObject = new JSONObject();
		for (Field field : targetClass.getDeclaredFields())
		{
			if (!field.isAnnotationPresent(JsonField.class))
			{
				/* not JsonField so check next one. */
				continue;
			}
			field.setAccessible(true);
			JsonField jsonField = field.getAnnotation(JsonField.class);
			Class<?> fieldType = field.getType();

			if (FIELD_MAP.containsKey(fieldType))
			{
				JsonFieldSetter jsonFieldSetter = FIELD_MAP.get(fieldType);
				jsonFieldSetter.setJsonField(jsonObject, jsonField.name(), field.get(target));
			}
			else if (fieldType.isAnnotationPresent(JsonObject.class))
			{
				JSONObject returnedJsonObject = createJsonObject(field.get(target));
				jsonObject.put(jsonField.name(), (null == returnedJsonObject ? JSONObject.NULL : returnedJsonObject));
			}
			else
			{
				/* unknown type */
				throw new JsonStathamException("Unknown JSON object is entered.");
			}
		}
		return jsonObject;
	}
}