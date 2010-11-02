/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json.json2java;

import static com.lckymn.kevin.common.util.Objects.*;

import java.util.List;

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
		return hash(listOfListOfListOfAddress);
	}

	@Override
	public boolean equals(Object jsonObjectHavingNestedGenericTypesObject)
	{
		if (areIdentical(this, jsonObjectHavingNestedGenericTypesObject))
		{
			return true;
		}
		final JsonObjectHavingNestedGenericTypes that =
			castIfInstanceOf(JsonObjectHavingNestedGenericTypes.class, jsonObjectHavingNestedGenericTypesObject);
		return isNotNull(that) && equal(this.listOfListOfListOfAddress, that.listOfListOfListOfAddress);
	}

	@Override
	public String toString()
	{
		return toStringBuilder(this).add("listOfListOfListOfAddress", listOfListOfListOfAddress)
				.toString();
	}
}
