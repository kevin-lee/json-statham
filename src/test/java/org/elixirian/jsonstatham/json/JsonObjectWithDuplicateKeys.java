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

import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.Json;
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
 * @version 0.01 (2009 12 20)
 */
@Json
public final class JsonObjectWithDuplicateKeys
{
  @JsonField(name = "usernmae")
  private String username;

  @JsonField(name = "name")
  private String fullName;

  @JsonField(name = "name")
  private String name;

  @JsonField(name = "email")
  private String email;

  /**
   * @return the username
   */
  public String getUsername()
  {
    return username;
  }

  /**
   * @param username
   *          the username to set
   */
  public void setUsername(final String username)
  {
    this.username = username;
  }

  /**
   * @return the fullName
   */
  public String getFullName()
  {
    return fullName;
  }

  /**
   * @param fullName
   *          the fullName to set
   */
  public void setFullName(final String fullName)
  {
    this.fullName = fullName;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(final String name)
  {
    this.name = name;
  }

  /**
   * @return the email
   */
  public String getEmail()
  {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(final String email)
  {
    this.email = email;
  }

  @Override
  public int hashCode()
  {
    return hash(username, fullName, name, email);
  }

  @Override
  public boolean equals(final Object jsonObjectWithDuplicateKeys)
  {
    if (identical(this, jsonObjectWithDuplicateKeys))
    {
      return true;
    }
    final JsonObjectWithDuplicateKeys that =
      castIfInstanceOf(JsonObjectWithDuplicateKeys.class, jsonObjectWithDuplicateKeys);
    /* @formatter:off */
		return isNotNull(that)	&& 
						   (equal(this.username, that.getUsername()) && 
								equal(this.fullName, that.getFullName()) &&
								equal(this.name, that.getName()) &&
								equal(this.email, that.getEmail()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("username", username)
        .add("fullName", fullName)
        .add("name", name)
        .add("email", email)
        .toString();
  }
}
