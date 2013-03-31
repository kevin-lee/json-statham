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
package org.elixirian.jsonstatham.core.reflect.java2json;

import static org.elixirian.kommonlee.util.Objects.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.elixirian.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.type.Pair;

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
 * @version 0.0.1 (2010-06-10)
 */
public class KnownObjectReferenceTypeProcessorDecider implements KnownTypeProcessorDeciderForJavaToJson
{
  public static final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> DEFAULT_KNOWN_TYPE_PROCESSOR_MAP;

  static
  {
    final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> tempMap =
      new HashMap<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter>();
    tempMap.put(Date.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        return reflectionJavaToJsonConverter.createJsonValue(value.toString());
      }
    });
    tempMap.put(Calendar.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        return reflectionJavaToJsonConverter.createJsonValue(((Calendar) value).getTime()
            .toString());
      }

    });
    tempMap.put(Entry.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final Entry<Object, Object> entry = (Entry<Object, Object>) value;
        return reflectionJavaToJsonConverter.newJsonObjectConvertible()
            .put((String) entry.getKey(), reflectionJavaToJsonConverter.createJsonValue(entry.getValue()));
      }

    });
    // tempMap.put(AbstractJsonObjectConvertiblePair.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
    tempMap.put(Pair.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final Pair<Object, Object> pair = (Pair<Object, Object>) value;
        return reflectionJavaToJsonConverter.newJsonObjectConvertible()
            .put(toStringOf(pair.getValue1()), reflectionJavaToJsonConverter.createJsonValue(pair.getValue2()));
      }

    });
    tempMap.put(JsonObject.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @Override
      public <T> Object process(
          @SuppressWarnings("unused") final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        return value;
      }
    });
    DEFAULT_KNOWN_TYPE_PROCESSOR_MAP = Collections.unmodifiableMap(tempMap);
  }

  private final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> knownTypeProcessorMap;

  public KnownObjectReferenceTypeProcessorDecider()
  {
    knownTypeProcessorMap = DEFAULT_KNOWN_TYPE_PROCESSOR_MAP;
  }

  public KnownObjectReferenceTypeProcessorDecider(
      final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> knownTypeProcessorMap)
  {
    this.knownTypeProcessorMap = Collections.unmodifiableMap(knownTypeProcessorMap);
  }

  /*
   * (non-Javadoc)
   * @see org.elixirian.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson#getKnownTypeProcessor(java.lang.Object)
   */
  @Override
  public <T> KnownTypeProcessorWithReflectionJavaToJsonConverter decide(final Class<T> type)
  {
    for (final Entry<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> entry : knownTypeProcessorMap.entrySet())
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
