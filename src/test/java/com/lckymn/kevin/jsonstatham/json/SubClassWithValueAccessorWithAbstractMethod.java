/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-06)
 */
@JsonObject
public class SubClassWithValueAccessorWithAbstractMethod extends SuperClassWithValueAccessorWithAbstractMethod
{
	@ValueAccessor(name = "emailAddress")
	@JsonField(name = "email")
	private String email;

	public SubClassWithValueAccessorWithAbstractMethod(String name, int number, String email)
	{
		super(name, number);
		this.email = email;
	}

	@Override
	public String name()
	{
		return "My name is nobody.";
	}
	
	@Override
	public String number()
	{
		return "The number is 100.";
	}

	/**
	 * @return the email
	 */
	public String emailAddress()
	{
		return "My email address is " + email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

}
