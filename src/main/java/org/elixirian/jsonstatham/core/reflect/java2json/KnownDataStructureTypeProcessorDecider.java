/**
 * 
 */
package org.elixirian.jsonstatham.core.reflect.java2json;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.elixirian.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.convertible.JsonArray;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
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
        final JsonArray jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (int i = 0, size = Array.getLength(value); i < size; i++)
        {
          jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(Array.get(value, i)));
        }
        return jsonArray;
      }
    });

    tempMap.put(Collection.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonArray jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (Object eachElement : (Collection<Object>) value)
        {
          jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(eachElement));
        }
        return jsonArray;
      }
    });
    tempMap.put(Iterable.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonArray jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (Object eachElement : (Iterable<Object>) value)
        {
          jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(eachElement));
        }
        return jsonArray;
      }
    });
    tempMap.put(Iterator.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonArray jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (Iterator<Object> iterator = (Iterator<Object>) value; iterator.hasNext();)
        {
          jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(iterator.next()));
        }
        return jsonArray;
      }
    });
    tempMap.put(Map.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> Object process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonObject jsonObject = reflectionJavaToJsonConverter.newJsonObjectConvertible();
        for (Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet())
        {
          jsonObject.put(String.valueOf(entry.getKey()),
              reflectionJavaToJsonConverter.createJsonValue(entry.getValue()));
        }
        return jsonObject;
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
