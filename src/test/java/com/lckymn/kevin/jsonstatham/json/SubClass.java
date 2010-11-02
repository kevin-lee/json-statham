/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import static com.lckymn.kevin.common.util.Conditions.*;
import static com.lckymn.kevin.common.util.Objects.*;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-12-22)
 */
@JsonObject
public class SubClass extends SuperClass
{
	@JsonField(name = "email")
	private String email;

	public SubClass(String name, int number, String email)
	{
		super(name, number);
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
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
		return hash(hash(hash(getName()), getNumber()), email);
	}

	@Override
	public boolean equals(Object subClass)
	{
		if (areIdentical(this, subClass))
		{
			return true;
		}
		final SubClass that = castIfInstanceOf(SubClass.class, subClass);
		/* @formatter:off */
		return isNotNull(that) && 
				and(super.equals(subClass), 
					equal(this.email, that.getEmail()));
		/* @formatter:on */
	}

	@Override
	public String toString()
	{
		return toStringBuilder(this).value(super.toString())
				.newLine()
				.add("email", email)
				.toString();
	}
}
