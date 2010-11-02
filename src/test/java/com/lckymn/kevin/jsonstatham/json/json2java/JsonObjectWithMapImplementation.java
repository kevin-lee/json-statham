/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json.json2java;

import static com.lckymn.kevin.common.util.Objects.*;

import java.util.LinkedHashMap;

import com.lckymn.kevin.jsonstatham.annotation.JsonConstructor;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.json.Address;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-10-27)
 */
@JsonObject
public class JsonObjectWithMapImplementation
{
	@JsonField(name = "address")
	private final LinkedHashMap<String, Address> addressLinkedHashMap;

	@JsonConstructor
	public JsonObjectWithMapImplementation(LinkedHashMap<String, Address> addressLinkedHashMap)
	{
		this.addressLinkedHashMap = addressLinkedHashMap;
	}

	public LinkedHashMap<String, Address> getAddressLinkedHashMap()
	{
		return addressLinkedHashMap;
	}

	@Override
	public int hashCode()
	{
		return hash(addressLinkedHashMap);
	}

	@Override
	public boolean equals(Object jsonObjectWithSetImplementation)
	{
		if (areIdentical(this, jsonObjectWithSetImplementation))
		{
			return true;
		}
		final JsonObjectWithMapImplementation that =
			castIfInstanceOf(JsonObjectWithMapImplementation.class, jsonObjectWithSetImplementation);
		return isNotNull(that) && equal(this.addressLinkedHashMap, that.getAddressLinkedHashMap());
	}

	@Override
	public String toString()
	{
		return toStringBuilder(this).add("addresses", addressLinkedHashMap)
				.toString();
	}
}
