/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json.json2java;

import java.util.LinkedHashSet;

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
public class JsonObjectWithSetImplementation
{
	@JsonField(name = "address")
	private final LinkedHashSet<Address> addressLinkedHashSet;

	@JsonConstructor
	public JsonObjectWithSetImplementation(LinkedHashSet<Address> addressLinkedHashSet)
	{
		this.addressLinkedHashSet = addressLinkedHashSet;
	}

	public LinkedHashSet<Address> getAddressLinkedHashSet()
	{
		return addressLinkedHashSet;
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(addressLinkedHashSet);
	}

	@Override
	public boolean equals(Object JsonObjectWithSetImplementation)
	{
		if (this == JsonObjectWithSetImplementation)
		{
			return true;
		}
		if (!(JsonObjectWithSetImplementation instanceof JsonObjectWithSetImplementation))
		{
			return false;
		}
		final JsonObjectWithSetImplementation that = (JsonObjectWithSetImplementation) JsonObjectWithSetImplementation;
		return this.addressLinkedHashSet == that.getAddressLinkedHashSet()
				|| (null != this.addressLinkedHashSet && this.addressLinkedHashSet.equals(that.getAddressLinkedHashSet()));
	}

	@Override
	public String toString()
	{
		return Objects.toStringBuilder(this)
				.add("addresses", addressLinkedHashSet)
				.toString();
	}
}
