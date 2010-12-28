/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-12-25)
 */
public final class UnorderedJsonObjectCreator extends AbstractJsonObjectCreator
{
	@Override
	public JsonObjectConvertible newJsonObjectConvertible()
	{
		return JsonObjectImpl.newUnorderedJsonObject();
	}
}
