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
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.jsonstatham.core.util.JsonUtil.*;
import static org.elixirian.kommonlee.util.MessageFormatter.*;
import static org.elixirian.kommonlee.util.Objects.*;
import static org.elixirian.kommonlee.util.collect.Maps.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.elixirian.jsonstatham.core.util.JsonUtil;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.io.CharAndStringWritable;
import org.elixirian.kommonlee.io.CharAndStringWritableToStringBuilder;
import org.elixirian.kommonlee.io.util.IoUtil;
import org.elixirian.kommonlee.util.NeoArrays;
import org.elixirian.kommonlee.util.collect.Maps;

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
public abstract class AbstractJsonObject extends AbstractJsonConvertible implements JsonObject
{
  private static final class NullJsonObject extends AbstractJsonConvertible implements JsonObject
  {
    private NullJsonObject()
    {
    }

    @Override
    public boolean isJsonObject()
    {
      return true;
    }

    @Override
    public boolean isJsonArray()
    {
      return false;
    }

    @Override
    public <T> JsonObject put(final String name, final T value) throws JsonStathamException
    {
      throw JsonStathamException.newJsonStathamException("The put method in NullJsonObject cannot used.\n"
          + "[input] String name: %s, Object value: %s", name, value);
    }

    @Override
    public String[] getNames()
    {
      return EMPTY_NAMES;
    }

    @Override
    public Object getActualObject()
    {
      return this;
    }

    /* @formatter:off */
    @Override
    public boolean containsName(@SuppressWarnings("unused") final String name) { return false; }
    /* @formatter:on */

    @Override
    public <T> T get(final String name)
    {
      throw JsonStathamException.newJsonStathamException(
          "The name method in NullJsonObject cannot used.\n[input] String name: %s", name);
    }

    @Override
    public int fieldLength()
    {
      return 0;
    }

    @Override
    public boolean isNull()
    {
      return true;
    }

    @Override
    public int hashCode()
    {
      return 0;
    }

    @Override
    public boolean equals(final Object jsonObject)
    {
      if (this == jsonObject)
      {
        return true;
      }
      final NullJsonObject that = castIfInstanceOf(NullJsonObject.class, jsonObject);
      return null != that;
    }

    @Override
    public String toString()
    {
      return "null";
    }

    @Override
    public Map<String, Object> copyToMap()
    {
      return EMPTY_MAP;
    }

    @Override
    public boolean isEmpty()
    {
      return true;
    }

    @Override
    public boolean isNotEmpty()
    {
      return false;
    }

    @Override
    public void write(final CharAndStringWritable charAndStringWritable)
    {
      charAndStringWritable.write("null");
    }
  }

  private static final String[] EMPTY_NAMES = NeoArrays.EMPTY_STRING_ARRAY;
  private static final Map<String, Object> EMPTY_MAP = Collections.emptyMap();

  private final Map<String, Object> jsonFieldMap;
  private final boolean ordered;

  public static final JsonObject NULL_JSON_OBJECT = new NullJsonObject();

  protected AbstractJsonObject(final JsonScanner jsonScanner, final boolean ordered)
  {
    this(ordered);
    map(jsonScanner, this.jsonFieldMap);
  }

