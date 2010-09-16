/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import java.util.Iterator;
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
		System.out.println("yeah!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
		return null;
	}

	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public Iterator<Address> getAddresses()
	{
		return null;
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
		return Objects.equals(this.id, that.id) && Objects.equals(this.name, that.name)
				&& Objects.equals(this.addressSet, that.addressSet);
	}
}
