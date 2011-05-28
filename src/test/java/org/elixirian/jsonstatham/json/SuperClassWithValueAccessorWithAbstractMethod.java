/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.common.util.Conditional.*;
import static org.elixirian.common.util.Objects.*;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.JsonObject;
import org.elixirian.jsonstatham.annotation.ValueAccessor;


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
@JsonObject
public abstract class SuperClassWithValueAccessorWithAbstractMethod
{
  @ValueAccessor(name = "name")
  @JsonField(name = "name")
  private String name;

  @ValueAccessor(name = "number")
  @JsonField(name = "number")
  private int number;

  public SuperClassWithValueAccessorWithAbstractMethod(String name, int number)
  {
    this.name = name;
    this.number = number;
  }

  /**
   * @return the name
   */
  public abstract String name();

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
  public abstract String number();

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
  public boolean equals(Object superClassWithValueAccessorWithAbstractMethod)
  {
    if (identical(this, superClassWithValueAccessorWithAbstractMethod))
    {
      return true;
    }
    final SuperClassWithValueAccessorWithAbstractMethod that =
      castIfInstanceOf(SuperClassWithValueAccessorWithAbstractMethod.class,
          superClassWithValueAccessorWithAbstractMethod);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.name, that.name), 
								equal(this.number, that.number));
		/* @formatter:on */
  }
}
