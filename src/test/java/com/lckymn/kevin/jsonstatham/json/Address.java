/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-11-28)
 */
@JsonObject
public class Address
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

	public Address()
	{
	}

	public Address(String street, String suburb, String city, String state, String postcode)
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
	 *            the street to set
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
	 *            the suburb to set
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
	 *            the city to set
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
	 *            the state to set
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
	 *            the postcode to set
	 */
	public void setPostcode(String postcode)
	{
		this.postcode = postcode;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(street, suburb, city, state, postcode);
	}

	@Override
	public boolean equals(Object address)
	{
		if (this == address)
		{
			return true;
		}
		if (!(address instanceof Address))
		{
			return false;
		}
		final Address that = (Address) address;
		return Objects.equals(this.street, that.getStreet()) && Objects.equals(this.suburb, that.getSuburb())
				&& Objects.equals(this.city, that.getCity()) && Objects.equals(this.state, that.getState())
				&& Objects.equals(this.postcode, that.getPostcode());
	}

	@Override
	public String toString()
	{
		return Objects.toStringBuilder(this)
				.add("street", street)
				.add("suburb", suburb)
				.add("city", city)
				.add("state", state)
				.add("postcode", postcode)
				.toString();
	}
}
