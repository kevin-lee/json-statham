/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
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

	public NestedJsonObject()
	{
	}

	public NestedJsonObject(Long primaryKey, String name, Address address)
	{
		this.primaryKey = primaryKey;
		this.name = name;
		this.address = address;
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
	 *            the primaryKey to set
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
	 *            the name to set
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
	 *            the address to set
	 */
	public void setAddress(Address address)
	{
		this.address = address;
	}

}
