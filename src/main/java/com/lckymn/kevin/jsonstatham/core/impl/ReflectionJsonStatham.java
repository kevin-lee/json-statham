/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lckymn.kevin.common.validation.AssertIt;
import com.lckymn.kevin.common.validation.ValidateIt;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;
import com.lckymn.kevin.jsonstatham.core.JSONObjectCreator;
import com.lckymn.kevin.jsonstatham.core.JsonStatham;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2009-11-21)
 * @version 0.0.2 (2009-12-07) It is refactored.
 * @version 0.0.3 (2009-12-07) It is redesigned.
 * @version 0.0.4 (2009-12-12) It can handle array, List and Map.
 * @version 0.0.5 (2009-12-20)
 *          <p>
 *          It can handle duplicate {@link JsonField} names. => It throws an exception.
 *          </p>
 *          <p>
 *          It can also handle {@link java.util.Date} type value annotated with {@link JsonField}. => It uses the toString() method of the
 *          value object, or if the field is also annotated with {@link ValueAccessor} annotation, it uses the method specified with the
 *          {@link ValueAccessor} annotation in order to get the value.
 *          </p>
 * @version 0.0.6 (2010-02-03) {@link JSONObjectCreator} is added to create a new {@link JSONObject}.
 * @version 0.0.7 (2010-02-12) The name is changed from NonIndentedJsonStatham to ReflectionJsonStatham. When the JsonObject is converted
 *          into JSON, if any fields annotated with @JsonField without the 'name' element explicitly set, it will use the actual field names
 *          as the JsonField names.
 */
public class ReflectionJsonStatham implements JsonStatham
{
	private static interface KnownTypeProcessor
	{
		Object process(ReflectionJsonStatham jsonStatham, Object source) throws IllegalArgumentException, IllegalAccessException,
				JSONException;
	}

	private static final Map<Class<?>, KnownTypeProcessor> KNOWN_DATA_STRUCTURES_PROCESSOR_MAP;
	private static final Map<Class<?>, KnownTypeProcessor> KNOWN_TYPE_PROCESSOR_MAP;
	private static final Set<Class<?>> KNOWN_BASIC_TYPE_SET;

	private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	static
	{
		Map<Class<?>, KnownTypeProcessor> tempMap = new HashMap<Class<?>, KnownTypeProcessor>();
		tempMap.put(Object[].class, new KnownTypeProcessor()
		{
			@Override
			public Object process(ReflectionJsonStatham jsonStatham, Object source) throws IllegalArgumentException,
					IllegalAccessException, JSONException
			{
				JSONArray jsonArray = new JSONArray();
				for (Object eachElement : (Object[]) source)
				{
					jsonArray.put(jsonStatham.createJsonValue(eachElement));
				}
				return jsonArray;
			}
		});
		tempMap.put(Collection.class, new KnownTypeProcessor()
		{
			@SuppressWarnings("unchecked")
			@Override
			public Object process(ReflectionJsonStatham jsonStatham, Object source) throws IllegalArgumentException,
					IllegalAccessException, JSONException
			{
				JSONArray jsonArray = new JSONArray();
				for (Object eachElement : (Collection<Object>) source)
				{
					jsonArray.put(jsonStatham.createJsonValue(eachElement));
				}
				return jsonArray;
			}
		});
		tempMap.put(Map.class, new KnownTypeProcessor()
		{
			@SuppressWarnings("unchecked")
			@Override
			public Object process(ReflectionJsonStatham jsonStatham, Object source) throws IllegalArgumentException,
					IllegalAccessException, JSONException
			{
				JSONObject jsonObject = jsonStatham.newJSONObject();
				for (Entry<Object, Object> entry : ((Map<Object, Object>) source).entrySet())
				{
					jsonObject.put((String) entry.getKey(), jsonStatham.createJsonValue(entry.getValue()));
				}
				return jsonObject;
			}
		});
		KNOWN_DATA_STRUCTURES_PROCESSOR_MAP = Collections.unmodifiableMap(tempMap);

		tempMap = new HashMap<Class<?>, KnownTypeProcessor>();
		tempMap.put(Date.class, new KnownTypeProcessor()
		{
			@Override
			public Object process(ReflectionJsonStatham jsonStatham, Object source) throws IllegalArgumentException,
					IllegalAccessException, JSONException
			{
				return jsonStatham.createJsonValue(source.toString());
			}
		});
		tempMap.put(Calendar.class, new KnownTypeProcessor()
		{
			@Override
			public Object process(ReflectionJsonStatham jsonStatham, Object source) throws IllegalArgumentException,
					IllegalAccessException, JSONException
			{
				return jsonStatham.createJsonValue(((Calendar) source).getTime()
						.toString());
			}

		});
		KNOWN_TYPE_PROCESSOR_MAP = Collections.unmodifiableMap(tempMap);

		Set<Class<?>> tempSet = new HashSet<Class<?>>();
		tempSet.add(Integer.TYPE);
		tempSet.add(Integer.class);
		tempSet.add(Long.TYPE);
		tempSet.add(Long.class);
		tempSet.add(BigInteger.class);
		tempSet.add(Float.TYPE);
		tempSet.add(Float.class);
		tempSet.add(Double.TYPE);
		tempSet.add(Double.class);
		tempSet.add(BigDecimal.class);
		tempSet.add(Number.class);
		tempSet.add(Boolean.TYPE);
		tempSet.add(Boolean.class);
		tempSet.add(String.class);
		KNOWN_BASIC_TYPE_SET = Collections.unmodifiableSet(tempSet);
	}

