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
public class SubClassWithValueAccessorWithoutItsName extends SuperClassWithValueAccessorWithoutItsName
{
  @ValueAccessor
  @JsonField(name = "email")
  private String email;

  public SubClassWithValueAccessorWithoutItsName(String name, int number, String email)
  {
    super(name, number);
    this.email = email;
  }

  /**
   * @return the email
   */
  public String getEmail()
  {
    return "My email address is " + email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(String email)
  {
    this.email = email;
  }

  @Override
  public int hashCode()
  {
    return hash(super.hashCode(), email);
  }

  @Override
  public boolean equals(Object subClassWithValueAccessorWithoutItsName)
  {
    if (identical(this, subClassWithValueAccessorWithoutItsName))
    {
      return true;
    }
    final SubClassWithValueAccessorWithoutItsName that =
      castIfInstanceOf(getClass(), subClassWithValueAccessorWithoutItsName);
    /* @formatter:off */
		return isNotNull(that) && 
						and(super.equals(subClassWithValueAccessorWithoutItsName), 
								equal(this.email, that.email));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).value(super.toString())
        .newLine()
        .add("email", email)
        .toString();
  }
}
