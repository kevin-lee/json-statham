/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import java.util.Set;
import java.util.Map.Entry;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingMapEntrySet
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueMapEntrySet")
	private final Set<Entry<String, Address>> mapEntrySet;

	public JsonObjectContainingMapEntrySet(String name, Set<Entry<String, Address>> mapEntrySet)
	{
		this.name = name;
		this.mapEntrySet = mapEntrySet;
	}
}