	private final JSONObjectCreator jsonObjectCreator;

	public ReflectionJsonStatham(JSONObjectCreator jsonObjectCreator)
	{
		this.jsonObjectCreator = jsonObjectCreator;
	}

	private JSONObject newJSONObject()
	{
		return jsonObjectCreator.newJSONObject();
	}

	private Object createJsonObject(Object sourceObject) throws IllegalArgumentException, IllegalAccessException, JSONException
	{
		if (null == sourceObject)
		{
			return JSONObject.NULL;
		}

		Class<?> sourceClass = sourceObject.getClass();

		AssertIt.isTrue(sourceClass.isAnnotationPresent(JsonObject.class), "The target object is not JSON object. "
				+ "It must be annotated with com.lckymn.kevin.jsonstatham.annotation.JsonObject.");

		Deque<Class<?>> classStack = new ArrayDeque<Class<?>>();
		while (!Object.class.equals(sourceClass))
		{
			classStack.push(sourceClass);
			sourceClass = sourceClass.getSuperclass();
		}

		Set<String> fieldNameSet = new HashSet<String>();
		JSONObject jsonObject = newJSONObject();
		for (Class<?> eachClass : classStack)
		{
			extractJsonFields(sourceObject, eachClass, fieldNameSet, jsonObject);
		}
		return jsonObject;
	}

	private void extractJsonFields(Object source, Class<?> sourceClass, Set<String> fieldNameSet, JSONObject jsonObject)
			throws IllegalAccessException, JSONException
	{
		for (Field field : sourceClass.getDeclaredFields())
		{
			if (!field.isAnnotationPresent(JsonField.class))
			{
				/* not JsonField so check next one. */
				continue;
			}
			field.setAccessible(true);

			/* get field name from the @JsonField annotation */
			String jsonFieldName = field.getAnnotation(JsonField.class)
					.name();

			if (ValidateIt.isEmpty(jsonFieldName))
			{
				/* no field name is set in the @JsonField annotation so use the actual field name for the JsonObject field. */
				jsonFieldName = field.getName();
			}

			if (fieldNameSet.contains(jsonFieldName))
			{
				/* [ERROR] duplicate field names found */
				throw new JsonStathamException("Json filed name must be unique. [JsonField name: " + jsonFieldName + "] in [field: "
						+ field + "] is already used in another field.");
			}
			fieldNameSet.add(jsonFieldName);

			Object fieldValue = null;

			if (field.isAnnotationPresent(ValueAccessor.class))
			{
				String valueAccessorName = field.getAnnotation(ValueAccessor.class)
						.name();
				try
				{
					Method method = sourceClass.getDeclaredMethod(valueAccessorName, EMPTY_CLASS_ARRAY);
					method.setAccessible(true);
					fieldValue = method.invoke(source, EMPTY_OBJECT_ARRAY);
				}
				catch (SecurityException e)
				{
					throw new JsonStathamException(e);
				}
				catch (NoSuchMethodException e)
				{
					throw new JsonStathamException("The given ValueAccessor method that is [" + valueAccessorName + "] is not found.", e);
				}
				catch (InvocationTargetException e)
				{
					throw new JsonStathamException("Value accessor invocation failed.\n"
							+ "It might be caused by any error happened in the given value accessor method or "
							+ "The given ValueAccessor method [" + valueAccessorName
							+ "] is not a proper value accessor for the JsonField [name: " + jsonFieldName + "].", e);
				}
			}
			else
			{
				fieldValue = field.get(source);
			}
			jsonObject.put(jsonFieldName, createJsonValue(fieldValue));
		}
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

		for (Entry<Class<?>, KnownTypeProcessor> entry : KNOWN_DATA_STRUCTURES_PROCESSOR_MAP.entrySet())
		{
			if (entry.getKey()
					.isAssignableFrom(type))
			{
				return entry.getValue()
						.process(this, value);
			}
		}

		for (Entry<Class<?>, KnownTypeProcessor> entry : KNOWN_TYPE_PROCESSOR_MAP.entrySet())
		{
			if (entry.getKey()
					.isAssignableFrom(type))
			{
				return entry.getValue()
						.process(this, value);
			}
		}

		if (type.isPrimitive() || KNOWN_BASIC_TYPE_SET.contains(type))
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

			Class<?> sourceClass = source.getClass();

			for (Entry<Class<?>, KnownTypeProcessor> entry : KNOWN_DATA_STRUCTURES_PROCESSOR_MAP.entrySet())
			{
				if (entry.getKey()
						.isAssignableFrom(sourceClass))
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