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
 * @version 0.0.1 (2012-08-18)
 */
public class JsonScannerForOrderedJsonObject extends AbstractJsonScanner
{

	public JsonScannerForOrderedJsonObject(final String jsonString)
	{
		super(jsonString);
	}

	@Override
	protected JsonObject newJsonObjectConvertible(final JsonScanner jsonScanner)
	{
		return new OrderedJsonObject(jsonScanner);
	}

	@Override
	protected JsonArrayWithOrderedJsonObject newJsonArrayConvertible(final JsonScanner jsonScanner)
	{
		return JsonArrayWithOrderedJsonObject.newJsonArray(jsonScanner);
	}
}
