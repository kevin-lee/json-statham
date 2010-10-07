/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import java.lang.reflect.Type;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-10)
 */
public interface KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<VT extends Type> extends
		KnownTypeProcessorDeciderForJsonToJava<KnownTypeProcessorWithReflectionJsonToJavaConverter<VT>, VT>
{
	@Override
	KnownTypeProcessorWithReflectionJsonToJavaConverter<VT> decide(VT type);
}
