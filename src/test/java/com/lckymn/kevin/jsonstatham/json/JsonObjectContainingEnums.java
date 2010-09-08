/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

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

		@Override
		public String toString()
		{
			return this.value;
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

}
