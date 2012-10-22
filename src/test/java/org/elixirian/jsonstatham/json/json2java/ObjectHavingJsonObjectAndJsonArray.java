/**
 *
 */
package org.elixirian.jsonstatham.json.json2java;

import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.core.convertible.JsonArray;
import org.elixirian.jsonstatham.core.convertible.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2012-10-22)
 */
@Json
public class ObjectHavingJsonObjectAndJsonArray
{
	@JsonField
	private String name;

	@JsonField
	private JsonObject jsonObject;

	@JsonField
	private JsonArray jsonArray;

	public ObjectHavingJsonObjectAndJsonArray(final String name, final JsonObject jsonObject, final JsonArray jsonArray)
	{
		this.name = name;
		this.jsonObject = jsonObject;
		this.jsonArray = jsonArray;
	}

	public String getFsi()
	{
		return name;
	}

	public JsonObject getJsonObject()
	{
		return jsonObject;
	}

	public JsonArray getJsonArray()
	{
		return jsonArray;
	}

	@Override
	public String toString()
	{
		/* @formatter:off */
		return toStringBuilder(this)
						.add("name", name)
						.add("jsonObject", jsonObject)
						.add("jsonArray", jsonArray)
					 .toString();
		/* @formatter:on */
	}

	@Override
	public int hashCode()
	{
		return hash(name, jsonObject, jsonArray);
	}

	@Override
	public boolean equals(final Object objectHavingJsonObjectAndJsonArray)
	{
		if (this == objectHavingJsonObjectAndJsonArray)
			return true;

		final ObjectHavingJsonObjectAndJsonArray that =
			castIfInstanceOf(ObjectHavingJsonObjectAndJsonArray.class, objectHavingJsonObjectAndJsonArray);
		/* @formatter:off */
		return null != that
				&& (equal(this.name, that.getFsi()) &&
						equal(this.jsonObject, that.getJsonObject()) &&
						equal(this.jsonArray, that.getJsonArray()));
		/* @formatter:on */
	}
}
