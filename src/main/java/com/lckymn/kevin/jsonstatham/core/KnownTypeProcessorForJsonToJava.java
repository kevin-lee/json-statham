/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import java.lang.reflect.Type;

import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

public interface KnownTypeProcessorForJsonToJava<P, VT extends Type>
{
	<T> Object process(P processor, VT valueType, Object value) throws IllegalArgumentException,
			IllegalAccessException, JsonStathamException;
}