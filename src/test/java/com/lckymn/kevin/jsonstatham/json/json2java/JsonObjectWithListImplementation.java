/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json.json2java;

import static com.lckymn.kevin.common.util.Objects.*;

import java.util.LinkedList;

import com.lckymn.kevin.jsonstatham.annotation.JsonConstructor;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.json.Address;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-10-27)
 */
@JsonObject
public class JsonObjectWithListImplementation
{
	@JsonField(name = "address")
	private final LinkedList<Address> addressLinkedList;

	@JsonConstructor
	public JsonObjectWithListImplementation(LinkedList<Address> addressLinkedList)
	{
		this.addressLinkedList = addressLinkedList;
	}

	public LinkedList<Address> getAddressLinkedList()
	{
		return addressLinkedList;
	}

	@Override
	public int hashCode()
	{
		return hash(addressLinkedList);
	}

	@Override
	public boolean equals(Object JsonObjectWithSetImplementation)
	{
		if (areIdentical(this, JsonObjectWithSetImplementation))
		{
			return true;
		}
		final JsonObjectWithListImplementation that =
			castIfInstanceOf(JsonObjectWithListImplementation.class, JsonObjectWithSetImplementation);
		return isNotNull(that) && equal(this.addressLinkedList, that.getAddressLinkedList());
	}

	@Override
	public String toString()
	{
		return toStringBuilder(this).add("addresses", addressLinkedList)
				.toString();
	}
}
