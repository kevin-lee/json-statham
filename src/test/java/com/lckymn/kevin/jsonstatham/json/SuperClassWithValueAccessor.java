/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-06)
 */
@JsonObject
public abstract class SuperClassWithValueAccessor
{
	@ValueAccessor(name = "name")
	@JsonField(name = "name")
	private String name;

	@ValueAccessor(name = "number")
	@JsonField(name = "number")
	private int number;

	public SuperClassWithValueAccessor(String name, int number)
	{
		this.name = name;
		this.number = number;
	}

	/**
	 * @return the name
	 */
	public String name()
	{
		return "My name is " + name;
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
	public String number()
	{
		return "The number is " + number;
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
	public boolean equals(Object superClassWithValueAccessor)
	{
		if (this == superClassWithValueAccessor)
		{
			return true;
		}
		if (!(superClassWithValueAccessor instanceof SuperClassWithValueAccessor))
		{
			return false;
		}
		final SuperClassWithValueAccessor that = (SuperClassWithValueAccessor) superClassWithValueAccessor;
		return Objects.equals(this.name, that.name) && Objects.equals(this.number, that.number);
	}
}
