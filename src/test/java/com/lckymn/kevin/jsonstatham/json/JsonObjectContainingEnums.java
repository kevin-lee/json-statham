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

	@Override
	public int hashCode()
	{
		return hash(hash(hash(hash(hash(name), number), passed), role), access);
	}

	@Override
	public boolean equals(Object jsonObjectContainingEnums)
	{
		if (areIdentical(this, jsonObjectContainingEnums))
		{
			return true;
		}
		final JsonObjectContainingEnums that =
			castIfInstanceOf(JsonObjectContainingEnums.class, jsonObjectContainingEnums);
		/* @formatter:off */
		return isNotNull(that) && 
				and(equal(this.name, that.name), 
					equal(this.number, that.number), 
					equal(this.passed, that.passed), 
					equal(this.role, that.role), 
					deepEqual(this.access, that.access));
		/* @formatter:on */
	}

	@Override
	public String toString()
	{
		return toStringBuilder(this).add("name", name)
				.add("number", number)
				.add("passed", passed)
				.add("role", role)
				.add("access", access)
				.toString();
	}

}
