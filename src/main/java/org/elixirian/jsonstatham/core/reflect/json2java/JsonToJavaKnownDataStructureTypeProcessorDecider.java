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

import static org.elixirian.kommonlee.util.collect.Lists.*;
import static org.elixirian.kommonlee.util.MessageFormatter.*;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverter;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava;
import org.elixirian.jsonstatham.core.convertible.JsonArray;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonArray;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.json.JSONArray;

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
public final class JsonToJavaKnownDataStructureTypeProcessorDecider implements
    KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Type>
{
  /* @formatter:off */
  public static final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<? extends Type>> 
                            DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP;
  /* @formatter:on */

  static
  {
    final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<? extends Type>> map =
      new LinkedHashMap<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<? extends Type>>();

    map.put(Array.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
      @Override
      public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
          final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
          JsonStathamException
      {
        final Class<?> actualValueType = value.getClass();

        JsonArray jsonArray = null;
        if (JsonArray.class.isAssignableFrom(actualValueType))
        {
          jsonArray = (JsonArray) value;
        }
        else if (JSONArray.class.isAssignableFrom(actualValueType))
        {
          jsonArray = new OrgJsonJsonArray((JSONArray) value);
        }
        else
        {
          throw new JsonStathamException(
              format(
                  "Unknown array type [valueType: %s][value: %s]\n"
                      + "[input] ReflectionJsonToJavaConverter reflectionJsonToJavaConverter: %s, Class<?> valueType: %s, Object value: %s",
                  valueType, value, reflectionJsonToJavaConverter, valueType, value));
        }

        final Class<?> targetClass = valueType;
        final int length = jsonArray.length();
        try
        {
          if (targetClass.isArray())
          {
            final Class<?> componentType = targetClass.getComponentType();
            final Object array = Array.newInstance(componentType, length);
            for (int i = 0; i < length; i++)
            {
              Array.set(array, i,
                  reflectionJsonToJavaConverter.resolveElement(componentType, jsonArray.get(i)));
            }
            @SuppressWarnings("unchecked")
            final
            T t = (T) array;
            return t;
          }
          else if (Collection.class.isAssignableFrom(targetClass))
          {
            final List<Object> list = newArrayList();
            for (int i = 0; i < length; i++)
            {
              list.add(reflectionJsonToJavaConverter.resolveElement(Object.class, jsonArray.get(i)));
            }
            @SuppressWarnings("unchecked")
            final T t = (T) list;
            return t;
          }
        }
        catch (final Exception e)
        {
          throw new JsonStathamException(
              format(
                  "[input] ReflectionJsonToJavaConverter reflectionJsonToJavaConverter: %s, Class<?> valueType: %s, Object value: %s",
                  reflectionJsonToJavaConverter, valueType, value), e);
        }
        throw new JsonStathamException(format("Unknown type [class: %s] [JsonArray: %s]", targetClass,
            jsonArray));
      }
    });

    map.put(Collection.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<ParameterizedType>() {

      @Override
      public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
          final ParameterizedType valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final Class<?> fieldType = (Class<?>) valueType.getRawType();
        try
        {
          if (Collection.class.isAssignableFrom(fieldType))
          {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            final Collection<?> collection =
              reflectionJsonToJavaConverter.createCollectionWithValues((Class<Collection>) fieldType,
                  valueType.getActualTypeArguments()[0], value);
            return collection;
          }
        }
        catch (final Exception e)
        {
          throw new JsonStathamException(
              format(
                  "[input] ReflectionJsonToJavaConverter reflectionJsonToJavaConverter: %s, ParameterizedType valueType: %s, Object value: %s",
                  reflectionJsonToJavaConverter, valueType, value), e);
        }
        throw new JsonStathamException(format("Unknown type [class: %s][object: %s]", fieldType, value));
      }
    });

    map.put(Map.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Type>() {
      @Override
      public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter, final Type valueType,
          final Object value) throws IllegalArgumentException, IllegalAccessException, JsonStathamException
      {
        if (valueType instanceof ParameterizedType)
        {
          final ParameterizedType parameterizedType = (ParameterizedType) valueType;
          @SuppressWarnings("unchecked")
          final Class<Map<String, Object>> mapType = (Class<Map<String, Object>>) parameterizedType.getRawType();
          return reflectionJsonToJavaConverter.createHashMapWithKeysAndValues(mapType,
              ((ParameterizedType) valueType).getActualTypeArguments()[1], value);
        }
        @SuppressWarnings("unchecked")
        final Class<Map<String, Object>> mapType = (Class<Map<String, Object>>) valueType;
        return reflectionJsonToJavaConverter.createHashMapWithKeysAndValues(mapType, value.getClass(), value);
        // return valueType instanceof ParameterizedType ?
        // reflectionJsonToJavaConverter.createHashMapWithKeysAndValues(
        // ((ParameterizedType) valueType).getActualTypeArguments()[1], value)
        // : reflectionJsonToJavaConverter.createHashMapWithKeysAndValues(valueType, value);
        // throw new JsonStathamException(format("Unknown type [class: %s][object: %s]", fieldType, value));
      }
    });

    map.put(Iterable.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<ParameterizedType>() {
      @Override
      public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
          final ParameterizedType valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
          JsonStathamException
      {
        @SuppressWarnings("unchecked")
        final
        Collection<?> collection =
          reflectionJsonToJavaConverter.createCollectionWithValues(List.class, valueType.getActualTypeArguments()[0],
              value);
        return collection;
      }
    });

    map.put(Iterator.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<ParameterizedType>() {
      @Override
      public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
          final ParameterizedType valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
          JsonStathamException
      {
        @SuppressWarnings("unchecked")
        final Collection<?> collection =
          reflectionJsonToJavaConverter.createCollectionWithValues(List.class, valueType.getActualTypeArguments()[0],
              value);
        return collection.iterator();
      }
    });

    map.put(Entry.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<ParameterizedType>() {
      @Override
      public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
          final ParameterizedType valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
          JsonStathamException
      {
        @SuppressWarnings("unchecked")
        final Class<Map<String, Object>> mapType = (Class<Map<String, Object>>) valueType.getRawType();
        final Map<String, Object> map =
          reflectionJsonToJavaConverter.createHashMapWithKeysAndValues(mapType, valueType.getActualTypeArguments()[1],
              value);
        for (final Entry<String, Object> entry : map.entrySet())
        {
          return entry;
        }
        return null;
      }
    });

    DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP = Collections.unmodifiableMap(map);
  }

  /* @formatter:off */
  private final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<? extends Type>> 
                      KnownDataStructuresProcessorMap;
  /* @formatter:on */

  public JsonToJavaKnownDataStructureTypeProcessorDecider()
  {
    this.KnownDataStructuresProcessorMap = DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP;
  }

  @Override
  public KnownTypeProcessorWithReflectionJsonToJavaConverter<Type> decide(final Type type)
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

    /* @formatter:off */
    for (final Entry<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<? extends Type>> entry : 
              KnownDataStructuresProcessorMap.entrySet())
    {
      /* @formatter:on */
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
