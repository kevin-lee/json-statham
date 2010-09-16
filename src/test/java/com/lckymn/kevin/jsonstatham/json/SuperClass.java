/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-12-22)
 */
@JsonObject
public abstract class SuperClass
{
	@JsonField(name = "name")
	private String name;

	@JsonField(name = "number")
	private int number;

	public SuperClass(String name, int number)
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
	public boolean equals(Object superClass)
	{
		if (this == superClass)
		{
			return true;
		}
		if (!(superClass instanceof SuperClass))
		{
			return false;
		}
		final SuperClass that = (SuperClass) superClass;
		return Objects.equals(this.name, that.getName()) && Objects.equals(this.number, that.getNumber());
	}
}
