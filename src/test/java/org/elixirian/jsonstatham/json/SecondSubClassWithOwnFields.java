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
 * @version 0.01 (2009-12-23)
 */
@Json
public class SecondSubClassWithOwnFields extends SubClass
{
  @JsonField(name = "address")
  private Address address;

  @JsonField(name = "comment")
  private String comment;

  public SecondSubClassWithOwnFields(final String name, final int number, final String email, final Address address,
      final String comment)
  {
    super(name, number, email);
    this.address = address;
    this.comment = comment;
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
  public void setAddress(final Address address)
  {
    this.address = address;
  }

  /**
   * @return the comment
   */
  public String getComment()
  {
    return comment;
  }

  /**
   * @param comment
   *          the comment to set
   */
  public void setComment(final String comment)
  {
    this.comment = comment;
  }

  @SuppressWarnings("boxing")
  @Override
  public int hashCode()
  {
    return hash(getName(), getNumber(), getEmail(), address, comment);
  }

  @Override
  public boolean equals(final Object secondSubClassWithOwnFields)
  {
    if (identical(this, secondSubClassWithOwnFields))
    {
      return true;
    }
    final SecondSubClassWithOwnFields that =
      castIfInstanceOf(SecondSubClassWithOwnFields.class, secondSubClassWithOwnFields);
    /* @formatter:off */
		return isNotNull(that) && 
						   (super.equals(secondSubClassWithOwnFields) && 
								equal(this.address, that.getAddress()) &&
								equal(this.comment, that.getComment()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).value(super.toString())
        .newLine()
        .add("address", address)
        .add("comment", comment)
        .toString();
  }
}
