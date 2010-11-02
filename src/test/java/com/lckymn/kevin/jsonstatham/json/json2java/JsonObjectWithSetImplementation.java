/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json.json2java;

import static com.lckymn.kevin.common.util.Objects.*;

import java.util.LinkedHashSet;

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
		return hash(addressLinkedHashSet);
	}

	@Override
	public boolean equals(Object jsonObjectWithSetImplementation)
	{
		if (areIdentical(this, jsonObjectWithSetImplementation))
		{
			return true;
		}
		final JsonObjectWithSetImplementation that =
			castIfInstanceOf(JsonObjectWithSetImplementation.class, jsonObjectWithSetImplementation);
		return isNotNull(that) && equal(this.addressLinkedHashSet, that.getAddressLinkedHashSet());
	}

	@Override
	public String toString()
	{
		return toStringBuilder(this).add("addresses", addressLinkedHashSet)
				.toString();
	}
}
