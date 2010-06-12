/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.lckymn.kevin.jsonstatham.core.JsonObjectConvertible;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
public class OrgJsonOrderedJsonObjectConvertibleCreator extends AbstractOrgJsonJsonObjectConvertibleCreator
{
	@Override
	public JsonObjectConvertible newJsonObjectConvertible()
	{
		return new OrgJsonJsonObjectConvertible(new JSONObject(new LinkedHashMap<String, Object>()));
	}

}
