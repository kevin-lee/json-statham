/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import org.elixirian.jsonstatham.exception.JsonStathamException;

/**
 * <pre>
 *     ___  _____                                _____
 *    /   \/    /_________  ___ ____ __ ______  /    /   ______  ______
 *   /        / /  ___ \  \/  //___// //     / /    /   /  ___ \/  ___ \
 *  /        \ /  _____/\    //   //   __   / /    /___/  _____/  _____/
 * /____/\____\\_____/   \__//___//___/ /__/ /________/\_____/ \_____/
 * </pre>
 *
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-12-25)
 */
public class OrderedJsonObjectCreator extends AbstractJsonObjectCreator
{
	@Override
	public OrderedJsonObject newJsonObjectConvertible(final String jsonString) throws JsonStathamException
	{
		return OrderedJsonObject.newJsonObject(jsonString);
	}

	@Override
	public OrderedJsonObject newJsonObjectConvertible()
	{
		return OrderedJsonObject.newJsonObject();
	}
}
