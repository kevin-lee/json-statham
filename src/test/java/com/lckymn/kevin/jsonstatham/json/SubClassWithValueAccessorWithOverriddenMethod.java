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
public class SubClassWithValueAccessorWithOverriddenMethod extends SuperClassWithValueAccessorWithOverriddenMethod
{
	@ValueAccessor(name = "emailAddress")
	@JsonField(name = "email")
	private String email;

	public SubClassWithValueAccessorWithOverriddenMethod(String name, int number, String email)
	{
		super(name, number);
		this.email = email;
	}

	@Override
	public String name()
	{
		return "My name is " + super.name();
	}

	@Override
	public String number()
	{
		return "The number is " + super.number();
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
	public boolean equals(Object subClassWithValueAccessorWithOverriddenMethod)
	{
		if (this == subClassWithValueAccessorWithOverriddenMethod)
		{
			return true;
		}
		if (!(subClassWithValueAccessorWithOverriddenMethod instanceof SubClassWithValueAccessorWithOverriddenMethod))
		{
			return false;
		}
		final SubClassWithValueAccessorWithOverriddenMethod that =
			(SubClassWithValueAccessorWithOverriddenMethod) subClassWithValueAccessorWithOverriddenMethod;
		return super.equals(subClassWithValueAccessorWithOverriddenMethod) && Objects.equals(this.email, that.email);
	}

	@Override
	public String toString()
	{
		return Objects.toStringBuilder(this)
				.addValue(super.toString())
				.add("email", email)
				.toString();
	}
}
