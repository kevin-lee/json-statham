/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import java.util.Iterator;

import com.lckymn.kevin.common.util.Objects;
import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingIterator
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueIterator")
	private final Iterator<String> iterator;

	public JsonObjectContainingIterator(String name, Iterator<String> iterator)
	{
		this.name = name;
		this.iterator = iterator;
	}

	public String getName()
	{
		return name;
	}

	public Iterator<String> getIterator()
	{
		return iterator;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name, iterator);
	}

	@Override
	public boolean equals(Object jsonObjectContainingIterator)
	{
		if (this == jsonObjectContainingIterator)
		{
			return true;
		}
		if (!(jsonObjectContainingIterator instanceof JsonObjectContainingIterator))
		{
			return false;
		}
		final JsonObjectContainingIterator that = (JsonObjectContainingIterator) jsonObjectContainingIterator;
		return Objects.equals(this.name, that.name) && iteratorEquals(this.iterator, that.iterator);
	}

	private boolean iteratorEquals(Iterator<String> iterator1, Iterator<String> iterator2)
	{
		if (iterator1.hasNext() != iterator2.hasNext())
		{
			return false;
		}
		int count1 = 0;
		int count2 = 0;
		while (iterator1.hasNext())
		{
			count1++;
			final String value = iterator1.next();
			if (iterator2.hasNext())
			{
				count2++;
				final String value2 = iterator2.next();
				if (value != value2)
				{
					if (null != value && !value.equals(value2))
					{
						return false;
					}
				}
			}
		}
		return count1 == count2;
	}
}