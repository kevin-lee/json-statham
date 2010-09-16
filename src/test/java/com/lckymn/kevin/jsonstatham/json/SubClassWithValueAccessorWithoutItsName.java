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
public class SubClassWithValueAccessorWithoutItsName extends SuperClassWithValueAccessorWithoutItsName
{
	@ValueAccessor
	@JsonField(name = "email")
	private String email;

	public SubClassWithValueAccessorWithoutItsName(String name, int number, String email)
	{
		super(name, number);
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
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
	public boolean equals(Object subClassWithValueAccessorWithoutItsName)
	{
		if (this == subClassWithValueAccessorWithoutItsName)
		{
			return true;
		}
		if (!(subClassWithValueAccessorWithoutItsName instanceof SubClassWithValueAccessorWithoutItsName))
		{
			return false;
		}
		final SubClassWithValueAccessorWithoutItsName that =
			(SubClassWithValueAccessorWithoutItsName) subClassWithValueAccessorWithoutItsName;
		return super.equals(subClassWithValueAccessorWithoutItsName) && Objects.equals(this.email, that.email);
	}
}
