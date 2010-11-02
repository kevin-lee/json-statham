/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import static com.lckymn.kevin.common.util.Conditions.*;
import static com.lckymn.kevin.common.util.Objects.*;

import java.util.Map.Entry;
import java.util.Set;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingMapEntrySet
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueMapEntrySet")
	private final Set<Entry<String, Address>> mapEntrySet;

	public JsonObjectContainingMapEntrySet(String name, Set<Entry<String, Address>> mapEntrySet)
	{
		this.name = name;
		this.mapEntrySet = mapEntrySet;
	}

	@Override
	public int hashCode()
	{
		return hash(name, mapEntrySet);
	}

	@Override
	public boolean equals(Object jsonObjectContainingMapEntrySet)
	{
		if (areIdentical(this, jsonObjectContainingMapEntrySet))
		{
			return true;
		}
		final JsonObjectContainingMapEntrySet that =
			castIfInstanceOf(JsonObjectContainingMapEntrySet.class, jsonObjectContainingMapEntrySet);
		/* @formatter:off */
		return isNotNull(that) && 
				and(equal(this.name, that.name), 
					equal(this.mapEntrySet, that.mapEntrySet));
		/* @formatter:on */
	}

	@Override
	public String toString()
	{
		return toStringBuilder(this).add("name", name)
				.add("valueMapEntrySet", mapEntrySet)
				.toString();
	}
}
