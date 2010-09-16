/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-02)
 * @version 0.0.2 (2010-09-13)
 */
public interface JsonArrayConvertible extends JsonConvertible
{
	Object get(int index);
	
	JsonArrayConvertible put(Object value);

	int length();

	@Override
	Object getActualObject();

	@Override
	String toString();
}
