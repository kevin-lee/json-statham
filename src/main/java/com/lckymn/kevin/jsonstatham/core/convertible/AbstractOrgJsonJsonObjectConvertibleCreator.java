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
public abstract class AbstractOrgJsonJsonObjectConvertibleCreator implements JsonObjectConvertibleCreator
{
	static final JsonObjectConvertible NULL_JSON_OBJECT_CONVERTIBLE = new JsonObjectConvertible()
	{

		@Override
		public String[] getNames()
		{
			throw new JsonStathamException("The getNames method in NullJsonObjectConvertible cannot used.");
		}

		@Override
		public Object get(@SuppressWarnings("unused") String name)
		{
			throw new JsonStathamException("The name method in NullJsonObjectConvertible cannot used.");
		}

		@Override
		public Object getActualObject()
		{
			return JSONObject.NULL;
		}

		@Override
		public JsonObjectConvertible put(@SuppressWarnings("unused") String name,
				@SuppressWarnings("unused") Object value) throws JsonStathamException
		{
			throw new JsonStathamException("The put method in NullJsonObjectConvertible cannot used.");
		}

		@Override
		public String toString()
		{
			return JSONObject.NULL.toString();
		}
	};

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

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.JsonObjectConvertibleCreator#newJsonObjectConvertible()
	 */
	@Override
	public abstract JsonObjectConvertible newJsonObjectConvertible();

	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.JsonObjectConvertibleCreator#nullJsonObjectConvertible()
	 */
	@Override
	public JsonObjectConvertible nullJsonObjectConvertible()
	{
		return NULL_JSON_OBJECT_CONVERTIBLE;
	}

}
