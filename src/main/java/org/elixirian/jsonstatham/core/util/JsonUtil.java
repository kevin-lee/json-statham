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
package org.elixirian.jsonstatham.core.util;

import static org.elixirian.kommonlee.util.MessageFormatter.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import org.elixirian.jsonstatham.core.convertible.AbstractJsonObject;
import org.elixirian.jsonstatham.core.convertible.ImmutableJsonNameValuePair;
import org.elixirian.jsonstatham.core.convertible.JsonArray;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithOrderedJsonObject;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithUnorderedJsonObject;
import org.elixirian.jsonstatham.core.convertible.JsonConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.OrderedJsonObject;
import org.elixirian.jsonstatham.core.convertible.UnorderedJsonObject;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.io.CharAndStringWritable;
import org.elixirian.kommonlee.util.CommonConstants;
import org.elixirian.kommonlee.util.NeoArrays;

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
 * @version 0.0.1 (2010-12-25)
 */
public final class JsonUtil
{
  private JsonUtil() throws IllegalAccessException
  {
    throw new IllegalAccessException(getClass().getName() + CommonConstants.CANNOT_BE_INSTANTIATED);
  }

  public static void doubleQuote(final CharAndStringWritable charAndStringWritable, final String value)
  {
    final int length = value.length();
    char c = 0;
    charAndStringWritable.write("\"");
    for (int i = 0; i < length; i++)
    {
      final char previous = c;
      c = value.charAt(i);
      switch (c)
      {
        case '"':
        case '\\':
          charAndStringWritable.write('\\')
              .write(c);
          break;
        case '/':
          if ('<' == previous)
          {
            charAndStringWritable.write('\\');
          }
          charAndStringWritable.write(c);
          break;
        case '\b':
          charAndStringWritable.write("\\b");
          break;
        case '\f':
          charAndStringWritable.write("\\f");
          break;
        case '\n':
          charAndStringWritable.write("\\n");
          break;
        case '\r':
          charAndStringWritable.write("\\r");
          break;
        case '\t':
          charAndStringWritable.write("\\t");
          break;
        default:
          if (' ' > c || (('\u0080' <= c & '\u00a0' > c) | ('\u2000' <= c & '\u2100' > c)))
          {
            final String hex = "000" + Integer.toHexString(c);
            charAndStringWritable.write("\\u")
                .write(hex.substring(hex.length() - 4));
          }
          else
          {
            charAndStringWritable.write(c);
          }
          break;
      }
    }
    charAndStringWritable.write('"');
  }

  // public static String doubleQuote(final String value)
  // {
  // final int length = value.length();
  // char c = 0;
  // final StringBuilder quotedStringBuilder = new StringBuilder("\"");
  // for (int i = 0; i < length; i++)
  // {
  // final char previous = c;
  // c = value.charAt(i);
  // switch (c)
  // {
  // case '"':
  // case '\\':
  // quotedStringBuilder.append('\\')
  // .append(c);
  // break;
  // case '/':
  // if ('<' == previous)
  // {
  // quotedStringBuilder.append('\\');
  // }
  // quotedStringBuilder.append(c);
  // break;
  // case '\b':
  // quotedStringBuilder.append("\\b");
  // break;
  // case '\f':
  // quotedStringBuilder.append("\\f");
  // break;
  // case '\n':
  // quotedStringBuilder.append("\\n");
  // break;
  // case '\r':
  // quotedStringBuilder.append("\\r");
  // break;
  // case '\t':
  // quotedStringBuilder.append("\\t");
  // break;
  // default:
  // if (' ' > c || (('\u0080' <= c & '\u00a0' > c) | ('\u2000' <= c & '\u2100' > c)))
  // {
  // final String hex = "000" + Integer.toHexString(c);
  // quotedStringBuilder.append("\\u")
  // .append(hex.substring(hex.length() - 4));
  // }
  // else
  // {
  // quotedStringBuilder.append(c);
  // }
  // break;
  // }
  // }
  // return quotedStringBuilder.append('"')
  // .toString();
  // }

  public static void validate(final Object value)
  {
    final Double doubleValue = castIfInstanceOf(Double.class, value);
    if (null != doubleValue)
    {
      if (doubleValue.isInfinite() | doubleValue.isNaN())
      {
        throw new JsonStathamException(format("JSON allow neither infinite numbers nor NaN. [value: %s]", doubleValue));
      }
    }
    final Float floatValue = castIfInstanceOf(Float.class, value);
    if (null != floatValue)
    {
      if (floatValue.isInfinite() | floatValue.isNaN())
      {
        throw new JsonStathamException(format("JSON allow neither infinite numbers nor NaN. [value: %s]", floatValue));
      }
    }
  }

