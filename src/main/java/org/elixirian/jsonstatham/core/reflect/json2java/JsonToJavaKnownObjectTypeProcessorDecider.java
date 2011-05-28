/**
 * 
 */
package org.elixirian.jsonstatham.core.reflect.json2java;

import static org.elixirian.common.util.MessageFormatter.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverter;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertible;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObjectConvertible;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.json.JSONObject;


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
 * @version 0.0.1 (2010-10-04)
 */
public final class JsonToJavaKnownObjectTypeProcessorDecider implements
    KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>
{
  public static final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> DEFAULT_KNOWN_OBJECT_TYPE_PROCESSOR_MAP;

  static
  {
    Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> map =
      new HashMap<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>>();
    map.put(Date.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
      @Override
      public <T> Object process(
          @SuppressWarnings("unused") ReflectionJsonToJavaConverter reflectionJsonToJavaConverter, Class<?> valueType,
          Object value) throws IllegalArgumentException, IllegalAccessException, JsonStathamException
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
          @SuppressWarnings("unused") ReflectionJsonToJavaConverter reflectionJsonToJavaConverter, Class<?> valueType,
          Object value) throws IllegalArgumentException, IllegalAccessException, JsonStathamException
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

    map.put(JSONObject.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
      @Override
      public <T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter, Class<?> valueType,
          Object value) throws IllegalArgumentException, IllegalAccessException, JsonStathamException
      {
        return reflectionJsonToJavaConverter.createFromJsonObject(valueType, new OrgJsonJsonObjectConvertible(
            (JSONObject) value));
      }
    });

    map.put(JsonObjectConvertible.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
      @Override
      public <T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter, Class<?> valueType,
          Object value) throws IllegalArgumentException, IllegalAccessException, JsonStathamException
      {
        return reflectionJsonToJavaConverter.createFromJsonObject(valueType, (OrgJsonJsonObjectConvertible) value);
      }

    });

    DEFAULT_KNOWN_OBJECT_TYPE_PROCESSOR_MAP = Collections.unmodifiableMap(map);
  }

  public final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> KnownObjectTypeProcessorMap;

  public JsonToJavaKnownObjectTypeProcessorDecider()
  {
    this.KnownObjectTypeProcessorMap = DEFAULT_KNOWN_OBJECT_TYPE_PROCESSOR_MAP;
  }

  @Override
  public KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>> decide(Class<?> type)
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