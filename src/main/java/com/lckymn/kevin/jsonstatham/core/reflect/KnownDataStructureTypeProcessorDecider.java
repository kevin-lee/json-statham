/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessor;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDecider;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-10)
 */
public class KnownDataStructureTypeProcessorDecider implements KnownTypeProcessorDecider
{
	public static final Map<Class<?>, KnownTypeProcessor> DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP;

	static
	{
		final Map<Class<?>, KnownTypeProcessor> tempMap = new HashMap<Class<?>, KnownTypeProcessor>();
		tempMap.put(Array.class, new KnownTypeProcessor()
		{
			@Override
			public Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, final Object source) throws IllegalArgumentException,
					IllegalAccessException, JsonStathamException
			{
				final JsonArrayConvertible jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
				for (int i = 0, size = Array.getLength(source); i < size; i++)
				{
					jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(Array.get(source, i)));
				}
				return jsonArrayConvertible;
			}
		});

		tempMap.put(Collection.class, new KnownTypeProcessor()
		{
			@SuppressWarnings("unchecked")
			@Override
			public Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, final Object source) throws IllegalArgumentException,
					IllegalAccessException, JsonStathamException
			{
				final JsonArrayConvertible jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
				for (Object eachElement : (Collection<Object>) source)
				{
					jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(eachElement));
				}
				return jsonArrayConvertible;
			}
		});
		tempMap.put(Iterable.class, new KnownTypeProcessor()
		{
			@SuppressWarnings("unchecked")
			@Override
			public Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, final Object source) throws IllegalArgumentException,
					IllegalAccessException, JsonStathamException
			{
				final JsonArrayConvertible jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
				for (Object eachElement : (Iterable<Object>) source)
				{
					jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(eachElement));
				}
				return jsonArrayConvertible;
			}
		});
		tempMap.put(Iterator.class, new KnownTypeProcessor()
		{
			@SuppressWarnings("unchecked")
			@Override
			public Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, final Object source) throws IllegalArgumentException,
					IllegalAccessException, JsonStathamException
			{
				final JsonArrayConvertible jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
				for (Iterator<Object> iterator = (Iterator<Object>) source; iterator.hasNext();)
				{
					jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(iterator.next()));
				}
				return jsonArrayConvertible;
			}
		});
		tempMap.put(Map.class, new KnownTypeProcessor()
		{
			@SuppressWarnings("unchecked")
			@Override
			public Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, final Object source) throws IllegalArgumentException,
					IllegalAccessException, JsonStathamException
			{
				final JsonObjectConvertible jsonObjectConvertible = reflectionJavaToJsonConverter.newJsonObjectConvertible();
				for (Entry<Object, Object> entry : ((Map<Object, Object>) source).entrySet())
				{
					jsonObjectConvertible.put(String.valueOf(entry.getKey()), reflectionJavaToJsonConverter.createJsonValue(entry.getValue()));
				}
				return jsonObjectConvertible;
			}
		});
		DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP = Collections.unmodifiableMap(tempMap);
	}

	private final Map<Class<?>, KnownTypeProcessor> knownDataStructuresProcessorMap;

	public KnownDataStructureTypeProcessorDecider()
	{
		knownDataStructuresProcessorMap = DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP;
	}

	public KnownDataStructureTypeProcessorDecider(Map<Class<?>, KnownTypeProcessor> knownDataStructuresProcessorMap)
	{
		this.knownDataStructuresProcessorMap = Collections.unmodifiableMap(knownDataStructuresProcessorMap);
	}

	@Override
	public KnownTypeProcessor decide(Class<?> type)
	{
		if (type.isArray())
		{
			return knownDataStructuresProcessorMap.get(Array.class);
		}

		for (Entry<Class<?>, KnownTypeProcessor> entry : knownDataStructuresProcessorMap.entrySet())
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