  public static String toStringValue(final Number value)
  {
    validate(value);
    String numberString = value.toString();

    if (0 < numberString.indexOf('.') && (0 > numberString.indexOf('e') & 0 > numberString.indexOf('E')))
    {
      /* remove 0s from the end. */
      int theEnd = numberString.length() - 1;
      while ('0' == numberString.charAt(theEnd))
        numberString = numberString.substring(0, theEnd--);

      if ('.' == numberString.charAt(theEnd))
        numberString = numberString.substring(0, theEnd);
    }
    return numberString;
  }

  public interface JsonObjectAndArrayCreator
  {
    JsonObject newJsonObject(final Map<Object, Object> map);

    JsonObject newJsonObject(final Object value);

    JsonArray newJsonArray(final Object[] elements);

    JsonArray newJsonArray(final Collection<?> elements);

    JsonArray newJsonArray(final Object value);
  }

  public static JsonObjectAndArrayCreator getJsonObjectAndArrayCreator(final JsonObject jsonObject)
  {
    return jsonObject instanceof OrderedJsonObject ? JSON_OBJECT_AND_ARRAY_WITH_ORDERED_JSON_OBJECT_CREATOR
        : JSON_OBJECT_AND_ARRAY_WITH_UNORDERED_JSON_OBJECT_CREATOR;
  }

  public static JsonObjectAndArrayCreator getJsonObjectAndArrayCreator(final JsonArray jsonArray)
  {
    return jsonArray instanceof JsonArrayWithOrderedJsonObject ? JSON_OBJECT_AND_ARRAY_WITH_ORDERED_JSON_OBJECT_CREATOR
        : JSON_OBJECT_AND_ARRAY_WITH_UNORDERED_JSON_OBJECT_CREATOR;
  }

  private static final JsonObjectAndArrayCreator JSON_OBJECT_AND_ARRAY_WITH_ORDERED_JSON_OBJECT_CREATOR =
    new JsonObjectAndArrayCreator() {
      @Override
      public OrderedJsonObject newJsonObject(final Map<Object, Object> map)
      {
        return OrderedJsonObject.newJsonObject(map);
      }

      @Override
      public OrderedJsonObject newJsonObject(final Object javaBean)
      {
        return OrderedJsonObject.newJsonObject(javaBean);
      }

      @Override
      public JsonArrayWithOrderedJsonObject newJsonArray(final Object[] elements)
      {
        return JsonArrayWithOrderedJsonObject.newJsonArray(elements);
      }

      @Override
      public JsonArrayWithOrderedJsonObject newJsonArray(final Collection<?> elements)
      {
        return JsonArrayWithOrderedJsonObject.newJsonArray(elements);
      }

      @Override
      public JsonArrayWithOrderedJsonObject newJsonArray(final Object value)
      {
        return JsonArrayWithOrderedJsonObject.newJsonArray(value);
      }

      @Override
      public String toString()
      {
        return "JSON_OBJECT_AND_ARRAY_WITH_ORDERED_JSON_OBJECT_CREATOR";
      }
    };

  private static final JsonObjectAndArrayCreator JSON_OBJECT_AND_ARRAY_WITH_UNORDERED_JSON_OBJECT_CREATOR =
    new JsonObjectAndArrayCreator() {
      @Override
      public UnorderedJsonObject newJsonObject(final Map<Object, Object> map)
      {
        return UnorderedJsonObject.newJsonObject(map);
      }

      @Override
      public UnorderedJsonObject newJsonObject(final Object javaBean)
      {
        return UnorderedJsonObject.newJsonObject(javaBean);
      }

      @Override
      public JsonArrayWithUnorderedJsonObject newJsonArray(final Object[] elements)
      {
        return JsonArrayWithUnorderedJsonObject.newJsonArray(elements);
      }

      @Override
      public JsonArrayWithUnorderedJsonObject newJsonArray(final Collection<?> elements)
      {
        return JsonArrayWithUnorderedJsonObject.newJsonArray(elements);
      }

      @Override
      public JsonArrayWithUnorderedJsonObject newJsonArray(final Object value)
      {
        return JsonArrayWithUnorderedJsonObject.newJsonArray(value);
      }

      @Override
      public String toString()
      {
        return "JSON_OBJECT_AND_ARRAY_WITH_UNORDERED_JSON_OBJECT_CREATOR";
      }
    };

