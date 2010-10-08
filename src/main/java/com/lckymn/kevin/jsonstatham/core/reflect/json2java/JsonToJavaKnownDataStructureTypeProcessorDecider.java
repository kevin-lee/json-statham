/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect.json2java;

import static com.lckymn.kevin.common.util.MessageFormatter.*;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;

import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverter;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-10-04)
 */
public final class JsonToJavaKnownDataStructureTypeProcessorDecider implements
		KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Type>
{
	public static final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<? extends Type>> DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP;

	static
	{
		final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<? extends Type>> map =
			new LinkedHashMap<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<? extends Type>>();

		map.put(Array.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>()
		{
			@Override
			public <T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter, Class<?> valueType,
					Object value) throws IllegalArgumentException, IllegalAccessException, JsonStathamException
			{
				final Class<?> actualValueType = value.getClass();

				JsonArrayConvertible jsonArrayConvertible = null;
				if (JsonArrayConvertible.class.isAssignableFrom(actualValueType))
				{
					jsonArrayConvertible = (JsonArrayConvertible) value;
				}
				else if (JSONArray.class.isAssignableFrom(actualValueType))
				{
					jsonArrayConvertible = new OrgJsonJsonArrayConvertible((JSONArray) value);
				}
				else
				{
					throw new JsonStathamException(format("Unknown array type [valueType: %s][value: %s]", valueType,
							value));
				}

				final Class<?> targetClass = valueType;
				final int length = jsonArrayConvertible.length();
				try
				{
					if (targetClass.isArray())
					{
						final Class<?> componentType = targetClass.getComponentType();
						final Object array = Array.newInstance(componentType, length);
						for (int i = 0; i < length; i++)
						{
							Array.set(
									array,
									i,
									reflectionJsonToJavaConverter.resolveElement(componentType,
											jsonArrayConvertible.get(i)));
						}
						@SuppressWarnings("unchecked")
						T t = (T) array;
						return t;
					}
					else if (Collection.class.isAssignableFrom(targetClass))
					{
						final List<Object> list = new ArrayList<Object>();
						for (int i = 0; i < length; i++)
						{
							list.add(reflectionJsonToJavaConverter.resolveElement(Object.class,
									jsonArrayConvertible.get(i)));
						}
						@SuppressWarnings("unchecked")
						final T t = (T) list;
						return t;
					}
				}
				catch (Exception e)
				{
					throw new JsonStathamException(e);
				}
				throw new JsonStathamException(format("Unknown type [class: %s] [JsonArrayConvertible: %s]",
						targetClass, jsonArrayConvertible));
			}
		});

		map.put(Collection.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<ParameterizedType>()
		{

			@Override
			public <T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					ParameterizedType valueType, Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				final Class<?> fieldType = (Class<?>) valueType.getRawType();
				try
				{
					if (Collection.class.isAssignableFrom(fieldType))
					{
						@SuppressWarnings({ "unchecked", "rawtypes" })
						final Collection<?> collection =
							reflectionJsonToJavaConverter.createCollectionWithValues(
									valueType.getActualTypeArguments()[0], (Class<Collection>) fieldType, value);
						return collection;
					}
				}
				catch (Exception e)
				{
					throw new JsonStathamException(e);
				}
				throw new JsonStathamException(format("Unknown type [class: %s][object: %s]", fieldType, value));
			}
		});

		map.put(Map.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<ParameterizedType>()
		{
			@Override
			public <T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					ParameterizedType valueType, Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				return reflectionJsonToJavaConverter.createHashMapWithKeysAndValues(
						valueType.getActualTypeArguments()[1], value);
			}
		});

		map.put(Iterable.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<ParameterizedType>()
		{
			@Override
			public <T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					ParameterizedType valueType, Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				@SuppressWarnings("unchecked")
				Collection<?> collection =
					reflectionJsonToJavaConverter.createCollectionWithValues(valueType.getActualTypeArguments()[0],
							List.class, value);
				return collection;
			}
		});

		map.put(Iterator.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<ParameterizedType>()
		{
			@Override
			public <T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					ParameterizedType valueType, Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				@SuppressWarnings("unchecked")
				final Collection<?> collection =
					reflectionJsonToJavaConverter.createCollectionWithValues(valueType.getActualTypeArguments()[0],
							List.class, value);
				return collection.iterator();
			}
		});

		map.put(Entry.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<ParameterizedType>()
		{
			@Override
			public <T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					ParameterizedType valueType, Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				final Map<String, Object> map =
					reflectionJsonToJavaConverter.createHashMapWithKeysAndValues(valueType.getActualTypeArguments()[1],
							value);
				for (Entry<String, Object> entry : map.entrySet())
				{
					return entry;
				}
				return null;
			}
		});

		DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP = Collections.unmodifiableMap(map);
	}

	private final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<? extends Type>> KnownDataStructuresProcessorMap;

	public JsonToJavaKnownDataStructureTypeProcessorDecider()
	{
		this.KnownDataStructuresProcessorMap = DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDeciderForJsonToJava#decide(java.lang.Class)
	 */
	@Override
	public KnownTypeProcessorWithReflectionJsonToJavaConverter<Type> decide(Type type)
	{
		final Class<?> classType =
			type instanceof Class ? (Class<?>) type
					: type instanceof ParameterizedType ? (Class<?>) ((ParameterizedType) type).getRawType() : null;

		if (null == classType)
		{
			return null;
		}

		if (classType.isArray())
		{
			@SuppressWarnings("unchecked")
			final KnownTypeProcessorWithReflectionJsonToJavaConverter<Type> result =
				(KnownTypeProcessorWithReflectionJsonToJavaConverter<Type>) KnownDataStructuresProcessorMap.get(Array.class);
			return result;
		}

		for (Entry<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<? extends Type>> entry : KnownDataStructuresProcessorMap.entrySet())
		{
			if (entry.getKey()
					.isAssignableFrom(classType))
			{
				@SuppressWarnings("unchecked")
				final KnownTypeProcessorWithReflectionJsonToJavaConverter<Type> result =
					(KnownTypeProcessorWithReflectionJsonToJavaConverter<Type>) entry.getValue();
				return result;
			}
		}
		return null;
	}
}
