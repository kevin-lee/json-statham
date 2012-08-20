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
 * @version 0.01 (2009-12-23)
 */
@Json
public class SecondSubClassWithOwnFields extends SubClass
{
  @JsonField(name = "address")
  private Address address;

  @JsonField(name = "comment")
  private String comment;

  public SecondSubClassWithOwnFields(String name, int number, String email, Address address, String comment)
  {
    super(name, number, email);
    this.address = address;
    this.comment = comment;
  }

  /**
   * @return the address
   */
  public Address getAddress()
  {
    return address;
  }

  /**
   * @param address
   *          the address to set
   */
  public void setAddress(Address address)
  {
    this.address = address;
  }

  /**
   * @return the comment
   */
  public String getComment()
  {
    return comment;
  }

  /**
   * @param comment
   *          the comment to set
   */
  public void setComment(String comment)
  {
    this.comment = comment;
  }

  @Override
  public int hashCode()
  {
    return hashObjects(hash(hash(getName()), getNumber()), getEmail(), address, comment);
  }

  @Override
  public boolean equals(Object secondSubClassWithOwnFields)
  {
    if (identical(this, secondSubClassWithOwnFields))
    {
      return true;
    }
    final SecondSubClassWithOwnFields that =
      castIfInstanceOf(SecondSubClassWithOwnFields.class, secondSubClassWithOwnFields);
    /* @formatter:off */
		return isNotNull(that) && 
						and(super.equals(secondSubClassWithOwnFields), 
								equal(this.address, that.getAddress()),
								equal(this.comment, that.getComment()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).value(super.toString())
        .newLine()
        .add("address", address)
        .add("comment", comment)
        .toString();
  }
}
