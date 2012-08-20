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
