/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect.java2json;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson;
import com.lckymn.kevin.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * <pre>
 *     ___  _____  __________  ___________ _____  ____
 *    /   \/    / /      \   \/   /_    _//     \/   /
 *   /        /  /    ___/\      / /   / /          /
 *  /        \  /    ___/  \    /_/   /_/          /
 * /____/\____\/_______/    \__//______/___/\_____/
 * </pre>
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-10)
 */
public class KnownDataStructureTypeProcessorDecider implements KnownTypeProcessorDeciderForJavaToJson
{
  public static final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP;

  static
  {
    final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> tempMap =
      new HashMap<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter>();
    tempMap.put(Array.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonArrayConvertible jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (int i = 0, size = Array.getLength(value); i < size; i++)
        {
          jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(Array.get(value, i)));
        }
        return jsonArrayConvertible;
      }
    });

    tempMap.put(Collection.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonArrayConvertible jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (Object eachElement : (Collection<Object>) value)
        {
          jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(eachElement));
        }
        return jsonArrayConvertible;
      }
    });
    tempMap.put(Iterable.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonArrayConvertible jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (Object eachElement : (Iterable<Object>) value)
        {
          jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(eachElement));
        }
        return jsonArrayConvertible;
      }
    });
    tempMap.put(Iterator.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonArrayConvertible jsonArrayConvertible = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (Iterator<Object> iterator = (Iterator<Object>) value; iterator.hasNext();)
        {
          jsonArrayConvertible.put(reflectionJavaToJsonConverter.createJsonValue(iterator.next()));
        }
        return jsonArrayConvertible;
      }
    });
    tempMap.put(Map.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonObjectConvertible jsonObjectConvertible = reflectionJavaToJsonConverter.newJsonObjectConvertible();
        for (Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet())
        {
          jsonObjectConvertible.put(String.valueOf(entry.getKey()),
              reflectionJavaToJsonConverter.createJsonValue(entry.getValue()));
        }
        return jsonObjectConvertible;
      }
    });
    DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP = Collections.unmodifiableMap(tempMap);
  }

  private final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> knownDataStructuresProcessorMap;

  public KnownDataStructureTypeProcessorDecider()
  {
    knownDataStructuresProcessorMap = DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP;
  }

  public KnownDataStructureTypeProcessorDecider(
      Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> knownDataStructuresProcessorMap)
  {
    this.knownDataStructuresProcessorMap = Collections.unmodifiableMap(knownDataStructuresProcessorMap);
  }

  @Override
  public <T> KnownTypeProcessorWithReflectionJavaToJsonConverter decide(Class<T> type)
  {
    if (type.isArray())
    {
      return knownDataStructuresProcessorMap.get(Array.class);
    }

    for (Entry<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> entry : knownDataStructuresProcessorMap.entrySet())
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
