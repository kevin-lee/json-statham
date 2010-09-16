/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import java.util.Iterator;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingIterator
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueIterator")
	private final Iterator<String> iterator;

	public JsonObjectContainingIterator(String name, Iterator<String> iterator)
	{
		this.name = name;
		this.iterator = iterator;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name, iterator);
	}

	@Override
	public boolean equals(Object jsonObjectContainingIterator)
	{
		if (this == jsonObjectContainingIterator)
		{
			return true;
		}
		if (!(jsonObjectContainingIterator instanceof JsonObjectContainingIterator))
		{
			return false;
		}
		final JsonObjectContainingIterator that = (JsonObjectContainingIterator) jsonObjectContainingIterator;
		return Objects.equals(this.name, that.name) && Objects.equals(this.iterator, that.iterator);
	}
}
