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
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Objects.*;

import java.util.Map;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;

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
 * @version 0.01 (2009-11-28)
 */
@Json
public class JsonObjectWithJsonConstructorWithSomeNotMatchingParams
{
  private final String name;

  @JsonField(name = "uri")
  private final String uri;

  @JsonField(name = "params")
  private final Map<String, String> parameterMap;

  @JsonConstructor
  public JsonObjectWithJsonConstructorWithSomeNotMatchingParams(final String name, final String uri,
      final Map<String, String> parameterMap)
  {
    this.name = name;
    this.uri = uri;
    this.parameterMap = parameterMap;
  }

  public String getName()
  {
    return name;
  }

  public String getUri()
  {
    return uri;
  }

  public Map<String, String> getParameterMap()
  {
    return parameterMap;
  }

  @Override
  public int hashCode()
  {
    return hash(name, uri, parameterMap);
  }

  @Override
  public boolean equals(final Object address)
  {
    if (identical(this, address))
    {
      return true;
    }
    final JsonObjectWithJsonConstructorWithSomeNotMatchingParams that =
      castIfInstanceOf(JsonObjectWithJsonConstructorWithSomeNotMatchingParams.class, address);
    /* @formatter:off */
		return isNotNull(that) && 
						   (equal(this.name, that.getName()) && 
								equal(this.uri, that.getUri()) &&
								equal(this.parameterMap, that.getParameterMap()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("name", name)
        .add("uri", uri)
        .add("parameterMap", parameterMap)
        .toString();
  }
}
