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
public class JsonScannerForUnorderedJsonObject extends AbstractJsonScanner
{
	public JsonScannerForUnorderedJsonObject(final String jsonString)
	{
		super(jsonString);
	}

	@Override
	protected UnorderedJsonObject newJsonObjectConvertible(final JsonScanner jsonScanner)
	{
		return new UnorderedJsonObject(jsonScanner);
	}

	@Override
	protected JsonArrayWithUnorderedJsonObject newJsonArrayConvertible(final JsonScanner jsonScanner)
	{
		return new JsonArrayWithUnorderedJsonObject(jsonScanner);
	}
}
