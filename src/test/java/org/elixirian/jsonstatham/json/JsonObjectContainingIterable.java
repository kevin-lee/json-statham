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
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import java.util.Iterator;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;


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
 * @version 0.0.1 (2010-02-03)
 */
@Json
public class JsonObjectContainingIterable
{
  @JsonField(name = "name")
  private final String name;

  @JsonField(name = "valueIterable")
  private final Iterable<String> iterable;

  public JsonObjectContainingIterable(String name, Iterable<String> iterable)
  {
    this.name = name;
    this.iterable = iterable;
  }

  @Override
  public int hashCode()
  {
    return hash(name, iterable);
  }

  @Override
  public boolean equals(final Object jsonObjectContainingIterable)
  {
    if (identical(this, jsonObjectContainingIterable))
    {
      return true;
    }
    final JsonObjectContainingIterable that =
      castIfInstanceOf(JsonObjectContainingIterable.class, jsonObjectContainingIterable);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.name, that.name), 
								iterableEquals(this.iterable, that.iterable));
		/* @formatter:on */
  }

  private boolean iterableEquals(final Iterable<String> iterable1, final Iterable<String> iterable2)
  {
    final Iterator<String> iterator1 = iterable1.iterator();
    final Iterator<String> iterator2 = iterable2.iterator();
    if (iterator1.hasNext() != iterator2.hasNext())
    {
      return false;
    }
    int count1 = 0;
    int count2 = 0;
    while (iterator1.hasNext())
    {
      count1++;
      final String value1 = iterator1.next();
      if (iterator2.hasNext())
      {
        count2++;
        final String value2 = iterator2.next();
        if (value1 != value2)
        {
          if (null != value1 && !value1.equals(value2))
          {
            return false;
          }
        }
      }
    }
    return count1 == count2;
  }
}
