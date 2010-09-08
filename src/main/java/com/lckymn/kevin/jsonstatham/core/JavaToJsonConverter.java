/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-08)
 */
public interface JavaToJsonConverter
{
	String convertIntoJson(Object source) throws IllegalArgumentException, JsonStathamException, IllegalAccessException;
}
