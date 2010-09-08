/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

import java.util.HashMap;

import org.json.JSONObject;


/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
public final class OrgJsonUnorderedJsonObjectConvertibleCreator extends AbstractOrgJsonJsonObjectConvertibleCreator
{
	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.JsonObjectConvertibleCreator#newJSONObject()
	 */
	@Override
	public JsonObjectConvertible newJsonObjectConvertible()
	{
		return new OrgJsonJsonObjectConvertible(new JSONObject(new HashMap<String, Object>()));
	}

}
