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
@Json
public class SubClassWithNoJsonObjectSuperClass extends SuperClassWithoutJsonObject
{
  @JsonField(name = "email")
  private String email;

  public SubClassWithNoJsonObjectSuperClass(String name, int number, String email)
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
  public boolean equals(Object subClassWithNoJsonObjectSuperClass)
  {
    if (identical(this, subClassWithNoJsonObjectSuperClass))
    {
      return true;
    }
    final SubClassWithNoJsonObjectSuperClass that =
      castIfInstanceOf(SubClassWithNoJsonObjectSuperClass.class, subClassWithNoJsonObjectSuperClass);
    /* @formatter:off */
		return isNotNull(that) && 
						and(super.equals(subClassWithNoJsonObjectSuperClass), 
								equal(this.email, that.getEmail()));
		/* @formatter:on */
  }
}
