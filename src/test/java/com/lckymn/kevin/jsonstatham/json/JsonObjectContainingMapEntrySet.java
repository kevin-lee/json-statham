/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import java.util.Set;
import java.util.Map.Entry;

import com.lckymn.kevin.common.util.Objects;
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
		return Objects.hash(name, mapEntrySet);
	}

	@Override
	public boolean equals(Object jsonObjectContainingMapEntrySet)
	{
		if (this == jsonObjectContainingMapEntrySet)
		{
			return true;
		}
		if (!(jsonObjectContainingMapEntrySet instanceof JsonObjectContainingMapEntrySet))
		{
			return false;
		}
		final JsonObjectContainingMapEntrySet that = (JsonObjectContainingMapEntrySet) jsonObjectContainingMapEntrySet;
		return Objects.equals(this.name, that.name) && Objects.equals(this.mapEntrySet, that.mapEntrySet);
	}

	@Override
	public String toString()
	{
		return Objects.toStringBuilder(this)
				.add("name", name)
				.add("valueMapEntrySet", mapEntrySet)
				.toString();
	}
}
