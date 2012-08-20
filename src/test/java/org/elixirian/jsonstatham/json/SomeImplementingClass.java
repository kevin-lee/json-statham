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
public class SomeImplementingClass implements SomeInterface
{
  @JsonField(name = "name")
  private String name;

  @JsonField(name = "number")
  private int number;

  @JsonField(name = "email")
  private String email;

  public SomeImplementingClass(String name, int number, String email)
  {
    this.name = name;
    this.number = number;
    this.email = email;
  }

  /**
   * @return the name
   */
  @Override
  public String getName()
  {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  @Override
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return the number
   */
  @Override
  public int getNumber()
  {
    return number;
  }

  /**
   * @param number
   *          the number to set
   */
  @Override
  public void setNumber(int number)
  {
    this.number = number;
  }

  /**
   * @return the email
   */
  @Override
  public String getEmail()
  {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  @Override
  public void setEmail(String email)
  {
    this.email = email;
  }

  @Override
  public int hashCode()
  {
    return hash(hash(hash(name), number), email);
  }

  @Override
  public boolean equals(Object someImplementingClass)
  {
    if (identical(this, someImplementingClass))
    {
      return true;
    }
    final SomeImplementingClass that = castIfInstanceOf(SomeImplementingClass.class, someImplementingClass);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.name, that.getName()), 
								equal(this.number, that.getNumber()),
								equal(this.email, that.getEmail()));
		/* @formatter:on */
  }
}
