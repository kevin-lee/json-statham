/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import java.lang.reflect.Type;

import com.lckymn.kevin.jsonstatham.core.reflect.json2java.ReflectionJsonToJavaConverter;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

public interface KnownTypeProcessorWithReflectionJsonToJavaConverter<VT extends Type> extends
		KnownTypeProcessorForJsonToJava<ReflectionJsonToJavaConverter, VT>
{
	@Override
	<T> Object process(ReflectionJsonToJavaConverter reflectionJsonToJavaConverter, VT valueType, Object value)
			throws IllegalArgumentException, IllegalAccessException, JsonStathamException;
}