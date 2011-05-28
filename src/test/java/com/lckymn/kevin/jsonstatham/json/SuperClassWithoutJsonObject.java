/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import static org.elixirian.common.util.Conditional.*;
import static org.elixirian.common.util.Objects.*;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;

/**
 * <pre>
 *     ___  _____  __________  ___________ _____  ____
 *    /   \/    / /      \   \/   /_    _//     \/   /
 *   /        /  /    ___/\      / /   / /          /
 *  /        \  /    ___/  \    /_/   /_/          /
 * /____/\____\/_______/    \__//______/___/\_____/
 * </pre>
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-06)
 */
public abstract class SuperClassWithoutJsonObject
{
  @JsonField(name = "name")
  private String name;

  @JsonField(name = "number")
  private int number;

  public SuperClassWithoutJsonObject(String name, int number)
  {
    this.name = name;
    this.number = number;
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
   * @return the number
   */
  public int getNumber()
  {
    return number;
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
  public boolean equals(Object superClassWithoutJsonObject)
  {
    if (identical(this, superClassWithoutJsonObject))
    {
      return true;
    }
    final SuperClassWithoutJsonObject that =
      castIfInstanceOf(SuperClassWithoutJsonObject.class, superClassWithoutJsonObject);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.name, that.getName()), 
								equal(this.number, that.getNumber()));
		/* @formatter:on */
  }
}
