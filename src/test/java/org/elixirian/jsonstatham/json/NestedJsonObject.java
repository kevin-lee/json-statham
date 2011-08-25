/**
 * 
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

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
 * @version 0.01 (2009-11-28)
 */
@JsonObject
public final class NestedJsonObject
{
  @JsonField(name = "id")
  private Long primaryKey;

  @JsonField(name = "name")
  private String name;

  @JsonField(name = "address")
  private Address address;

  @JsonField
  private int intNumber;

  @JsonField
  private double doubleNumber;

  public NestedJsonObject()
  {
  }

  public NestedJsonObject(Long primaryKey, String name, Address address, int intNumber, double doubleNumber)
  {
    this.primaryKey = primaryKey;
    this.name = name;
    this.address = address;
    this.intNumber = intNumber;
    this.doubleNumber = doubleNumber;
  }

  /**
   * @return the primaryKey
   */
  public Long getPrimaryKey()
  {
    return primaryKey;
  }

  /**
   * @param primaryKey
   *          the primaryKey to set
   */
  public void setPrimaryKey(Long primaryKey)
  {
    this.primaryKey = primaryKey;
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

  public int getIntNumber()
  {
    return intNumber;
  }

  public void setIntNumber(int intNumber)
  {
    this.intNumber = intNumber;
  }

  public double getDoubleNumber()
  {
    return doubleNumber;
  }

  public void setDoubleNumber(double doubleNumber)
  {
    this.doubleNumber = doubleNumber;
  }

  @Override
  public int hashCode()
  {
    return hash(hash(hash(primaryKey, name, address), intNumber), doubleNumber);
  }

  @Override
  public boolean equals(Object nestedJsonObject)
  {
    if (identical(this, nestedJsonObject))
    {
      return true;
    }
    final NestedJsonObject that = castIfInstanceOf(NestedJsonObject.class, nestedJsonObject);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.primaryKey, that.getPrimaryKey()), 
								equal(this.name, that.getName()),
								equal(this.address, that.getAddress()),
								equal(this.intNumber, that.getIntNumber()),
								equal(this.doubleNumber, that.getDoubleNumber()));
		/* @formatter:on */
  }
}
