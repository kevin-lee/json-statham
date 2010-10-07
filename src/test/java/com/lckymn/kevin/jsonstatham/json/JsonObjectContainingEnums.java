/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-05-10)
 */
@JsonObject
public final class JsonObjectContainingEnums
{
	public static enum Role
	{
		SYSTEM_ADMIN, MANAGER, MEMBER
	}

	public static enum Access
	{
		WIKI("Access to wiki"), BLOG("Access to blog"), TWITTER("Access to twitter"), EMAIL("Access to email");

		private final String value;

		private Access(String value)
		{
			this.value = value;
		}
		
		public String value()
		{
			return value;
		}
	}

	@JsonField
	private final String name;

	@JsonField
	private final int number;

	@JsonField
	private final boolean passed;

	@JsonField
	private final Role role;

	@JsonField
	private final Access[] access;

	public JsonObjectContainingEnums(String name, int number, boolean passed, Role role, Access... access)
	{
		this.name = name;
		this.number = number;
		this.passed = passed;
		this.role = role;
		this.access = access;
	}

	@SuppressWarnings("boxing")
	@Override
	public int hashCode()
	{
		return Objects.hash(name, number, passed, role, access);
	}

	@SuppressWarnings("boxing")
	@Override
	public boolean equals(Object jsonObjectContainingEnums)
	{
		if (this == jsonObjectContainingEnums)
		{
			return true;
		}
		if (!(jsonObjectContainingEnums instanceof JsonObjectContainingEnums))
		{
			return false;
		}
		final JsonObjectContainingEnums that = (JsonObjectContainingEnums) jsonObjectContainingEnums;
		return Objects.equals(this.name, that.name) && Objects.equals(this.number, that.number)
				&& Objects.equals(this.passed, that.passed) && Objects.equals(this.role, that.role)
				&& Objects.deepEquals(this.access, that.access);
	}

}
