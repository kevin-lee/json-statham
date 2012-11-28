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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonField;

/**
 * <pre>
 *     ___  _____                                              _____
 *    /   \/    / ______ __________________  ______ __ ______ /    /   ______  ______  
 *   /        / _/ __  // /  /   / /  /   /_/ __  // //     //    /   /  ___ \/  ___ \ 
 *  /        \ /  /_/ _/  _  _  /  _  _  //  /_/ _/   __   //    /___/  _____/  _____/
 * /____/\____\/_____//__//_//_/__//_//_/ /_____//___/ /__//________/\_____/ \_____/
 * </pre>
 * 
 * <pre>
 *     ___  _____                                _____
 *    /   \/    /_________  ___ ____ __ ______  /    /   ______  ______
 *   /        / /  ___ \  \/  //___// //     / /    /   /  ___ \/  ___ \
 *  /        \ /  _____/\    //   //   __   / /    /___/  _____/  _____/
 * /____/\____\\_____/   \__//___//___/ /__/ /________/\_____/ \_____/
 * </pre>
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2012-11-28)
 */
@Json
public class Product
{
  @JsonField
  private final Long id;

  @JsonField
  private final String name;

  @JsonField
  private final BigDecimal price;

  @JsonField
  private final BigInteger quantity;

  public Product(final Long id, final String name, final BigDecimal price, final BigInteger quantity)
  {
    this.id = id;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  public Long getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public BigDecimal getPrice()
  {
    return price;
  }

  public BigInteger getQuantity()
  {
    return quantity;
  }

  @Override
  public int hashCode()
  {
    return hash(id, name, price, quantity);
  }

  @Override
  public boolean equals(final Object product)
  {
    if (this == product)
    {
      return true;
    }
    final Product that = castIfInstanceOf(Product.class, product);
    /* @formatter:off */
    return null != that &&
            (equal(this.id,       that.getId()) &&
             equal(this.name,     that.getName()) &&
             equal(this.price,    that.getPrice()) &&
             equal(this.quantity, that.getQuantity()));
    /* @formatter:on */
  }

  @Override
  public String toString()
  {
    /* @formatter:off */
    return toStringBuilder(this)
            .add("id",       id)
            .add("name",     name)
            .add("price",    price)
            .add("quantity", quantity)
          .toString();
    /* @formatter:on */
  }
}
