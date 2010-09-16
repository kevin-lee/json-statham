/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import com.lckymn.kevin.jsonstatham.core.reflect.ReflectionJsonToJavaConverter;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

public interface KnownTypeProcessorWithReflectionJsonToJavaConverter extends
		KnownTypeProcessor<ReflectionJsonToJavaConverter>
{
	@Override
	Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter, Object source)
			throws IllegalArgumentException, IllegalAccessException, JsonStathamException;
}