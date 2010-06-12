/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import com.lckymn.kevin.jsonstatham.core.reflect.ReflectionJsonStatham;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

public interface KnownTypeProcessor
{
	Object process(ReflectionJsonStatham jsonStatham, Object source) throws IllegalArgumentException, IllegalAccessException,
			JsonStathamException;
}