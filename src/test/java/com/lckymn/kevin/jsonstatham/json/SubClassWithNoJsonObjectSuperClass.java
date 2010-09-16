/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-06)
 */
@JsonObject
public class SubClassWithNoJsonObjectSuperClass extends SuperClassWithoutJsonObject
{
	@JsonField(name = "email")
	private String email;

	public SubClassWithNoJsonObjectSuperClass(String name, int number, String email)
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
	public boolean equals(Object subClassWithNoJsonObjectSuperClass)
	{
		if (this == subClassWithNoJsonObjectSuperClass)
		{
			return true;
		}
		if (!(subClassWithNoJsonObjectSuperClass instanceof SubClassWithNoJsonObjectSuperClass))
		{
			return false;
		}
		final SubClassWithNoJsonObjectSuperClass that =
			(SubClassWithNoJsonObjectSuperClass) subClassWithNoJsonObjectSuperClass;
		return super.equals(subClassWithNoJsonObjectSuperClass) && Objects.equals(this.email, that.getEmail());
	}
}
