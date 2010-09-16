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

	@Override
	public int hashCode()
	{
		return super.hashCode() + Objects.hash(email);
	}

	@Override
	public boolean equals(Object subClassWithValueAccessorWithAbstractMethod)
	{
		if (this == subClassWithValueAccessorWithAbstractMethod)
		{
			return true;
		}
		if (!(subClassWithValueAccessorWithAbstractMethod instanceof SubClassWithValueAccessorWithAbstractMethod))
		{
			return false;
		}
		final SubClassWithValueAccessorWithAbstractMethod that =
			(SubClassWithValueAccessorWithAbstractMethod) subClassWithValueAccessorWithAbstractMethod;
		return super.equals(subClassWithValueAccessorWithAbstractMethod) && Objects.equals(this.email, that.email);
	}
}
