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

import static org.elixirian.kommonlee.util.MessageFormatter.*;

import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.json.JSONArray;
import org.json.JSONException;

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
 * @version 0.0.1 (2010-06-02)
 * @version 0.0.2 (2010-09-13)
 */
public final class OrgJsonJsonArray implements JsonArray
{
  private final JSONArray orgJsonArray;

  public OrgJsonJsonArray(final JSONArray orgJsonArray)
  {
    this.orgJsonArray = orgJsonArray;
  }

  @Override
  public <T> T get(final int index)
  {
    try
    {
      @SuppressWarnings("unchecked")
      final T value = (T) orgJsonArray.get(index);
      return value;
    }
    catch (final JSONException e)
    {
      throw new JsonStathamException(format("[input] int index: %s", Integer.valueOf(index)), e);
    }
  }

  @Override
  public <T> JsonArray put(final T value)
  {
    if (value instanceof JsonConvertible)
    {
      orgJsonArray.put(((JsonConvertible) value).getActualObject());
    }
    else
    {
      orgJsonArray.put(value);
    }
    return this;
  }

  @Override
  public int length()
  {
    return orgJsonArray.length();
  }

  @Override
  public Object getActualObject()
  {
    return orgJsonArray;
  }

  @Override
  public String toString()
  {
    return orgJsonArray.toString();
  }
}
