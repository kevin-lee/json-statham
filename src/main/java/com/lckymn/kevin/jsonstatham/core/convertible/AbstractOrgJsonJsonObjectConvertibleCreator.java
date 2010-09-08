/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

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
		public Object getActualObject()
		{
			return JSONObject.NULL;
		}

		@Override
		public JsonObjectConvertible put(@SuppressWarnings("unused") String name, @SuppressWarnings("unused") Object value)
				throws JsonStathamException
		{
			throw new JsonStathamException("The put method in NullJsonObjectConvertible cannot used.");
		}

		@Override
		public String toString()
		{
			return JSONObject.NULL.toString();
		}
	};

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
