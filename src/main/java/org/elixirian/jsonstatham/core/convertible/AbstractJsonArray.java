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

import static org.elixirian.kommonlee.util.Objects.*;
import static org.elixirian.kommonlee.util.collect.Lists.*;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.elixirian.jsonstatham.core.util.JsonUtil;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.io.CharAndStringWritable;
import org.elixirian.kommonlee.io.CharAndStringWritableToStringBuilder;
import org.elixirian.kommonlee.io.util.IoUtil;
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
public abstract class AbstractJsonArray extends AbstractJsonConvertible implements JsonArray
{
  private final List<Object> list;

  protected AbstractJsonArray()
  {
    this.list = newArrayList();
  }

  protected AbstractJsonArray(final JsonScanner jsonScanner)
  {
    this();
    map(jsonScanner, this.list);
  }

  private void map(final JsonScanner jsonScanner, final List<Object> list)
  {
    mustNotBeNull(list);
    char c = jsonScanner.nextNonWhiteSpaceChar();
    if ('[' != c)
    {
      throw JsonStathamException.newJsonStathamException("Invalid JSON Array found in the JsonScanner. "
          + "It must start with [ but does not.\n[char found:[int char: %s][char found: '%s']]%s", Integer.valueOf(c),
          Character.valueOf(c), jsonScanner.getPreviousCharInfo());
    }
    c = jsonScanner.nextNonWhiteSpaceChar();
    if (']' != c)
    {
      while (true)
      {
        if (',' == c)
        {
          list.add(AbstractJsonObject.NULL_JSON_OBJECT);
        }
        else
        {
          jsonScanner.backToPrevious();
          list.add(jsonScanner.nextValue());
          c = jsonScanner.nextNonWhiteSpaceChar();
        }
        switch (c)
        {
          case ',':
          case ';':
            if (']' == jsonScanner.nextNonWhiteSpaceChar())
            {
              return;
            }
            jsonScanner.backToPrevious();
            break;
          case ']':
            return;
          default:
            throw JsonStathamException.newJsonStathamException(
                ", (line delimiter) or ] is expected but neither is found.\n[char found:[int char: %s][char found: '%s']]%s",
                Integer.valueOf(c), Character.valueOf(c), jsonScanner.getPreviousCharInfo());
        }
        c = jsonScanner.nextNonWhiteSpaceChar();
      }
    }
  }

  protected AbstractJsonArray(final Collection<?> elements)
  {
    this(elements.toArray());
  }

  protected AbstractJsonArray(final Object[] elements)
  {
    this.list = newArrayList();
    for (final Object value : elements)
    {
      this.list.add(JsonUtil.convert(value, this));
    }
  }

  @Override
  public boolean isJsonObject()
  {
    return false;
  }

  @Override
  public boolean isJsonArray()
  {
    return true;
  }

  @Override
  public <T> T get(final int index)
  {
    @SuppressWarnings("unchecked")
    final T value = (T) list.get(index);
    return value;
  }

  @Override
  public <T> JsonArray put(final T value)
  {
    list.add(value);
    return this;
  }

  @Override
  public int length()
  {
    return list.size();
  }

  protected List<Object> getList()
  {
    return list;
  }

  @Override
  public int hashCode()
  {
    return hash(list);
  }

  @Override
  public boolean equals(final Object jsonArray)
  {
    if (this == jsonArray)
    {
      return true;
    }
    final AbstractJsonArray that = castIfInstanceOf(AbstractJsonArray.class, jsonArray);
    /* @formatter:off */
    return null != that &&
            (equal(this.list,
                   that.getList()));
    /* @formatter:on */
  }

  @Override
  public void write(final CharAndStringWritable charAndStringWritable)
  {
    charAndStringWritable.write("[");
    final Iterator<Object> iterator = list.iterator();

    if (iterator.hasNext())
    {
      JsonUtil.writeValue(charAndStringWritable, iterator.next(), this);
    }

    while (iterator.hasNext())
    {
      charAndStringWritable.write(',');
      JsonUtil.writeValue(charAndStringWritable, iterator.next(), this);
    }
    charAndStringWritable.write(']');
  }

  @Override
  public String toString()
  {
    final StringBuilder stringBuilder = new StringBuilder();
    final CharAndStringWritable charAndStringWritable = new CharAndStringWritableToStringBuilder(stringBuilder);
    write(charAndStringWritable);
    IoUtil.closeQuietly(charAndStringWritable);
    return stringBuilder.toString();
  }

  public static Object[] convertToArrayIfArray(final Object possibleArray)
  {
    if (NeoArrays.isArray(possibleArray))
    {
      final int length = Array.getLength(possibleArray);
      final Object[] elements = new Object[length];
      for (int i = 0; i < length; i++)
      {
        elements[i] = Array.get(possibleArray, i);
      }
      return elements;
    }
    return null;
  }

  @Override
  public Iterator<Object> iterator()
  {
    return list.iterator();
  }

  @Override
  public List<Object> copyToList()
  {
    return newArrayList(list);
  }

  @Override
  public boolean isEmpty()
  {
    return isEmpty0();
  }

  private boolean isEmpty0()
  {
    return list.isEmpty();
  }

  @Override
  public boolean isNotEmpty()
  {
    return !isEmpty0();
  }
}
