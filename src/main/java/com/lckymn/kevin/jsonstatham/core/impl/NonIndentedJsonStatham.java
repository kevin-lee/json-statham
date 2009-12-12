/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONArray;
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
 * @version 0.04 (2009-12-12) It can handle array, List and Map.
 */
public class NonIndentedJsonStatham implements JsonStatham
{
	private static interface KnownTypeProcessor
	{
		Object process(NonIndentedJsonStatham jsonStatham, Object source) throws IllegalArgumentException, IllegalAccessException,
				JSONException;
	}

	private static final Map<Class<?>, KnownTypeProcessor> KNOWN_TYPE_PROCESSOR_MAP;
	private static final Set<Class<?>> KNOWN_FIELD_SET;

	static
	{
		Map<Class<?>, KnownTypeProcessor> tempMap = new HashMap<Class<?>, KnownTypeProcessor>();
		tempMap.put(Object[].class, new KnownTypeProcessor()
		{
			@Override
			public Object process(NonIndentedJsonStatham jsonStatham, Object source) throws IllegalArgumentException,
					IllegalAccessException, JSONException
			{
				return jsonStatham.createJsonArray((Object[]) source);
			}
		});
		tempMap.put(List.class, new KnownTypeProcessor()
		{
			@SuppressWarnings("unchecked")
			@Override
			public Object process(NonIndentedJsonStatham jsonStatham, Object source) throws IllegalArgumentException,
					IllegalAccessException, JSONException
			{
				return jsonStatham.createJsonArray((List<Object>) source);
			}
		});
		tempMap.put(Map.class, new KnownTypeProcessor()
		{
			@SuppressWarnings("unchecked")
			@Override
			public Object process(NonIndentedJsonStatham jsonStatham, Object source) throws IllegalArgumentException,
					IllegalAccessException, JSONException
			{
				return jsonStatham.createJsonMap((Map) source);
			}
		});
		KNOWN_TYPE_PROCESSOR_MAP = Collections.unmodifiableMap(tempMap);

		Set<Class<?>> tempSet = new HashSet<Class<?>>();
		tempSet.add(int.class);
		tempSet.add(Integer.class);
		tempSet.add(long.class);
		tempSet.add(Long.class);
		tempSet.add(BigInteger.class);
		tempSet.add(float.class);
		tempSet.add(Float.class);
		tempSet.add(double.class);
		tempSet.add(BigDecimal.class);
		tempSet.add(boolean.class);
		tempSet.add(Boolean.class);
		tempSet.add(String.class);
		KNOWN_FIELD_SET = Collections.unmodifiableSet(tempSet);
	}

	private JSONArray createJsonArray(Object[] array) throws IllegalArgumentException, IllegalAccessException, JSONException
	{
		JSONArray jsonArray = new JSONArray();
		for (Object eachElement : array)
		{
			jsonArray.put(createJsonValue(eachElement));
		}
		return jsonArray;
	}

	private JSONArray createJsonArray(List<Object> list) throws IllegalArgumentException, IllegalAccessException, JSONException
	{
		JSONArray jsonArray = new JSONArray();
		for (Object eachElement : list)
		{
			jsonArray.put(createJsonValue(eachElement));
		}
		return jsonArray;
	}

	private JSONObject newJSONObject()
	{
		return new JSONObject(new LinkedHashMap<String, Object>());
	}

	private JSONObject createJsonMap(Map<Object, Object> map) throws IllegalArgumentException, IllegalAccessException, JSONException
	{
		JSONObject jsonObject = newJSONObject();

		for (Entry<Object, Object> entry : map.entrySet())
		{
			jsonObject.put((String) entry.getKey(), createJsonValue(entry.getValue()));
		}
		return jsonObject;
	}

	private Object createJsonObject(Object source) throws IllegalArgumentException, IllegalAccessException, JSONException
	{
		if (null == source)
		{
			return JSONObject.NULL;
		}

		Class<?> targetClass = source.getClass();

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
			Object fieldValue = field.get(source);
			jsonObject.put(jsonFieldName, createJsonValue(fieldValue));
		}
		return jsonObject;
	}

	/**
	 * @param value
	 *            the given target object to be converted to {@link JSONObject}.
	 * @return
	 * @throws JSONException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	protected Object createJsonValue(Object value) throws IllegalArgumentException, IllegalAccessException, JSONException
	{
		if (null == value)
		{
			return JSONObject.NULL;
		}

		Class<?> type = value.getClass();

		for (Entry<Class<?>, KnownTypeProcessor> entry : KNOWN_TYPE_PROCESSOR_MAP.entrySet())
		{
			if (entry.getKey()
					.isAssignableFrom(type))
			{
				return entry.getValue()
						.process(this, value);
			}
		}

		if (KNOWN_FIELD_SET.contains(type))
		{
			return value;
		}
		else if (type.isAnnotationPresent(JsonObject.class))
		{
			return createJsonObject(value);
		}
		else
		{
			/* unknown type */
			throw new JsonStathamException("Unknown JSON object is entered.\n" + "[type: " + type + "][value: " + value + "]");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.JsonStatham#convertToJson(java.lang.Object)
	 */
	@Override
	public String convertIntoJson(Object source) throws JsonStathamException
	{
		try
		{
			if (null == source)
			{
				return JSONObject.NULL.toString();
			}

			for (Entry<Class<?>, KnownTypeProcessor> entry : KNOWN_TYPE_PROCESSOR_MAP.entrySet())
			{
				if (entry.getKey()
						.isAssignableFrom(source.getClass()))
				{
					return entry.getValue()
							.process(this, source)
							.toString();
				}
			}
			return createJsonObject(source).toString();
		}
		catch (IllegalArgumentException e)
		{
			throw new JsonStathamException("Wrong object is passed or it has illegal fields with the @JsonField annotation", e);
		}
		catch (IllegalAccessException e)
		{
			throw new JsonStathamException(e);
		}
		catch (JSONException e)
		{
			throw new JsonStathamException(e);
		}
	}

}