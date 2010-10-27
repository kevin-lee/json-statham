/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json.json2java;

import java.util.LinkedHashMap;

import com.lckymn.kevin.common.util.Objects;
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
		return Objects.hashCode(addressLinkedHashMap);
	}

	@Override
	public boolean equals(Object JsonObjectWithSetImplementation)
	{
		if (this == JsonObjectWithSetImplementation)
		{
			return true;
		}
		if (!(JsonObjectWithSetImplementation instanceof JsonObjectWithMapImplementation))
		{
			return false;
		}
		final JsonObjectWithMapImplementation that = (JsonObjectWithMapImplementation) JsonObjectWithSetImplementation;
		return this.addressLinkedHashMap == that.getAddressLinkedHashMap()
				|| (null != this.addressLinkedHashMap && this.addressLinkedHashMap.equals(that.getAddressLinkedHashMap()));
	}

	@Override
	public String toString()
	{
		return Objects.toStringBuilder(this)
				.add("addresses", addressLinkedHashMap)
				.toString();
	}
}
