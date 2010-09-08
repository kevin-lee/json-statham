/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

import java.util.LinkedHashMap;

import org.json.JSONObject;


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
