/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import java.util.Set;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingSet
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueSet")
	private final Set<String> set;

	public JsonObjectContainingSet(String name, Set<String> set)
	{
		this.name = name;
		this.set = set;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name, set);
	}

	@Override
	public boolean equals(Object jsonObjectContainingSet)
	{
		if (this == jsonObjectContainingSet)
		{
			return true;
		}
		if (!(jsonObjectContainingSet instanceof JsonObjectContainingSet))
		{
			return false;
		}
		final JsonObjectContainingSet that = (JsonObjectContainingSet) jsonObjectContainingSet;
		return Objects.equals(this.name, that.name) && Objects.equals(this.set, that.set);
	}
}
