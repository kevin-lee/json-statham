/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import java.util.Collection;

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
public class JsonArrayWithOrderedJsonObject extends AbstractJsonArray
{

	protected JsonArrayWithOrderedJsonObject()
	{
	}

	protected JsonArrayWithOrderedJsonObject(final Collection<?> elements)
	{
		super(elements);
	}

	protected JsonArrayWithOrderedJsonObject(final JsonScanner jsonScanner)
	{
		super(jsonScanner);
	}

	protected JsonArrayWithOrderedJsonObject(final Object[] elements)
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

	public static JsonArrayWithOrderedJsonObject newJsonArray(final Object arrayObject)
	{
		final Object[] elements = convertToArrayIfArray(arrayObject);
		if (null == elements)
		{
			throw JsonStathamException.newJsonStathamException(
					"The given arrayObject is not an array object.[arrayObject: %s]", arrayObject);
		}
		return new JsonArrayWithOrderedJsonObject(elements);
	}

	public static JsonArrayWithOrderedJsonObject newJsonArray(final Object... elements)
	{
		return new JsonArrayWithOrderedJsonObject(elements);
	}

	public static JsonArrayWithOrderedJsonObject newJsonArray(final JsonScanner jsonScanner)
	{
		return new JsonArrayWithOrderedJsonObject(jsonScanner);
	}

	public static JsonArrayWithOrderedJsonObject newJsonArray(final String jsonString)
	{
		final JsonScanner jsonScanner = new JsonScannerForOrderedJsonObject(jsonString);
		return new JsonArrayWithOrderedJsonObject(jsonScanner);
	}
}