  private void map(final JsonScanner jsonScanner, final Map<String, Object> jsonFieldMap) throws JsonStathamException
  {
    char c = jsonScanner.nextNonWhiteSpaceChar();
    if ('{' != c)
    {
      throw JsonStathamException.newJsonStathamException("Invalid JSON String found in the JsonScanner. "
          + "It must start with { but does not.\n[char found:[int char: %s][char found: '%s']]%s", Integer.valueOf(c),
          Character.valueOf(c), jsonScanner.getPreviousCharInfo());
    }

    String key = null;
    while (true)
    {
      c = jsonScanner.nextNonWhiteSpaceChar();
      switch (c)
      {
        case 0:
          throw JsonStathamException.newJsonStathamException("Invalid JSON String found in the JsonScanner. "
              + "It must end with } but does not.\n[char found:[int char: %s][char found: '%s']]%s",
              Integer.valueOf(c), Character.valueOf(c), jsonScanner.getPreviousCharInfo());
        case '}':
          /* an empty JSON object "{}" */
          return;
        default:
          jsonScanner.backToPrevious();
          key = toStringOf(jsonScanner.nextValue());
      }

      c = jsonScanner.nextNonWhiteSpaceChar();
      if ('=' == c)
      {
        if ('>' != jsonScanner.nextChar())
        {
          jsonScanner.backToPrevious();
        }
      }
      else if (':' != c)
      {
        throw JsonStathamException.newJsonStathamException(
            "The separator char that is : is expected after the key yet not found.\n{char found:[int char: %s][char found: '%s']}%s",
            Integer.valueOf(c), Character.valueOf(c), jsonScanner.getPreviousCharInfo());
      }

      if (jsonFieldMap.containsKey(key))
      {
        throw JsonStathamException.newJsonStathamException(
            "Duplicate key found!\nThe key [%s] already exists in this JSON object", key);
      }
      final Object value = jsonScanner.nextValue();
      if (null != key && null != value)
      {
        jsonFieldMap.put(key, value);
      }

      c = jsonScanner.nextNonWhiteSpaceChar();
      switch (c)
      {
        case ',':
        case ';':
          if ('}' == jsonScanner.nextNonWhiteSpaceChar())
          {
            return;
          }
          jsonScanner.backToPrevious();
          break;
        case '}':
          return;
        default:
          throw JsonStathamException.newJsonStathamException(
              ", (line delimiter) or } is expected but neither is found.\n[char found:[int char: %s][char found: '%s']]%s",
              Integer.valueOf(c), Character.valueOf(c), jsonScanner.getPreviousCharInfo());
      }
    }
  }

  protected AbstractJsonObject(final boolean ordered)
  {
    this.ordered = ordered;
    this.jsonFieldMap = this.ordered ? Maps.<String, Object> newLinkedHashMap() : Maps.<String, Object> newHashMap();
  }

  protected AbstractJsonObject(final Map<?, Object> jsonFieldMap, final boolean ordered)
  {
    this(ordered);
    for (final Entry<?, Object> entry : jsonFieldMap.entrySet())
    {
      this.jsonFieldMap.put(toStringOf(entry.getKey()), JsonUtil.convert(entry.getValue(), this));
    }
  }

  protected AbstractJsonObject(final Object javaBean, final boolean ordered)
  {
    this(ordered);
    final Class<?> theClass = javaBean.getClass();
    final boolean includeSuperClass = theClass.getClassLoader() != null;

    final Method[] methods = includeSuperClass ? theClass.getMethods() : theClass.getDeclaredMethods();
    for (int i = 0; i < methods.length; i++)
    {
      try
      {
        final Method method = methods[i];
        if (Modifier.isPublic(method.getModifiers()))
        {
          final String name = method.getName();
          String key = "";
          if (name.startsWith("get"))
          {
            /* @formatter:off */
						key = ("getClass".equals(name) || "getDeclaringClass".equals(name)) ?
										"" :
										name.substring(3);
						/* @formatter:on */
          }
          else if (name.startsWith("is"))
          {
            key = name.substring(2);
          }
          final int length = key.length();
          if (0 < length && Character.isUpperCase(key.charAt(0)) && 0 == method.getParameterTypes().length)
          {
            if (length == 1)
            {
              key = key.toLowerCase();
            }
            else
            {
              final boolean isSecondNotUpperCase = !Character.isUpperCase(key.charAt(1));

              if (2 == length)
              {
                if (isSecondNotUpperCase)
                {
                  key = key.substring(0, 1)
                      .toLowerCase() + key.substring(1);
                }
              }
              else
              {
                key = key.substring(0, 1)
                    .toLowerCase() + key.substring(1);
              }
            }
            final Object result = method.invoke(javaBean, (Object[]) null);
            if (null != result)
            {
              this.jsonFieldMap.put(key, JsonUtil.convert(result, this));
            }
          }
        }
      }
      catch (final Throwable e)
      {
        /* Any exception (or throwable) should be ignored. */
        System.out.println(format(
            "log from %s (%s.java:%s)\nException is thrown when extracting data from JavaBean [%s].\n"
                + "This should be just ignored.\n"
                + "[paramInfo-> final Object javaBean: %s, final boolean ordered: %s][Message from Throwable: %s]",
            JsonUtil.class, JsonUtil.class.getSimpleName(), Integer.valueOf(Thread.currentThread()
                .getStackTrace()[1].getLineNumber()), javaBean, javaBean, Boolean.valueOf(ordered), e.getMessage()));
      }
    }
  }