  public static void writeValue(final CharAndStringWritable charAndStringWritable, final Object value,
      final JsonObject jsonObject)
  {
    final JsonObjectAndArrayCreator jsonObjectAndArrayCreator = getJsonObjectAndArrayCreator(jsonObject);
    // return writeValue(value, jsonObjectAndArrayCreator);
    writeValue(charAndStringWritable, value, jsonObjectAndArrayCreator);
  }

  public static void writeValue(final CharAndStringWritable charAndStringWritable, final Object value,
      final JsonArray jsonArray)
  {
    final JsonObjectAndArrayCreator jsonObjectAndArrayCreator = getJsonObjectAndArrayCreator(jsonArray);
    // return writeValue(value, jsonObjectAndArrayCreator);
    writeValue(charAndStringWritable, value, jsonObjectAndArrayCreator);
  }

  public static void writeValue(final CharAndStringWritable charAndStringWritable, final Object value,
      final JsonObjectAndArrayCreator withJsonObjectAndArrayCreator)
  {
    if (null == value)
    {
      charAndStringWritable.write(AbstractJsonObject.NULL_JSON_OBJECT.toString());

    }
    else if (value instanceof JsonConvertible)
    {
      ((JsonConvertible) value).write(charAndStringWritable);
    }
    else if (value instanceof Boolean)
    {
      charAndStringWritable.write(value.toString());
    }
    else if (value instanceof Number)
      charAndStringWritable.write(toStringValue((Number) value));
    else if (value instanceof Map)
    {
      @SuppressWarnings("unchecked")
      final Map<Object, Object> map = (Map<Object, Object>) value;
      withJsonObjectAndArrayCreator.newJsonObject(map)
          .write(charAndStringWritable);
    }
    else if (value instanceof Collection)
    {
      withJsonObjectAndArrayCreator.newJsonArray((Collection<?>) value)
          .write(charAndStringWritable);
    }
    else if (value.getClass()
        .isArray())
    {
      final int length = Array.getLength(value);
      final Object[] objects = new Object[length];
      for (int i = 0; i < length; i++)
      {
        objects[i] = Array.get(value, i);
      }
      withJsonObjectAndArrayCreator.newJsonArray(objects)
          .write(charAndStringWritable);
    }
    else
    {
      // return doubleQuote(value.toString());
      doubleQuote(charAndStringWritable, value.toString());
    }
  }

  /**
   * returns an Object if it is possible to get one based on the given stringValue.
   * <ul>
   * <li>if the stringValue is null or "null", it returns {@link AbstractJsonObject#NULL_JSON_OBJECT}.</li>
   * <li>if the stringValue is an empty String, it returns "" (an empty String).</li>
   * <li>if the stringValue is "true" (case insensitive), it returns {@link Boolean#TRUE}.</li>
   * <li>if the stringValue is "false" (case insensitive), it returns {@link Boolean#FALSE}.</li>
   * <li>if the stringValue is a decimal, it returns a {@link Double} object.</li>
   * <li>if the stringValue is an integer, it returns {@link Long} when the number.intValue() != number.longValue.
   * Otherwise, it returns an {@link Integer} object.</li>
   * <li>if the stringValue is any exception is thrown while the stringValue is converted into one of {@link Double},
   * {@link Integer} and {@link Long} objects, it ignores the exception, and still returns the given stringValue.</li>
   * <li>None of the cases above is matching, it just returns the given stringValue.</li>
   * </ul>
   * 
   * @param stringValue
   *          the given stringValue
   * @return an Object created based on the given stringValue.
   */
  public static Object fromStringToValueIfPossible(final String stringValue)
  {
    if (null == stringValue || "null".equalsIgnoreCase(stringValue))
    {
      return AbstractJsonObject.NULL_JSON_OBJECT;
    }
    if (stringValue.isEmpty())
    {
      return stringValue;
    }
    if ("true".equalsIgnoreCase(stringValue))
    {
      return Boolean.TRUE;
    }
    if ("false".equalsIgnoreCase(stringValue))
    {
      return Boolean.FALSE;
    }

    final char c = stringValue.charAt(0);
    if (('0' <= c & '9' >= c) || '-' == c || '.' == c || '+' == c)
    {
      /*
       * Try to get a number if it looks like a number. If converting to number fails, it should still be a value String
       * so any exception (or throwable) should be just ignored.
       */
      try
      {
        if (0 <= stringValue.indexOf('.') || 0 <= stringValue.indexOf('e') || 0 <= stringValue.indexOf('E'))
        {
          final Double doubleValue = Double.valueOf(stringValue);
          if (!doubleValue.isNaN() && !doubleValue.isInfinite())
          {
            return doubleValue;
          }
        }
        else
        {
          final Long longValue = Long.valueOf(stringValue);
          final int intValue = longValue.intValue();
          if (longValue.longValue() == intValue)
          {
            return Integer.valueOf(intValue);
          }
          return longValue;
        }
      }
      catch (final Throwable e)
      {
        /* It is to ignore any exception (or throwable). */
        System.out.println(format(
            "log from %s (%s.java:%s)\nException is thrown when converting stringValue [%s] into a Number object.\n"
                + "This should be just ignored.\n[paramInfo-> String stringValue: %s][Message from Throwable: %s]",
            JsonUtil.class, JsonUtil.class.getSimpleName(), Integer.valueOf(Thread.currentThread()
                .getStackTrace()[1].getLineNumber()), stringValue, stringValue, e.getMessage()));
      }
    }
    return stringValue;
  }

