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
 * @version 0.01 (2009-11-28)
 */
@Json
public final class NestedJsonObject
{
  @JsonField(name = "id")
  private Long primaryKey;

  @JsonField(name = "name")
  private String name;

  @JsonField(name = "address")
  private Address address;

  @JsonField
  private int intNumber;

  @JsonField
  private double doubleNumber;

  public NestedJsonObject()
  {
  }

  public NestedJsonObject(Long primaryKey, String name, Address address, int intNumber, double doubleNumber)
  {
    this.primaryKey = primaryKey;
    this.name = name;
    this.address = address;
    this.intNumber = intNumber;
    this.doubleNumber = doubleNumber;
  }

  /**
   * @return the primaryKey
   */
  public Long getPrimaryKey()
  {
    return primaryKey;
  }

  /**
   * @param primaryKey
   *          the primaryKey to set
   */
  public void setPrimaryKey(Long primaryKey)
  {
    this.primaryKey = primaryKey;
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
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return the address
   */
  public Address getAddress()
  {
    return address;
  }

  /**
   * @param address
   *          the address to set
   */
  public void setAddress(Address address)
  {
    this.address = address;
  }

  public int getIntNumber()
  {
    return intNumber;
  }

  public void setIntNumber(int intNumber)
  {
    this.intNumber = intNumber;
  }

  public double getDoubleNumber()
  {
    return doubleNumber;
  }

  public void setDoubleNumber(double doubleNumber)
  {
    this.doubleNumber = doubleNumber;
  }

  @Override
  public int hashCode()
  {
    return hash(hash(hash(primaryKey, name, address), intNumber), doubleNumber);
  }

  @Override
  public boolean equals(Object nestedJsonObject)
  {
    if (identical(this, nestedJsonObject))
    {
      return true;
    }
    final NestedJsonObject that = castIfInstanceOf(NestedJsonObject.class, nestedJsonObject);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.primaryKey, that.getPrimaryKey()), 
								equal(this.name, that.getName()),
								equal(this.address, that.getAddress()),
								equal(this.intNumber, that.getIntNumber()),
								equal(this.doubleNumber, that.getDoubleNumber()));
		/* @formatter:on */
  }
}
