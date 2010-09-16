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
 */
public final class OrgJsonJsonArrayConvertibleCreator implements JsonArrayConvertibleCreator
{

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.JsonArrayConvertibleCreator#newJsonArrayConvertible()
	 */
	@Override
	public JsonArrayConvertible newJsonArrayConvertible()
	{
		return new OrgJsonJsonArrayConvertible(new JSONArray());
	}

	@Override
	public JsonArrayConvertible newJsonArrayConvertible(String jsonString)
	{
		try
		{
			return new OrgJsonJsonArrayConvertible(new JSONArray(jsonString));
		}
		catch (JSONException e)
		{
			throw new JsonStathamException(e);
		}
	}

}
