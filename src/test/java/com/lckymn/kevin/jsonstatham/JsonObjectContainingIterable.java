/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

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
}
