/**
 * 
 */
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Conditional.*;
import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.JsonConstructor;
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
 * @version 0.01 (2009-11-28)
 */
@Json
public class AddressWithJsonConstructor
{
  @JsonField(name = "street")
  private String street;

  @JsonField(name = "suburb")
  private String suburb;

  @JsonField(name = "city")
  private String city;

  @JsonField(name = "state")
  private String state;

  @JsonField(name = "postcode")
  private String postcode;

  @JsonConstructor
  public AddressWithJsonConstructor(String street, String suburb, String city, String state, String postcode)
  {
    this.street = street;
    this.suburb = suburb;
    this.city = city;
    this.state = state;
    this.postcode = postcode;
  }

  /**
   * @return the street
   */
  public String getStreet()
  {
    return street;
  }

  /**
   * @param street
   *          the street to set
   */
  public void setStreet(String street)
  {
    this.street = street;
  }

  /**
   * @return the suburb
   */
  public String getSuburb()
  {
    return suburb;
  }

  /**
   * @param suburb
   *          the suburb to set
   */
  public void setSuburb(String suburb)
  {
    this.suburb = suburb;
  }

  /**
   * @return the city
   */
  public String getCity()
  {
    return city;
  }

  /**
   * @param city
   *          the city to set
   */
  public void setCity(String city)
  {
    this.city = city;
  }

  /**
   * @return the state
   */
  public String getState()
  {
    return state;
  }

  /**
   * @param state
   *          the state to set
   */
  public void setState(String state)
  {
    this.state = state;
  }

  /**
   * @return the postcode
   */
  public String getPostcode()
  {
    return postcode;
  }

  /**
   * @param postcode
   *          the postcode to set
   */
  public void setPostcode(String postcode)
  {
    this.postcode = postcode;
  }

  @Override
  public int hashCode()
  {
    return hash(street, suburb, city, state, postcode);
  }

  @Override
  public boolean equals(Object address)
  {
    if (identical(this, address))
    {
      return true;
    }
    final AddressWithJsonConstructor that = castIfInstanceOf(AddressWithJsonConstructor.class, address);
    /* @formatter:off */
		return isNotNull(that) && 
						and(equal(this.street, that.getStreet()), 
								equal(this.suburb, that.getSuburb()),
								equal(this.city, that.getCity()), 
								equal(this.state, that.getState()),
								equal(this.postcode, that.getPostcode()));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("street", street)
        .add("suburb", suburb)
        .add("city", city)
        .add("state", state)
        .add("postcode", postcode)
        .toString();
  }
}
