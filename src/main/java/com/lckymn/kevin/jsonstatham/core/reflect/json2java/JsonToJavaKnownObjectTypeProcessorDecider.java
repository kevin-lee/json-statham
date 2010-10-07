/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect.json2java;

import static com.lckymn.kevin.common.util.MessageFormatter.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverter;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-10-04)
 */
public final class JsonToJavaKnownObjectTypeProcessorDecider implements
		KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>
{
	public static final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> DEFAULT_KNOWN_OBJECT_TYPE_PROCESSOR_MAP;

	static
	{
		Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> map =
			new HashMap<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>>();
		map.put(Date.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>()
		{
			@Override
			public <T> Object process(
					@SuppressWarnings("unused") ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					Class<?> valueType, Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				if (long.class.equals(value.getClass()) || Long.class.equals(value.getClass()))
				{
					return new Date(((Long) value).longValue());
				}
				throw new JsonStathamException(format("Unknown type [class: %s][object: %s]", valueType, value));
			}
		});

		map.put(Calendar.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>()
		{
			@Override
			public <T> Object process(
					@SuppressWarnings("unused") ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					Class<?> valueType, Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				if (long.class.equals(value.getClass()) || Long.class.equals(value.getClass()))
				{
					final Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(((Long) value).longValue());
					return calendar;
				}
				throw new JsonStathamException(format("Unknown type [class: %s][object: %s]", valueType, value));
			}

		});

		map.put(JSONObject.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>()
		{
			@Override
			public <T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter, Class<?> valueType,
					Object value) throws IllegalArgumentException, IllegalAccessException, JsonStathamException
			{
				return reflectionJsonToJavaConverter.createFromJsonObject(valueType, new OrgJsonJsonObjectConvertible(
						(JSONObject) value));
			}
		});

		map.put(JsonObjectConvertible.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>()
		{
			@Override
			public <T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter, Class<?> valueType,
					Object value) throws IllegalArgumentException, IllegalAccessException, JsonStathamException
			{
				return reflectionJsonToJavaConverter.createFromJsonObject(valueType,
						(OrgJsonJsonObjectConvertible) value);
			}

		});

		DEFAULT_KNOWN_OBJECT_TYPE_PROCESSOR_MAP = Collections.unmodifiableMap(map);
	}

	public final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> KnownObjectTypeProcessorMap;

	public JsonToJavaKnownObjectTypeProcessorDecider()
	{
		this.KnownObjectTypeProcessorMap = DEFAULT_KNOWN_OBJECT_TYPE_PROCESSOR_MAP;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDeciderForJsonToJava#decide(java.lang.Class)
	 */
	@Override
	public KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>> decide(Class<?> type)
	{
		for (Entry<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> entry : KnownObjectTypeProcessorMap.entrySet())
		{
			if (entry.getKey()
					.isAssignableFrom(type))
			{
				return entry.getValue();
			}
		}
		return null;
	}

}
