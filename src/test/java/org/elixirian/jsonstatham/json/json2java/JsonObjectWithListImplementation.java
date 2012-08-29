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
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Objects.*;

import java.util.LinkedList;

import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.json.Address;


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
 * @version 0.0.1 (2010-10-27)
 */
@Json
public class JsonObjectWithListImplementation
{
  @JsonField(name = "address")
  private final LinkedList<Address> addressLinkedList;

  @JsonConstructor
  public JsonObjectWithListImplementation(LinkedList<Address> addressLinkedList)
  {
    this.addressLinkedList = addressLinkedList;
  }

  public LinkedList<Address> getAddressLinkedList()
  {
    return addressLinkedList;
  }

  @Override
  public int hashCode()
  {
    return hash(addressLinkedList);
  }

  @Override
  public boolean equals(Object JsonObjectWithSetImplementation)
  {
    if (identical(this, JsonObjectWithSetImplementation))
    {
      return true;
    }
    final JsonObjectWithListImplementation that =
      castIfInstanceOf(JsonObjectWithListImplementation.class, JsonObjectWithSetImplementation);
    return isNotNull(that) && equal(this.addressLinkedList, that.getAddressLinkedList());
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("addresses", addressLinkedList)
        .toString();
  }
}
