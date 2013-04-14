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

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonConstructor;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.kommonlee.util.Objects;

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
public class AddressWithPrivateConstructorAndJsonConstructor
{
  @JsonField(name = "street")
  private String street;

  @JsonField(name = "suburb")
  private String suburb;

  @JsonField(name = "city")
  private String city;

  @JsonField(name = "state")
  private String state;

  @JsonField(name = "postcode")
  private String postcode;

  @JsonConstructor
  private AddressWithPrivateConstructorAndJsonConstructor(final String street, final String suburb, final String city, final String state,
      final String postcode)
  {
    this.street = street;
    this.suburb = suburb;
    this.city = city;
    this.state = state;
    this.postcode = postcode;
  }

  /**
   * @return the street
   */
  public String getStreet()
  {
    return street;
  }

  /**
   * @param street
   *          the street to set
   */
  public void setStreet(final String street)
  {
    this.street = street;
  }

  /**
   * @return the suburb
   */
  public String getSuburb()
  {
    return suburb;
  }

  /**
   * @param suburb
   *          the suburb to set
   */
  public void setSuburb(final String suburb)
  {
    this.suburb = suburb;
  }

  /**
   * @return the city
   */
  public String getCity()
  {
    return city;
  }

  /**
   * @param city
   *          the city to set
   */
  public void setCity(final String city)
  {
    this.city = city;
  }

  /**
   * @return the state
   */
  public String getState()
  {
    return state;
  }

  /**
   * @param state
   *          the state to set
   */
  public void setState(final String state)
  {
    this.state = state;
  }

  /**
   * @return the postcode
   */
  public String getPostcode()
  {
    return postcode;
  }

  /**
   * @param postcode
   *          the postcode to set
   */
  public void setPostcode(final String postcode)
  {
    this.postcode = postcode;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(street, suburb, city, state, postcode);
  }

  @Override
  public boolean equals(final Object address)
  {
    if (identical(this, address))
    {
      return true;
    }
    final AddressWithPrivateConstructorAndJsonConstructor that =
      castIfInstanceOf(AddressWithPrivateConstructorAndJsonConstructor.class, address);
    /* @formatter:off */
		return isNotNull(that) &&
						   (equal(this.street, that.getStreet()) && 
								equal(this.suburb, that.getSuburb()) &&
								equal(this.city, that.getCity()) &&
								equal(this.state, that.getState()) &&
								equal(this.postcode, that.getPostcode()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("street", street)
        .add("suburb", suburb)
        .add("city", city)
        .add("state", state)
        .add("postcode", postcode)
        .toString();
  }

  public static AddressWithPrivateConstructorAndJsonConstructor newAddressWithPrivateConstructorAndJsonConstructor(
      final String street, final String suburb, final String city, final String state, final String postcode)
  {
    return new AddressWithPrivateConstructorAndJsonConstructor(street, suburb, city, state, postcode);
  }
}
