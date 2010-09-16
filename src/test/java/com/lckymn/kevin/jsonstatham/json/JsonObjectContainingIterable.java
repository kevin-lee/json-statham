/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingIterable
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueIterable")
	private final Iterable<String> iterable;

	public JsonObjectContainingIterable(String name, Iterable<String> iterable)
	{
		this.name = name;
		this.iterable = iterable;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name, iterable);
	}

	@Override
	public boolean equals(Object jsonObjectContainingIterable)
	{
		if (this == jsonObjectContainingIterable)
		{
			return true;
		}
		if (!(jsonObjectContainingIterable instanceof JsonObjectContainingIterable))
		{
			return false;
		}
		JsonObjectContainingIterable that = (JsonObjectContainingIterable) jsonObjectContainingIterable;
		return Objects.equals(this.name, that.name) && Objects.equals(this.iterable, that.iterable);
	}
}
