/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-11-21)
 */
public interface JsonStatham
{
	/**
	 * Returns String value containing JSON text. It turns the given target object which must be annotated with {@link JsonObject} into JSON
	 * text. If the target object is not annotated with {@link JsonObject}, it throws {@link IllegalStateException}.
	 * 
	 * @param target
	 *            the target object.
	 * @return String value which contains JSON text created based on the given target object.
	 * @throws JsonStathamException
	 *             TODO: finish it!
	 */
	String convertToJson(Object target) throws JsonStathamException;
}
