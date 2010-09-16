/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.common.util.Objects;
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

	@SuppressWarnings("boxing")
	@Override
	public int hashCode()
	{
		return Objects.hash(getName(), getNumber(), email);
	}

	@Override
	public boolean equals(Object subClass)
	{
		if (this == subClass)
		{
			return true;
		}
		if (!(subClass instanceof SubClass))
		{
			return false;
		}
		final SubClass that = (SubClass) subClass;
		return super.equals(subClass) && Objects.equals(this.email, that.getEmail());
	}
}
