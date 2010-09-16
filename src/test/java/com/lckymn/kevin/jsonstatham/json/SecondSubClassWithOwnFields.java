/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-12-23)
 */
@JsonObject
public class SecondSubClassWithOwnFields extends SubClass
{
	@JsonField(name = "address")
	private Address address;

	@JsonField(name = "comment")
	private String comment;

	public SecondSubClassWithOwnFields(String name, int number, String email, Address address, String comment)
	{
		super(name, number, email);
		this.address = address;
		this.comment = comment;
	}

	/**
	 * @return the address
	 */
	public Address getAddress()
	{
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address)
	{
		this.address = address;
	}

	/**
	 * @return the comment
	 */
	public String getComment()
	{
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment)
	{
		this.comment = comment;
	}

	@SuppressWarnings("boxing")
	@Override
	public int hashCode()
	{
		return Objects.hash(getName(), getNumber(), getEmail(), address, comment);
	}

	@Override
	public boolean equals(Object secondSubClassWithOwnFields)
	{
		if (this == secondSubClassWithOwnFields)
		{
			return true;
		}
		if (!(secondSubClassWithOwnFields instanceof SecondSubClassWithOwnFields))
		{
			return false;
		}
		final SecondSubClassWithOwnFields that = (SecondSubClassWithOwnFields) secondSubClassWithOwnFields;
		return super.equals(secondSubClassWithOwnFields) && Objects.equals(this.address, that.getAddress())
				&& Objects.equals(this.comment, that.getComment());
	}
}
