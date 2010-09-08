/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-08)
 */
public interface JsonToJavaConverter
{
	<T> T convertFromJson(Class<T> type, String json);
}
