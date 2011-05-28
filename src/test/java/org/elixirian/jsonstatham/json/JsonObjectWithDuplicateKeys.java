/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.common.util.Conditional.*;
import static org.elixirian.common.util.Objects.*;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.JsonObject;


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
 * @version 0.01 (2009 12 20)
 */
@JsonObject
public final class JsonObjectWithDuplicateKeys
{
  @JsonField(name = "usernmae")
  private String username;

  @JsonField(name = "name")
  private String fullName;

  @JsonField(name = "name")
  private String name;

  @JsonField(name = "email")
  private String email;

  /**
   * @return the username
   */
  public String getUsername()
  {
    return username;
  }

  /**
   * @param username
   *          the username to set
   */
  public void setUsername(String username)
  {
    this.username = username;
  }

  /**
   * @return the fullName
   */
  public String getFullName()
  {
    return fullName;
  }

  /**
   * @param fullName
   *          the fullName to set
   */
  public void setFullName(String fullName)
  {
    this.fullName = fullName;
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
    return hash(username, fullName, name, email);
  }

  @Override
  public boolean equals(Object jsonObjectWithDuplicateKeys)
  {
    if (identical(this, jsonObjectWithDuplicateKeys))
    {
      return true;
    }
    final JsonObjectWithDuplicateKeys that =
      castIfInstanceOf(JsonObjectWithDuplicateKeys.class, jsonObjectWithDuplicateKeys);
    /* @formatter:off */
		return isNotNull(that)	&& 
						and(equal(this.username, that.getUsername()), 
								equal(this.fullName, that.getFullName()),
								equal(this.name, that.getName()), 
								equal(this.email, that.getEmail()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("username", username)
        .add("fullName", fullName)
        .add("name", name)
        .add("email", email)
        .toString();
  }
}