  @Override
  public boolean isJsonObject()
  {
    return true;
  }

  @Override
  public boolean isJsonArray()
  {
    return false;
  }

  @Override
  public String[] getNames()
  {
    if (0 == jsonFieldMap.size())
      return EMPTY_NAMES;

    final Set<String> keySet = jsonFieldMap.keySet();
    return keySet.toArray(new String[keySet.size()]);
  }

  @Override
  public int fieldLength()
  {
    return jsonFieldMap.size();
  }

  @Override
  public boolean containsName(final String name)
  {
    return jsonFieldMap.containsKey(name);
  }

  @Override
  public <T> T get(final String name)
  {
    @SuppressWarnings("unchecked")
    final T value = (T) jsonFieldMap.get(name);
    return value;
  }

  @Override
  public <T> JsonObject put(final String name, final T value) throws JsonStathamException
  {
    put0(name, value);
    return this;
  }

  private <T> void put0(final String name, final T value)
  {
    if (null == name)
      throw new JsonStathamException(format("The name must not be null.\n[input] String name: %s, Object value: %s]",
          name, value));

    validate(value);
    jsonFieldMap.put(name, value);
  }

  @Override
  public Object getActualObject()
  {
    return this;
  }

  @Override
  public boolean isNull()
  {
    return false;
  }

  protected Map<String, Object> getJsonFieldMap()
  {
    return jsonFieldMap;
  }

  public boolean isOrdered()
  {
    return ordered;
  }

  public boolean isNotOrdered()
  {
    return !ordered;
  }

  @Override
  public int hashCode()
  {
    return hashObjectWithSeed(hash(ordered), jsonFieldMap);
  }

  @Override
  public boolean equals(final Object jsonObject)
  {
    if (this == jsonObject)
    {
      return true;
    }
    final AbstractJsonObject that = castIfInstanceOf(AbstractJsonObject.class, jsonObject);
    /* @formatter:off */
		return null != that &&
						(equal(this.ordered, that.isOrdered()) &&
						 equal(this.jsonFieldMap, that.getJsonFieldMap()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    final StringBuilder stringBuilder = new StringBuilder();
    final CharAndStringWritableToStringBuilder charAndStringWritable =
      new CharAndStringWritableToStringBuilder(stringBuilder);
    write(charAndStringWritable);
    IoUtil.closeQuietly(charAndStringWritable);
    return stringBuilder.toString();
  }

  @Override
  public void write(final CharAndStringWritable charAndStringWritable)
  {
    charAndStringWritable.write("{");
    final Iterator<Entry<String, Object>> iterator = jsonFieldMap.entrySet()
        .iterator();

    if (iterator.hasNext())
    {
      final Entry<String, Object> field = iterator.next();
      /* name */
      doubleQuote(charAndStringWritable, field.getKey());
      charAndStringWritable.write(':');
      /* value */
      JsonUtil.writeValue(charAndStringWritable, field.getValue(), this);
    }

    while (iterator.hasNext())
    {
      final Entry<String, Object> field = iterator.next();
      charAndStringWritable.write(',');
      /* name */
      doubleQuote(charAndStringWritable, field.getKey());
      charAndStringWritable.write(':');
      /* value */
      JsonUtil.writeValue(charAndStringWritable, field.getValue(), this);
    }
    charAndStringWritable.write('}');
  }

  @Override
  public Map<String, Object> copyToMap()
  {
    if (ordered)
    {
      return newLinkedHashMap(jsonFieldMap);
    }
    return newHashMap(jsonFieldMap);
  }

  @Override
  public boolean isEmpty()
  {
    return isEmpty0();
  }

  private boolean isEmpty0()
  {
    return jsonFieldMap.isEmpty();
  }

  @Override
  public boolean isNotEmpty()
  {
    return !isEmpty0();
  }
}
