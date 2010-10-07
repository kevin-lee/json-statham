/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

public interface KnownTypeProcessor<P>
{
	<T> Object process(P processor, Class<T> valueType, Object value) throws IllegalArgumentException, IllegalAccessException,
			JsonStathamException;
}