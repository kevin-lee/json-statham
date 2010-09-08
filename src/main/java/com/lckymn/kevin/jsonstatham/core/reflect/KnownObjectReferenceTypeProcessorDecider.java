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

import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessor;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDecider;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-10)
 */
public class KnownObjectReferenceTypeProcessorDecider implements KnownTypeProcessorDecider
{
	public static final Map<Class<?>, KnownTypeProcessor> DEFAULT_KNOWN_TYPE_PROCESSOR_MAP;

	static
	{
		final Map<Class<?>, KnownTypeProcessor> tempMap = new HashMap<Class<?>, KnownTypeProcessor>();
		tempMap.put(Date.class, new KnownTypeProcessor()
		{
			@Override
			public Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, final Object source) throws IllegalArgumentException,
					IllegalAccessException, JsonStathamException
			{
				return reflectionJavaToJsonConverter.createJsonValue(source.toString());
			}
		});
		tempMap.put(Calendar.class, new KnownTypeProcessor()
		{
			@Override
			public Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, final Object source) throws IllegalArgumentException,
					IllegalAccessException, JsonStathamException
			{
				return reflectionJavaToJsonConverter.createJsonValue(((Calendar) source).getTime()
						.toString());
			}

		});
		tempMap.put(Entry.class, new KnownTypeProcessor()
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

	private final Map<Class<?>, KnownTypeProcessor> knownTypeProcessorMap;

	public KnownObjectReferenceTypeProcessorDecider()
	{
		knownTypeProcessorMap = DEFAULT_KNOWN_TYPE_PROCESSOR_MAP;
	}

	public KnownObjectReferenceTypeProcessorDecider(Map<Class<?>, KnownTypeProcessor> knownTypeProcessorMap)
	{
		this.knownTypeProcessorMap = Collections.unmodifiableMap(knownTypeProcessorMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDecider#getKnownTypeProcessor(java.lang.Object)
	 */
	@Override
	public KnownTypeProcessor decide(Class<?> type)
	{
		for (Entry<Class<?>, KnownTypeProcessor> entry : knownTypeProcessorMap.entrySet())
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
