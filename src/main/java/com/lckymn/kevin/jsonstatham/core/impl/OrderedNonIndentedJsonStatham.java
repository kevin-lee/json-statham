/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.impl;

import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.lckymn.kevin.common.validation.AssertIt;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-12-07)
 */
public class OrderedNonIndentedJsonStatham extends AbstractJsonStatham
{
	@Override
	protected JSONObject newJSONObject()
	{
		return new JSONObject(new LinkedHashMap<String, Object>());
	}

	@Override
	public String convertIntoJson(Object target) throws JsonStathamException
	{
		try
		{
			AssertIt.isNotNull(target, "The given object is null.");
			JSONObject jsonObject = createJsonObject(target);

			return (null == jsonObject ? "null" : jsonObject.toString());
		}
		catch (IllegalArgumentException e)
		{
			throw new JsonStathamException("Wrong object is passed or it has illegal fields with the @JsonField annotation", e);
		}
		catch (JSONException e)
		{
			throw new JsonStathamException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new JsonStathamException(e);
		}
	}

}
