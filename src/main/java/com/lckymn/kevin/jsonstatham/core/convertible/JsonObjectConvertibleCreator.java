/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
public interface JsonObjectConvertibleCreator
{
	JsonObjectConvertible newJsonObjectConvertible();

	JsonObjectConvertible nullJsonObjectConvertible();

	JsonObjectConvertible newJsonObjectConvertible(String jsonString) throws JsonStathamException;
}
