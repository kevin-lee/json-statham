/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-10)
 */
public class KnownObjectReferenceTypeProcessorDecider implements KnownTypeProcessorDeciderForJavaToJson
{
	public static final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> DEFAULT_KNOWN_TYPE_PROCESSOR_MAP;

	static
	{
		final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> tempMap = new HashMap<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter>();
		tempMap.put(Date.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter()
		{
			@Override
			public Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, final Object source) throws IllegalArgumentException,
					IllegalAccessException, JsonStathamException
			{
				return reflectionJavaToJsonConverter.createJsonValue(source.toString());
			}
		});
		tempMap.put(Calendar.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter()
		{
			@Override
			public Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, final Object source) throws IllegalArgumentException,
					IllegalAccessException, JsonStathamException
			{
				return reflectionJavaToJsonConverter.createJsonValue(((Calendar) source).getTime()
						.toString());
			}

		});
		tempMap.put(Entry.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter()
		{
			@SuppressWarnings("unchecked")
			@Override
			public Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, final Object source) throws IllegalArgumentException,
					IllegalAccessException, JsonStathamException
			{
				final Entry<Object, Object> entry = (Entry<Object, Object>) source;
				return reflectionJavaToJsonConverter.newJsonObjectConvertible()
						.put((String) entry.getKey(), reflectionJavaToJsonConverter.createJsonValue(entry.getValue()));
			}

		});
		DEFAULT_KNOWN_TYPE_PROCESSOR_MAP = Collections.unmodifiableMap(tempMap);
	}

	private final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> knownTypeProcessorMap;

	public KnownObjectReferenceTypeProcessorDecider()
	{
		knownTypeProcessorMap = DEFAULT_KNOWN_TYPE_PROCESSOR_MAP;
	}

	public KnownObjectReferenceTypeProcessorDecider(Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> knownTypeProcessorMap)
	{
		this.knownTypeProcessorMap = Collections.unmodifiableMap(knownTypeProcessorMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson#getKnownTypeProcessor(java.lang.Object)
	 */
	@Override
	public KnownTypeProcessorWithReflectionJavaToJsonConverter decide(Class<?> type)
	{
		for (Entry<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> entry : knownTypeProcessorMap.entrySet())
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
