/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-02)
 */
public interface JsonArrayConvertible extends JsonConvertible
{
	JsonArrayConvertible put(Object value);

	@Override
	Object getActualObject();

	@Override
	String toString();
}
