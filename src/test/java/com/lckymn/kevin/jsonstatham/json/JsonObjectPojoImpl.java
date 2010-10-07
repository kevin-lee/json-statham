/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-06)
 */
@JsonObject
public class JsonObjectPojoImpl implements JsonObjectPojo
{
	@ValueAccessor
	@JsonField
	private Long id;

	@ValueAccessor
	@JsonField
	private String name;

	@ValueAccessor(name = "getAddresses")
	@JsonField(name = "addresses")
	private Set<Address> addressSet;

	public JsonObjectPojoImpl(Long id, String name, Set<Address> addressSet)
	{
		this.id = id;
		this.name = name;
		this.addressSet = addressSet;
	}

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public Iterator<Address> getAddresses()
	{
		return addressSet.iterator();
	}

	@Override
	public Set<Address> getAddressSet()
	{
		return addressSet;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id, name, addressSet);
	}

	@Override
	public boolean equals(Object jsonObjectPojoImpl)
	{
		if (this == jsonObjectPojoImpl)
		{
			return true;
		}
		if (!(jsonObjectPojoImpl instanceof JsonObjectPojoImpl))
		{
			return false;
		}
		final JsonObjectPojoImpl that = (JsonObjectPojoImpl) jsonObjectPojoImpl;
		return Objects.equals(this.id, that.getId()) && Objects.equals(this.name, that.getName())
				&& Objects.equals(this.addressSet, that.getAddressSet());
	}

	@Override
	public String toString()
	{
		return Objects.toStringBuilder(this)
				.add("id", id)
				.add("name", name)
				.add("addresses", addressSet)
				.toString();
	}
}
