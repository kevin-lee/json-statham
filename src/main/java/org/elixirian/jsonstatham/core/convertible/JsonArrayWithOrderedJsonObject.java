/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import java.util.Collection;

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
public class JsonArrayWithOrderedJsonObject extends JsonArray
{

	public JsonArrayWithOrderedJsonObject()
	{
	}

	public JsonArrayWithOrderedJsonObject(final Collection<?> elements)
	{
		super(elements);
	}

	public JsonArrayWithOrderedJsonObject(final JsonScanner jsonScanner)
	{
		super(jsonScanner);
	}

	public JsonArrayWithOrderedJsonObject(final Object[] elements)
	{
		super(elements);
	}

	public static JsonArrayWithOrderedJsonObject newJsonArray()
	{
		return new JsonArrayWithOrderedJsonObject();
	}

	public static JsonArrayWithOrderedJsonObject newJsonArray(final Collection<?> elements)
	{
		return new JsonArrayWithOrderedJsonObject(elements);
	}

	public static JsonArrayWithOrderedJsonObject newJsonArray(final Object... elements)
	{
		return new JsonArrayWithOrderedJsonObject(elements);
	}

	public static JsonArrayWithOrderedJsonObject newJsonArray(final JsonScannerForOrderedJsonObject jsonScanner)
	{
		return new JsonArrayWithOrderedJsonObject(jsonScanner);
	}

	public static JsonArrayWithOrderedJsonObject newJsonArray(final String jsonString)
	{
		final JsonScanner jsonScanner = new JsonScannerForOrderedJsonObject(jsonString);
		return new JsonArrayWithOrderedJsonObject(jsonScanner);
	}
}
