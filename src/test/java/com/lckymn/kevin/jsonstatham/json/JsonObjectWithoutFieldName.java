package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-12)
 */
@JsonObject
public class JsonObjectWithoutFieldName
{
	@JsonField
	private final long id;

	@JsonField
	private final String name;

	@JsonField
	private final String address;

	public JsonObjectWithoutFieldName(long id, String name, String address)
	{
		this.id = id;
		this.name = name;
		this.address = address;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id, name, address);
	}

	@SuppressWarnings("boxing")
	@Override
	public boolean equals(Object jsonObjectWithoutFieldName)
	{
		if (this == jsonObjectWithoutFieldName)
		{
			return true;
		}
		if (!(jsonObjectWithoutFieldName instanceof JsonObjectWithoutFieldName))
		{
			return false;
		}
		final JsonObjectWithoutFieldName that = (JsonObjectWithoutFieldName) jsonObjectWithoutFieldName;
		return Objects.equals(this.id, that.id) && Objects.equals(this.name, that.name)
				&& Objects.equals(this.address, that.address);
	}
}
