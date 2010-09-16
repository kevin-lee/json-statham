/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import java.util.List;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingList
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueList")
	private final List<String> list;

	public JsonObjectContainingList(String name, List<String> list)
	{
		this.name = name;
		this.list = list;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name, list);
	}

	@Override
	public boolean equals(Object jsonObjectContainingList)
	{
		if (this == jsonObjectContainingList)
		{
			return true;
		}
		if (!(jsonObjectContainingList instanceof JsonObjectContainingList))
		{
			return false;
		}
		final JsonObjectContainingList that = (JsonObjectContainingList) jsonObjectContainingList;
		return Objects.equals(this.name, that.name) && Objects.equals(this.list, that.list);
	}
}
