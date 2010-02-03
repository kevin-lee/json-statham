/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.impl;

import java.util.HashMap;

import org.json.JSONObject;

import com.lckymn.kevin.jsonstatham.core.JSONObjectCreator;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
public class HashMapJSONObjectCreator implements JSONObjectCreator
{
	/*
	 * (non-Javadoc)
	 * @see com.lckymn.kevin.jsonstatham.core.JSONObjectCreator#newJSONObject()
	 */
	@Override
	public JSONObject newJSONObject()
	{
		return new JSONObject(new HashMap<String, Object>());
	}

}
