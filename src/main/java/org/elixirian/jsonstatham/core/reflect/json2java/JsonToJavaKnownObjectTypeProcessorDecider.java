/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original JSON Statham project is owned by Lee, Seong Hyun (Kevin).
 *
 * -What does it mean to you?
 * Nothing, unless you want to take the ownership of
 * "the original project" (not yours or forked & modified one).
 * You are free to use it for both non-commercial and commercial projects
 * and free to modify it as the Apache License allows.
 *
 * -So why is this condition necessary?
 * It is only to protect the original project (See the case of Java).
 *
 *
 * Copyright 2009 Lee, Seong Hyun (Kevin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elixirian.jsonstatham.core.reflect.json2java;

import static org.elixirian.kommonlee.util.MessageFormatter.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverter;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava;
import org.elixirian.jsonstatham.core.convertible.AbstractJsonObjectConvertiblePair;
import org.elixirian.jsonstatham.core.convertible.ImmutableJsonObjectConvertiblePair;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.MutableJsonObjectConvertiblePair;
import org.elixirian.jsonstatham.core.convertible.OrderedJsonObject;
import org.elixirian.jsonstatham.core.convertible.UnorderedJsonObject;
import org.elixirian.jsonstatham.exception.JsonStathamException;

/**
 * <pre>
 *     ___  _____                                _____
 *    /   \/    /_________  ___ ____ __ ______  /    /   ______  ______
 *   /        / /  ___ \  \/  //___// //     / /    /   /  ___ \/  ___ \
 *  /        \ /  _____/\    //   //   __   / /    /___/  _____/  _____/
 * /____/\____\\_____/   \__//___//___/ /__/ /________/\_____/ \_____/
 * </pre>
 *
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-10-04)
 */
public final class JsonToJavaKnownObjectTypeProcessorDecider implements
		KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>
{
	public static final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> DEFAULT_KNOWN_OBJECT_TYPE_PROCESSOR_MAP;

	static
	{
		final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> map =
			new LinkedHashMap<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>>();
		map.put(Date.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
			@Override
			public <T> Object process(
					@SuppressWarnings("unused") final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				if (long.class.equals(value.getClass()) || Long.class.equals(value.getClass()))
				{
					return new Date(((Long) value).longValue());
				}
				throw new JsonStathamException(format("Unknown type [class: %s][object: %s]", valueType, value));
			}
		});

		map.put(Calendar.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
			@Override
			public <T> Object process(
					@SuppressWarnings("unused") final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
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

//		map.put(JSONObject.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
//			@Override
//			public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
//					final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
//					JsonStathamException
//			{
//				return reflectionJsonToJavaConverter.createFromJsonObject(valueType, new OrgJsonJsonObject((JSONObject) value));
//			}
//		});

		map.put(OrderedJsonObject.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
			@Override
			public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				final JsonObject castedValue = (JsonObject) value;
				if (castedValue.isNull())
				{
					return null;
				}
				final JsonObject result = getJsonObject(valueType, castedValue);
				if (null != result)
				{
					return result;
				}
				return reflectionJsonToJavaConverter.createFromJsonObject(valueType, (OrderedJsonObject) value);
			}

		});

		map.put(UnorderedJsonObject.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
			@Override
			public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				final JsonObject castedValue = (JsonObject) value;
				if (castedValue.isNull())
				{
					return null;
				}
				final JsonObject result = getJsonObject(valueType, castedValue);
				if (null != result)
				{
					return result;
				}
				return reflectionJsonToJavaConverter.createFromJsonObject(valueType, (UnorderedJsonObject) value);
			}

		});

		map.put(JsonObject.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
			@Override
			public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				final JsonObject castedValue = (JsonObject) value;
				if (castedValue.isNull())
				{
					return null;
				}
				final JsonObject result = getJsonObject(valueType, castedValue);
				if (null != result)
				{
					return result;
				}
				return reflectionJsonToJavaConverter.createFromJsonObject(valueType, castedValue);
			}

		});

		map.put(AbstractJsonObjectConvertiblePair.class,
				new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
					@Override
					public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
							final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
							JsonStathamException
					{
						final JsonObject castedValue = (JsonObject) value;
						if (castedValue.isNull())
						{
							return null;
						}
						if (0 == castedValue.fieldLength())
						{
							return null;
						}
						if (ImmutableJsonObjectConvertiblePair.class.isAssignableFrom(valueType))
						{
							final String name = castedValue.getNames()[0];
							final ImmutableJsonObjectConvertiblePair<?, ?> immutableJsonObjectConvertiblePair =
								new ImmutableJsonObjectConvertiblePair<Object, Object>(name, castedValue.get(name));
							return immutableJsonObjectConvertiblePair;
						}
						else if (MutableJsonObjectConvertiblePair.class.isAssignableFrom(valueType))
						{
							final String name = castedValue.getNames()[0];
							final MutableJsonObjectConvertiblePair<?, ?> mutableJsonObjectConvertiblePair =
								new MutableJsonObjectConvertiblePair<Object, Object>(name, castedValue.get(name));
							return mutableJsonObjectConvertiblePair;
						}
						else
						{
							throw new JsonStathamException(
									format(
											"Unknown AbstractJsonObjectConvertiblePair (Class<?> valueType: %s) type! "
													+ "[ReflectionJsonToJavaConverter reflectionJsonToJavaConverter: %s, Class<?> valueType: %s, Object value: %s] "
													+ "It must be either org.elixirian.jsonstatham.core.convertible.ImmutableJsonObjectConvertiblePair or org.elixirian.jsonstatham.core.convertible.MutableJsonObjectConvertiblePair.",
											valueType, reflectionJsonToJavaConverter, valueType, value));
						}
					}

				});

		DEFAULT_KNOWN_OBJECT_TYPE_PROCESSOR_MAP = Collections.unmodifiableMap(map);
	}

	private static JsonObject getJsonObject(final Class<?> valueType, final JsonObject value)
	{
		return JsonObject.class.isAssignableFrom(valueType) ? value : null;
	}

	public final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> KnownObjectTypeProcessorMap;

	public JsonToJavaKnownObjectTypeProcessorDecider()
	{
		this.KnownObjectTypeProcessorMap = DEFAULT_KNOWN_OBJECT_TYPE_PROCESSOR_MAP;
	}

	@Override
	public KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>> decide(final Class<?> type)
	{
		/* @formatter:off */
    for (final Entry<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> entry :
            KnownObjectTypeProcessorMap.entrySet())
    {
      /* @formatter:on */
			if (entry.getKey()
					.isAssignableFrom(type))
			{
				return entry.getValue();
			}
		}
		return null;
	}

}
