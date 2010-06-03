/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.lckymn.kevin.jsonstatham.core.JsonConvertible;
import com.lckymn.kevin.jsonstatham.core.JsonObjectConvertible;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-02)
 */
public final class OrgJsonJsonObjectConvertible implements JsonObjectConvertible
{
	private final JSONObject jsonObject;

	public OrgJsonJsonObjectConvertible(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.JsonObjectConvertible#put(java.lang.String, java.lang.Object)
	 */
	@Override
	public JsonObjectConvertible put(String name, Object value) throws JsonStathamException
	{
		try
		{
			if (value instanceof JsonConvertible)
			{
				jsonObject.put(name, ((JsonConvertible) value).getActualObject());
			}
			else
			{
				jsonObject.put(name, value);
			}
			return this;
		}
		catch (JSONException e)
		{
			throw new JsonStathamException(e);
		}
	}

	@Override
	public Object getActualObject()
	{
		return jsonObject;
	}

	@Override
	public String toString()
	{
		return jsonObject.toString();
	}
}
