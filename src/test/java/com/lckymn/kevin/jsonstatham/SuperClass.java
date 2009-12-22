/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-12-22)
 */
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

}
