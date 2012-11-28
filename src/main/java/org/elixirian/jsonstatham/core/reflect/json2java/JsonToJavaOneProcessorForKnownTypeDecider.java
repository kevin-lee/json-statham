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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverter;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava;
import org.elixirian.jsonstatham.core.SimpleKnownTypeChecker;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.reflect.Primitives;
import org.json.JSONObject;

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
public class JsonToJavaOneProcessorForKnownTypeDecider implements
    KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>
{
  public static final KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>> DEFAULT_KNOWN_TYPE_PROCESSOR;
  public static final Set<Class<?>> DAFAULT_KNOWN_BASIC_TYPE_SET;
  public static final Set<Class<?>> DAFAULT_KNOWN_EXTENSIBLE_BASIC_TYPE_SET;
  public static final SimpleKnownTypeChecker[] DAFAULT_SIMPLE_TYPE_CHECKERS;

  static
  {
    DEFAULT_KNOWN_TYPE_PROCESSOR = new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
      @Override
      public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
          final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
          JsonStathamException
      {
        if (JSONObject.NULL.equals(value))
        {
          if (Primitives.isPrimitive(valueType))
          {
            throw new IllegalArgumentException(format("value is null but the valueType is one of primitive type\n"
                + "(\"unused\") ReflectionJsonToJavaConverter reflectionJsonToJavaConverter:%s\n"
                + "Class<?> valueType: %s\n" + "Object value: %s", reflectionJsonToJavaConverter, valueType, value));
          }
          return null;
        }

        final Class<?> actualValueType = value.getClass();

        if (BigDecimal.class.equals(valueType))
        {
          if (null == value)
          {
            return null;
          }
          /* @formatter:off */
          if (long.class.isAssignableFrom(actualValueType) || Long.class.isAssignableFrom(actualValueType) ||
              int.class.isAssignableFrom(actualValueType) || Integer.class.isAssignableFrom(actualValueType))
          {
            return BigDecimal.valueOf(((Number) value).longValue());
          }
          /* @formatter:on */
          return new BigDecimal(value.toString());
        }

        if (BigInteger.class.equals(valueType))
        {
          if (null == value)
          {
            return null;
          }
          /* @formatter:off */
          if (long.class.isAssignableFrom(actualValueType) || Long.class.isAssignableFrom(actualValueType) ||
              int.class.isAssignableFrom(actualValueType) || Integer.class.isAssignableFrom(actualValueType))
          {
            return BigInteger.valueOf(((Number) value).longValue());
          }
          /* @formatter:on */
          return new BigInteger(value.toString());
        }

        // if (valueType.isAssignableFrom(actualValueType))
        // {
        // return value;
        // }
        if (long.class.isAssignableFrom(valueType) || Long.class.isAssignableFrom(valueType))
        {
          if (int.class.isAssignableFrom(actualValueType) || Integer.class.isAssignableFrom(actualValueType))
          {
            return Long.valueOf(((Integer) value).intValue());
          }
        }

        if (String.class.equals(actualValueType))
        {
          if (valueType.isEnum())
          {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            final Enum<?> t = Enum.valueOf((Class<? extends Enum>) valueType, (String) value);
            return t;
          }
        }
        return value;
        // throw new JsonStathamException(format(
        // "Type mismatched: [expectedType: %s][actualType: %s][object: %s]", valueType, actualValueType,
        // value));
      }
    };

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
    tempSet.add(Boolean.TYPE);
    tempSet.add(Boolean.class);
    tempSet.add(String.class);
    DAFAULT_KNOWN_BASIC_TYPE_SET = Collections.unmodifiableSet(tempSet);

    tempSet = new HashSet<Class<?>>();
    tempSet.add(Number.class);
    DAFAULT_KNOWN_EXTENSIBLE_BASIC_TYPE_SET = Collections.unmodifiableSet(tempSet);

    DAFAULT_SIMPLE_TYPE_CHECKERS = new SimpleKnownTypeChecker[] { new SimpleKnownTypeChecker() {
      @Override
      public boolean isKnown(final Class<?> type)
      {
        return type.isPrimitive();
      }
    }, new SimpleKnownTypeChecker() {
      @Override
      public boolean isKnown(final Class<?> type)
      {
        return type.isEnum();
      }
    } };
  }

  private final KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>> knownTypeProcessorWithReflectionJsonToJavaConverter;
  private final Set<Class<?>> knownBasicTypeSet;
  private final Set<Class<?>> knownExtensibleBasicTypeSet;
  private final SimpleKnownTypeChecker[] simpleKnownTypeCheckers;

  public JsonToJavaOneProcessorForKnownTypeDecider()
  {
    this.knownTypeProcessorWithReflectionJsonToJavaConverter = DEFAULT_KNOWN_TYPE_PROCESSOR;
    this.knownBasicTypeSet = DAFAULT_KNOWN_BASIC_TYPE_SET;
    this.simpleKnownTypeCheckers = DAFAULT_SIMPLE_TYPE_CHECKERS;
    this.knownExtensibleBasicTypeSet = DAFAULT_KNOWN_EXTENSIBLE_BASIC_TYPE_SET;
  }

  @Override
  public KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>> decide(final Class<?> type)
  {
    for (final SimpleKnownTypeChecker simpleKnownTypeChecker : simpleKnownTypeCheckers)
    {
      if (simpleKnownTypeChecker.isKnown(type))
      {
        return knownTypeProcessorWithReflectionJsonToJavaConverter;
      }
    }
    if (knownBasicTypeSet.contains(type))
    {
      return knownTypeProcessorWithReflectionJsonToJavaConverter;
    }

    for (final Class<?> knownType : knownExtensibleBasicTypeSet)
    {
      if (knownType.isAssignableFrom(type))
      {
        return knownTypeProcessorWithReflectionJsonToJavaConverter;
      }
    }
    return null;
  }

}