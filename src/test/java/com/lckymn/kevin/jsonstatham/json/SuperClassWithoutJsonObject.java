/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-06)
 */
public abstract class SuperClassWithoutJsonObject
{
	@JsonField(name = "name")
	private String name;

	@JsonField(name = "number")
	private int number;

	public SuperClassWithoutJsonObject(String name, int number)
	{
		this.name = name;
		this.number = number;
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
	 * @return the number
	 */
	public int getNumber()
	{
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(int number)
	{
		this.number = number;
	}

	@SuppressWarnings("boxing")
	@Override
	public int hashCode()
	{
		return Objects.hash(name, number);
	}

	@SuppressWarnings("boxing")
	@Override
	public boolean equals(Object superClassWithoutJsonObject)
	{
		if (this == superClassWithoutJsonObject)
		{
			return true;
		}
		if (!(superClassWithoutJsonObject instanceof SuperClassWithoutJsonObject))
		{
			return false;
		}
		final SuperClassWithoutJsonObject that = (SuperClassWithoutJsonObject) superClassWithoutJsonObject;
		return Objects.equals(this.name, that.getName()) && Objects.equals(this.number, that.getNumber());
	}
}
