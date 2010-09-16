/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import com.lckymn.kevin.jsonstatham.core.reflect.ReflectionJavaToJsonConverter;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

public interface KnownTypeProcessorWithReflectionJavaToJsonConverter extends
		KnownTypeProcessor<ReflectionJavaToJsonConverter>
{
	@Override
	Object process(ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, Object source)
			throws IllegalArgumentException, IllegalAccessException, JsonStathamException;
}