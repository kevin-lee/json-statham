package com.lckymn.kevin.jsonstatham.core;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-10)
 * @version 0.0.2 (2010-09-16) moved from
 *          {@link com.lckymn.kevin.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider}.
 */
public interface SimpleKnownTypeChecker
{
	boolean isKnown(Class<?> type);
}