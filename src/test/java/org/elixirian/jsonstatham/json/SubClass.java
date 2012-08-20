/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;


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
 * @version 0.01 (2009-12-22)
 */
@Json
public class SubClass extends SuperClass
{
  @JsonField(name = "email")
  private String email;

  public SubClass(String name, int number, String email)
  {
    super(name, number);
    this.email = email;
  }

  /**
   * @return the email
   */
  public String getEmail()
  {
    return email;
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
    return hash(hash(hash(getName()), getNumber()), email);
  }

  @Override
  public boolean equals(Object subClass)
  {
    if (identical(this, subClass))
    {
      return true;
    }
    final SubClass that = castIfInstanceOf(SubClass.class, subClass);
    /* @formatter:off */
		return isNotNull(that) && 
						and(super.equals(subClass), 
								equal(this.email, that.getEmail()));
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
