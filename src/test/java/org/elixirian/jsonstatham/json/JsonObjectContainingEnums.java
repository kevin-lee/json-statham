/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original KommonLee project is owned by Lee, Seong Hyun (Kevin).
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
 * @version 0.0.1 (2010-05-10)
 */
@Json
public final class JsonObjectContainingEnums
{
  public static enum Role
  {
    SYSTEM_ADMIN, MANAGER, MEMBER
  }

  public static enum Access
  {
    WIKI("Access to wiki"), BLOG("Access to blog"), TWITTER("Access to twitter"), EMAIL("Access to email");

    private final String value;

    private Access(String value)
    {
      this.value = value;
    }

    public String value()
    {
      return value;
    }
  }

  @JsonField
  private final String name;

  @JsonField
  private final int number;

  @JsonField
  private final boolean passed;

  @JsonField
  private final Role role;

  @JsonField
  private final Access[] access;

  public JsonObjectContainingEnums(String name, int number, boolean passed, Role role, Access... access)
  {
    this.name = name;
    this.number = number;
    this.passed = passed;
    this.role = role;
    this.access = access;
  }

  @Override
  public int hashCode()
  {
    return hash(hash(hash(hash(hash(name), number), passed), role), access);
  }

  @Override
  public boolean equals(Object jsonObjectContainingEnums)
  {
    if (identical(this, jsonObjectContainingEnums))
    {
      return true;
    }
    final JsonObjectContainingEnums that = castIfInstanceOf(JsonObjectContainingEnums.class, jsonObjectContainingEnums);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.name, that.name), 
								equal(this.number, that.number), 
								equal(this.passed, that.passed), 
								equal(this.role, that.role), 
								deepEqual(this.access, that.access));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("name", name)
        .add("number", number)
        .add("passed", passed)
        .add("role", role)
        .add("access", access)
        .toString();
  }

}
