/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

import java.util.Set;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingSet
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueSet")
	private final Set<String> set;

	public JsonObjectContainingSet(String name, Set<String> set)
	{
		this.name = name;
		this.set = set;
	}
}
