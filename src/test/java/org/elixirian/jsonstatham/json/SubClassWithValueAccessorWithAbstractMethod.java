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
public class SubClassWithValueAccessorWithAbstractMethod extends SuperClassWithValueAccessorWithAbstractMethod
{
  @ValueAccessor(name = "emailAddress")
  @JsonField(name = "email")
  private String email;

  public SubClassWithValueAccessorWithAbstractMethod(String name, int number, String email)
  {
    super(name, number);
    this.email = email;
  }

  @Override
  public String name()
  {
    return "My name is nobody.";
  }

  @Override
  public String number()
  {
    return "The number is 100.";
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
  public boolean equals(Object subClassWithValueAccessorWithAbstractMethod)
  {
    if (identical(this, subClassWithValueAccessorWithAbstractMethod))
    {
      return true;
    }
    final SubClassWithValueAccessorWithAbstractMethod that =
      castIfInstanceOf(SubClassWithValueAccessorWithAbstractMethod.class, subClassWithValueAccessorWithAbstractMethod);
    /* @formatter:off */
		return isNotNull(that) && 
						and(super.equals(subClassWithValueAccessorWithAbstractMethod), 
								equal(this.email, that.email));
		/* @formatter:on */
  }
}
