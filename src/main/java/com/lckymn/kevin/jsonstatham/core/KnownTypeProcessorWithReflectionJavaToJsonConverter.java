/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import com.lckymn.kevin.jsonstatham.core.reflect.java2json.ReflectionJavaToJsonConverter;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

public interface KnownTypeProcessorWithReflectionJavaToJsonConverter extends
		KnownTypeProcessor<ReflectionJavaToJsonConverter>
{
	@Override
	<T> Object process(ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, Class<T> valueType, Object value)
			throws IllegalArgumentException, IllegalAccessException, JsonStathamException;
}