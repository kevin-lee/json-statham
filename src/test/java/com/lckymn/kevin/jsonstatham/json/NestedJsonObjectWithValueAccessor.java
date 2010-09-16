/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-11-28)
 */
@JsonObject
public class NestedJsonObjectWithValueAccessor
{
	@ValueAccessor
	@JsonField(name = "id")
	private Long primaryKey;

	@ValueAccessor
	@JsonField(name = "name")
	private String name;

	@ValueAccessor
	@JsonField(name = "parent")
	private NestedJsonObjectWithValueAccessor parent;

	public NestedJsonObjectWithValueAccessor(Long primaryKey, String name, NestedJsonObjectWithValueAccessor parent)
	{
		this.primaryKey = primaryKey;
		this.name = name;
		this.parent = parent;
	}

	public Long getPrimaryKey()
	{
		return primaryKey;
	}

	public void setPrimaryKey(Long primaryKey)
	{
		this.primaryKey = primaryKey;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public NestedJsonObjectWithValueAccessor getParent()
	{
		return parent;
	}

	public void setParent(NestedJsonObjectWithValueAccessor parent)
	{
		this.parent = parent;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(primaryKey, name, parent);
	}

	@Override
	public boolean equals(Object nestedJsonObjectWithValueAccessor)
	{
		if (this == nestedJsonObjectWithValueAccessor)
		{
			return true;
		}
		if (!(nestedJsonObjectWithValueAccessor instanceof NestedJsonObjectWithValueAccessor))
		{
			return false;
		}
		final NestedJsonObjectWithValueAccessor that =
			(NestedJsonObjectWithValueAccessor) nestedJsonObjectWithValueAccessor;
		return Objects.equals(this.primaryKey, that.getPrimaryKey()) && Objects.equals(this.name, that.getName())
				&& Objects.equals(this.parent, that.getParent());
	}
}
