/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json.json2java;

import java.util.List;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.json.Address;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-10-08)
 */
@JsonObject
public class JsonObjectHavingNestedGenericTypes
{
	@JsonField
	private List<List<List<Address>>> listOfListOfListOfAddress;

	public JsonObjectHavingNestedGenericTypes(List<List<List<Address>>> listOfListOfListOfAddress)
	{
		this.listOfListOfListOfAddress = listOfListOfListOfAddress;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(listOfListOfListOfAddress);
	}

	@Override
	public boolean equals(Object jsonObjectHavingNestedGenericTypesObject)
	{
		if (this == jsonObjectHavingNestedGenericTypesObject)
		{
			return true;
		}
		if (!(jsonObjectHavingNestedGenericTypesObject instanceof JsonObjectHavingNestedGenericTypes))
		{
			return false;
		}
		final JsonObjectHavingNestedGenericTypes that =
			(JsonObjectHavingNestedGenericTypes) jsonObjectHavingNestedGenericTypesObject;
		return Objects.equals(this.listOfListOfListOfAddress, that.listOfListOfListOfAddress);
	}

	@Override
	public String toString()
	{
		return Objects.toStringBuilder(this)
				.add("listOfListOfListOfAddress", listOfListOfListOfAddress)
				.toString();
	}
}
