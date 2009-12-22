/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-12-22)
 */
@JsonObject
public class SomeImplementingClass implements SomeInterface
{
	@JsonField(name = "name")
	private String name;

	@JsonField(name = "number")
	private int number;

	@JsonField(name = "email")
	private String email;

	public SomeImplementingClass(String name, int number, String email)
	{
		this.name = name;
		this.number = number;
		this.email = email;
	}

	/**
	 * @return the name
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the number
	 */
	@Override
	public int getNumber()
	{
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	@Override
	public void setNumber(int number)
	{
		this.number = number;
	}

	/**
	 * @return the email
	 */
	@Override
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	@Override
	public void setEmail(String email)
	{
		this.email = email;
	}

}
