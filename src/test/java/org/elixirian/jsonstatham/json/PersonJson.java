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
 * @version 0.0.1 (2014-03-23)
 */
@Json
public class PersonJson
{
  @JsonField
  private final Long id;

  @JsonField
  private final String name;

  @JsonField
  private final String email;

  @JsonField
  private final boolean verified;

  @JsonField
  private final Address address;

  public PersonJson(final Long id, final String name, final String email, final boolean verified, final Address address)
  {
    this.id = id;
    this.name = name;
    this.email = email;
    this.verified = verified;
    this.address = address;
  }

  public Long getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public String getEmail()
  {
    return email;
  }

  public boolean isVerified()
  {
    return verified;
  }
  
  public Address getAddress()
  {
    return address;
  }

  @Override
  public int hashCode()
  {
    return hash(id, name, email, verified, address);
  }

  @Override
  public boolean equals(final Object personJson)
  {
    if (this == personJson)
    {
      return true;
    }
    final PersonJson that = castIfInstanceOf(PersonJson.class, personJson);
    /* @formatter:off */
    return null != that &&
            (equal(this.id, that.getId()) &&
             equal(this.name, that.getName()) &&
             equal(this.email, that.getEmail()) &&
             equal(this.verified, that.isVerified()) &&
             equal(this.address, that.getAddress()));
    /* @formatter:on */
  }

  @Override
  public String toString()
  {
    /* @formatter:off */
    return toStringBuilder(this)
            .add("id", id)
            .add("name", name)
            .add("email", email)
            .add("verified", verified)
            .add("address", address)
          .toString();
    /* @formatter:on */
  }
}