  public static Object convert(final Object value, final JsonObjectAndArrayCreator jsonObjectAndArrayCreator)
  {
    if (null == value)
    {
      return AbstractJsonObject.NULL_JSON_OBJECT;
    }
    /* @formatter:off */
		if (value instanceof JsonObject ||
				value instanceof JsonArray ||
				value instanceof String ||
				value instanceof Boolean ||
				value instanceof Integer ||
				value instanceof Long ||
				value instanceof Double ||
				value instanceof Enum ||
				value instanceof Character ||
				value instanceof Byte ||
				value instanceof Short ||
				value instanceof Float)
		{
			return value;
		}
		/* @formatter:on */

    try
    {
      if (value instanceof Collection)
      {
        return jsonObjectAndArrayCreator.newJsonArray((Collection<?>) value);
      }
      if (value instanceof Map)
      {
        @SuppressWarnings("unchecked")
        final Map<Object, Object> castedValue = (Map<Object, Object>) value;
        return jsonObjectAndArrayCreator.newJsonObject(castedValue);
      }
      if (NeoArrays.isArray(value))
      {
        return jsonObjectAndArrayCreator.newJsonArray(value);
      }
      final Class<? extends Object> theClass = value.getClass();
      final Package thePackage = theClass.getPackage();
      /* @formatter:off */
			final String packageName = null == thePackage ?
																		"" :
																		thePackage.getName();
			/* @formatter:on */

      /* @formatter:off */
			if (packageName.startsWith("java") ||
					packageName.startsWith("javax") ||
					null == theClass.getClassLoader())
			{
				return toStringOf(value);
			}
			/* @formatter:on */
      return jsonObjectAndArrayCreator.newJsonObject(value);
    }
    catch (final Throwable e)
    {
      /* @formatter:off */
      System.out.println(format(
          "log from %s (%s.java:%s)\nException is thrown when converting value [%s] into another object.\n"
              + "This should be just ignored.\n[paramInfo-> Object value: %s, JsonObjectAndArrayCreator jsonObjectAndArrayCreator: %s]"
              + "[Message from Throwable: %s]", JsonUtil.class, JsonUtil.class.getSimpleName(),
          Integer.valueOf(Thread
                           .currentThread()
                           .getStackTrace()[1]
                           .getLineNumber()),
                          value, value, jsonObjectAndArrayCreator,
                          e.getMessage()));
      /* @formatter:on */
      return null;
    }
  }

  public static Object convert(final Object value, final JsonObject jsonObject)
  {
    return convert(value, getJsonObjectAndArrayCreator(jsonObject));
  }

  public static Object convert(final Object value, final JsonArray jsonArray)
  {
    return convert(value, getJsonObjectAndArrayCreator(jsonArray));
  }

  public static <N, V> ImmutableJsonNameValuePair<V> newJsonNameValuePair(final String name, final V value)
  {
    return new ImmutableJsonNameValuePair<V>(name, value);
  }

  public static String toPrintable(final char c)
  {
    String result;
    switch (c)
    {
      case '\\':
        result = "\\";
        break;
      case '\b':
        result = "\\b";
        break;
      case '\f':
        result = "\\f";
        break;
      case '\n':
        result = "\\n";
        break;
      case '\r':
        result = "\\r";
        break;
      case '\t':
        result = "\\t";
        break;
      default:
        if (' ' > c || (('\u0080' <= c & '\u00a0' > c) | ('\u2000' <= c & '\u2100' > c)))
        {
          final String hex = "000" + Integer.toHexString(c);
          result = "\\u" + hex.substring(hex.length() - 4);
        }
        else
        {
          result = String.valueOf(c);
        }
        break;
    }
    return result;
  }
}
