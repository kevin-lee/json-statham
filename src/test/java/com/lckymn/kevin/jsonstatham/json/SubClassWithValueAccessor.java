/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import static com.lckymn.kevin.common.util.Conditions.*;
import static com.lckymn.kevin.common.util.Objects.*;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-06)
 */
@JsonObject
public class SubClassWithValueAccessor extends SuperClassWithValueAccessor
{
	@ValueAccessor(name = "emailAddress")
	@JsonField(name = "email")
	private String email;

	public SubClassWithValueAccessor(String name, int number, String email)
	{
		super(name, number);
		this.email = email;
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
		return hash(name(), number(), email);
	}

	@Override
	public boolean equals(Object subClassWithValueAccessor)
	{
		if (areIdentical(this, subClassWithValueAccessor))
		{
			return true;
		}
		final SubClassWithValueAccessor that =
			castIfInstanceOf(SubClassWithValueAccessor.class, subClassWithValueAccessor);
		/* @formatter:off */
		return isNotNull(that) && 
				and(super.equals(subClassWithValueAccessor), 
					equal(this.email, that.email));
		/* @formatter:on */
	}
}
