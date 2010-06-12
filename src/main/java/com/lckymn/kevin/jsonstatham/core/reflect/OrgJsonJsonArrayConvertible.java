/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import org.json.JSONArray;

import com.lckymn.kevin.jsonstatham.core.JsonArrayConvertible;
import com.lckymn.kevin.jsonstatham.core.JsonConvertible;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-02)
 */
public final class OrgJsonJsonArrayConvertible implements JsonArrayConvertible
{
	private final JSONArray jsonArray;

	public OrgJsonJsonArrayConvertible(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.JsonArrayConvertible#put(java.lang.Object)
	 */
	@Override
	public JsonArrayConvertible put(Object value)
	{
		if (value instanceof JsonConvertible)
		{
			jsonArray.put(((JsonConvertible) value).getActualObject());
		}
		else
		{
			jsonArray.put(value);
		}
		return this;
	}

	@Override
	public Object getActualObject()
	{
		return jsonArray;
	}

	@Override
	public String toString()
	{
		return jsonArray.toString();
	}
}
