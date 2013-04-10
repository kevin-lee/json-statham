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
 * @version 0.0.1 (2013-04-10)
 */
public class ImmutableJsonNameValuePair<V> implements JsonNameValuePair<String, V>
{
  private final String name;
  private final V value;

  public ImmutableJsonNameValuePair(final String name, final V value)
  {
    this.name = name;
    this.value = value;
  }

  @Override
  public String getValue1()
  {
    return name;
  }

  @Override
  public V getValue2()
  {
    return value;
  }

  @Override
  public int hashCode()
  {
    return hash(name, value);
  }

  @Override
  public boolean equals(final Object pair)
  {
    if (this == pair)
    {
      return true;
    }
    final JsonNameValuePair<?, ?> that = castIfInstanceOf(JsonNameValuePair.class, pair);
    /* @formatter:off */
    return null != that &&
        (equal(this.name, that.getValue1()) &&
         equal(this.value, that.getValue2()));
    /* @formatter:on */
  }

  @Override
  public String toString()
  {
    /* @formatter:off */
    return toStringBuilder(this)
            .add("name", name)
            .add("value", value)
          .toString();
    /* @formatter:on */
  }
}
