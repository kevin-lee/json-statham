/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

import org.json.JSONArray;
import org.json.JSONException;

import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-02)
 * @version 0.0.2 (2010-09-13)
 */
public final class OrgJsonJsonArrayConvertible implements JsonArrayConvertible
{
	private final JSONArray jsonArray;

	public OrgJsonJsonArrayConvertible(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	@Override
	public Object get(int index)
	{
		try
		{
			return jsonArray.get(index);
		}
		catch (JSONException e)
		{
			throw new JsonStathamException(e);
		}
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
	public int length()
	{
		return jsonArray.length();
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
