/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import java.util.Collection;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingCollection
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueCollection")
	private final Collection<String> collection;

	public JsonObjectContainingCollection(String name, Collection<String> collection)
	{
		this.name = name;
		this.collection = collection;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name, collection);
	}

	@Override
	public boolean equals(Object jsonObjectContainingCollection)
	{
		if (this == jsonObjectContainingCollection)
		{
			return true;
		}
		if (!(jsonObjectContainingCollection instanceof JsonObjectContainingCollection))
		{
			return false;
		}
		final JsonObjectContainingCollection that = (JsonObjectContainingCollection) jsonObjectContainingCollection;
		return Objects.equals(this.name, that.name) && Objects.equals(this.collection, that.collection);
	}

}
