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
		return Objects.hash(name(), number(), email);
	}

	@Override
	public boolean equals(Object subClassWithValueAccessor)
	{
		if (this == subClassWithValueAccessor)
		{
			return true;
		}
		if (!(subClassWithValueAccessor instanceof SubClassWithValueAccessor))
		{
			return false;
		}
		final SubClassWithValueAccessor that = (SubClassWithValueAccessor) subClassWithValueAccessor;
		return super.equals(subClassWithValueAccessor) && Objects.equals(this.email, that.email);
	}
}
