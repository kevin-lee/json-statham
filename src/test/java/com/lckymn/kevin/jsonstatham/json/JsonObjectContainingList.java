/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import java.util.List;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingList
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueList")
	private final List<String> list;

	public JsonObjectContainingList(String name, List<String> list)
	{
		this.name = name;
		this.list = list;
	}
}
