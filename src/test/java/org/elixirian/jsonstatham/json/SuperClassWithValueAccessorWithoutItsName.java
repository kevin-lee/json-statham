/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

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
public abstract class SuperClassWithValueAccessorWithoutItsName
{
  @ValueAccessor
  @JsonField(name = "name")
  private String name;

  @ValueAccessor
  @JsonField(name = "number")
  private int number;

  public SuperClassWithValueAccessorWithoutItsName(String name, int number)
  {
    this.name = name;
    this.number = number;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return "My name is " + name;
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
   * @return the number
   */
  public String getNumber()
  {
    return "The number is " + number;
  }

  /**
   * @param number
   *          the number to set
   */
  public void setNumber(int number)
  {
    this.number = number;
  }

  @Override
  public int hashCode()
  {
    return hash(hash(name), number);
  }

  @Override
  public boolean equals(Object superClassWithValueAccessorWithoutItsName)
  {
    if (identical(this, superClassWithValueAccessorWithoutItsName))
    {
      return true;
    }
    final SuperClassWithValueAccessorWithoutItsName that =
      castIfInstanceOf(SuperClassWithValueAccessorWithoutItsName.class, superClassWithValueAccessorWithoutItsName);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.name, that.name), 
								equal(this.number, that.number));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("name", name)
        .add("number", number)
        .toString();
  }
}
