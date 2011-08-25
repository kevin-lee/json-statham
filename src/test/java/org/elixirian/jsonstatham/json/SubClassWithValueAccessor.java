/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

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
public class SubClassWithValueAccessor extends SuperClassWithValueAccessor
{
  @ValueAccessor(name = "emailAddress")
  @JsonField(name = "email")
  private String email;

  public SubClassWithValueAccessor(String name, int number, String email)
  {
    super(name, number);
    this.email = email;
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
    return hash(name(), number(), email);
  }

  @Override
  public boolean equals(Object subClassWithValueAccessor)
  {
    if (identical(this, subClassWithValueAccessor))
    {
      return true;
    }
    final SubClassWithValueAccessor that = castIfInstanceOf(SubClassWithValueAccessor.class, subClassWithValueAccessor);
    /* @formatter:off */
		return isNotNull(that) && 
						and(super.equals(subClassWithValueAccessor), 
								equal(this.email, that.email));
		/* @formatter:on */
  }
}
