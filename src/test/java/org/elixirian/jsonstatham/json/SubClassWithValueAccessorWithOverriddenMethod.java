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
public class SubClassWithValueAccessorWithOverriddenMethod extends SuperClassWithValueAccessorWithOverriddenMethod
{
  @ValueAccessor(name = "emailAddress")
  @JsonField(name = "email")
  private String email;

  public SubClassWithValueAccessorWithOverriddenMethod(String name, int number, String email)
  {
    super(name, number);
    this.email = email;
  }

  @Override
  public String name()
  {
    return "My name is " + super.name();
  }

  @Override
  public String number()
  {
    return "The number is " + super.number();
  }

  /**
   * @return the email
   */
  public String emailAddress()
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
  public boolean equals(Object subClassWithValueAccessorWithOverriddenMethod)
  {
    if (identical(this, subClassWithValueAccessorWithOverriddenMethod))
    {
      return true;
    }
    final SubClassWithValueAccessorWithOverriddenMethod that =
      castIfInstanceOf(SubClassWithValueAccessorWithOverriddenMethod.class,
          subClassWithValueAccessorWithOverriddenMethod);
    /* @formatter:off */
		return isNotNull(that) && 
						and(super.equals(subClassWithValueAccessorWithOverriddenMethod), 
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
