/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

public interface KnownTypeProcessor<T>
{
	Object process(T processor, Object source) throws IllegalArgumentException, IllegalAccessException,
			JsonStathamException;
}