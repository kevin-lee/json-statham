/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-02)
 */
public interface JsonObjectConvertible extends JsonConvertible
{
	String[] getNames();
	
	Object get(String name);
	
	JsonObjectConvertible put(String name, Object value) throws JsonStathamException;

	@Override
	Object getActualObject();

	@Override
	String toString();
}
