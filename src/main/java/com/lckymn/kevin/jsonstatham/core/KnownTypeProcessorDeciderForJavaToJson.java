/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-10)
 */
public interface KnownTypeProcessorDeciderForJavaToJson extends
		KnownTypeProcessorDecider<KnownTypeProcessorWithReflectionJavaToJsonConverter>
{
	KnownTypeProcessorWithReflectionJavaToJsonConverter decide(Class<?> type);
}
