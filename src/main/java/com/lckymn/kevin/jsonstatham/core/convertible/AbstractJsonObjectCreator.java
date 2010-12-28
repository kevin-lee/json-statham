/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

import org.json.JSONException;
import org.json.JSONObject;

import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-02)
 */
public abstract class AbstractJsonObjectCreator implements JsonObjectConvertibleCreator
{

	@Override
	public JsonObjectConvertible newJsonObjectConvertible(String jsonString) throws JsonStathamException
	{
		try
		{
			return new OrgJsonJsonObjectConvertible(new JSONObject(jsonString));
		}
		catch (JSONException e)
		{
			throw new JsonStathamException(e);
		}
	}

	@Override
	public abstract JsonObjectConvertible newJsonObjectConvertible();

	@Override
	public JsonObjectConvertible nullJsonObjectConvertible()
	{
		return JsonObjectImpl.NULL_JSON_OBJECT;
	}

}
