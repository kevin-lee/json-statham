/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json.json2java;

import java.util.LinkedList;

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
		return Objects.hashCode(addressLinkedList);
	}

	@Override
	public boolean equals(Object JsonObjectWithSetImplementation)
	{
		if (this == JsonObjectWithSetImplementation)
		{
			return true;
		}
		if (!(JsonObjectWithSetImplementation instanceof JsonObjectWithListImplementation))
		{
			return false;
		}
		final JsonObjectWithListImplementation that =
			(JsonObjectWithListImplementation) JsonObjectWithSetImplementation;
		return this.addressLinkedList == that.getAddressLinkedList()
				|| (null != this.addressLinkedList && this.addressLinkedList.equals(that.getAddressLinkedList()));
	}

	@Override
	public String toString()
	{
		return Objects.toStringBuilder(this)
				.add("addresses", addressLinkedList)
				.toString();
	}
}
