/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import static com.lckymn.kevin.common.util.Conditions.*;
import static com.lckymn.kevin.common.util.Objects.*;

import java.util.Iterator;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingIterable
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueIterable")
	private final Iterable<String> iterable;

	public JsonObjectContainingIterable(String name, Iterable<String> iterable)
	{
		this.name = name;
		this.iterable = iterable;
	}

	@Override
	public int hashCode()
	{
		return hash(name, iterable);
	}

	@Override
	public boolean equals(Object jsonObjectContainingIterable)
	{
		if (areIdentical(this, jsonObjectContainingIterable))
		{
			return true;
		}
		final JsonObjectContainingIterable that =
			castIfInstanceOf(JsonObjectContainingIterable.class, jsonObjectContainingIterable);
		/* @formatter:off */
		return isNotNull(that) && 
				and(equal(this.name, that.name), 
					iterableEquals(this.iterable, that.iterable));
		/* @formatter:on */
	}

	private boolean iterableEquals(Iterable<String> iterable1, Iterable<String> iterable2)
	{
		final Iterator<String> iterator1 = iterable1.iterator();
		final Iterator<String> iterator2 = iterable2.iterator();
		if (iterator1.hasNext() != iterator2.hasNext())
		{
			return false;
		}
		int count1 = 0;
		int count2 = 0;
		while (iterator1.hasNext())
		{
			count1++;
			final String value1 = iterator1.next();
			if (iterator2.hasNext())
			{
				count2++;
				final String value2 = iterator2.next();
				if (value1 != value2)
				{
					if (null != value1 && !value1.equals(value2))
					{
						return false;
					}
				}
			}
		}
		return count1 == count2;
	}
}
