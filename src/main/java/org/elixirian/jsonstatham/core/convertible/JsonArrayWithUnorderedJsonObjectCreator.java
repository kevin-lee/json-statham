/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

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
 * @version 0.0.1 (2010-06-02)
 */
public class JsonArrayWithUnorderedJsonObjectCreator implements JsonArrayCreator
{
	@Override
	public JsonArray newJsonArrayConvertible()
	{
		return JsonArrayWithUnorderedJsonObject.newJsonArray();
	}

	@Override
	public JsonArray newJsonArrayConvertible(final String jsonString)
	{
		return JsonArrayWithUnorderedJsonObject.newJsonArray(jsonString);
	}
}
