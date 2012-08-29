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

import java.util.Iterator;
import java.util.Set;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.ValueAccessor;


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
 * @version 0.0.1 (2010-03-06)
 */
@Json
public class JsonObjectPojoImpl implements JsonObjectPojo
{
  @ValueAccessor
  @JsonField
  private Long id;

  @ValueAccessor
  @JsonField
  private String name;

  @ValueAccessor(name = "getAddresses")
  @JsonField(name = "addresses")
  private Set<Address> addressSet;

  public JsonObjectPojoImpl(Long id, String name, Set<Address> addressSet)
  {
    this.id = id;
    this.name = name;
    this.addressSet = addressSet;
  }

  @Override
  public Long getId()
  {
    return id;
  }

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public Iterator<Address> getAddresses()
  {
    return addressSet.iterator();
  }

  @Override
  public Set<Address> getAddressSet()
  {
    return addressSet;
  }

  @Override
  public int hashCode()
  {
    return hash(id, name, addressSet);
  }

  @Override
  public boolean equals(Object jsonObjectPojoImpl)
  {
    if (identical(this, jsonObjectPojoImpl))
    {
      return true;
    }
    final JsonObjectPojoImpl that = castIfInstanceOf(JsonObjectPojoImpl.class, jsonObjectPojoImpl);
    /* @formatter:off */
		return isNotNull(that)	&& 
						and(equal(this.id, that.getId()), 
								equal(this.name, that.getName()),
								equal(this.addressSet, that.getAddressSet()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("id", id)
        .add("name", name)
        .add("addresses", addressSet)
        .toString();
  }
}
