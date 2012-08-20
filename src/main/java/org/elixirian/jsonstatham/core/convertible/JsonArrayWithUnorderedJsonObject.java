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
public class JsonArrayWithUnorderedJsonObject extends AbstractJsonArray
{

	protected JsonArrayWithUnorderedJsonObject()
	{
	}

	protected JsonArrayWithUnorderedJsonObject(final Collection<?> elements)
	{
		super(elements);
	}

	protected JsonArrayWithUnorderedJsonObject(final JsonScanner jsonScanner)
	{
		super(jsonScanner);
	}

	protected JsonArrayWithUnorderedJsonObject(final Object[] elements)
	{
		super(elements);
	}

	public static JsonArrayWithUnorderedJsonObject newJsonArray(final Object arrayObject)
	{
		final Object[] elements = convertToArrayIfArray(arrayObject);
		if (null == elements)
		{
			throw JsonStathamException.newJsonStathamException(
					"The given arrayObject is not an array object.[arrayObject: %s]", arrayObject);
		}
		return new JsonArrayWithUnorderedJsonObject(elements);
	}

	public static JsonArrayWithUnorderedJsonObject newJsonArray()
	{
		return new JsonArrayWithUnorderedJsonObject();
	}

	public static JsonArrayWithUnorderedJsonObject newJsonArray(final Collection<?> elements)
	{
		return new JsonArrayWithUnorderedJsonObject(elements);
	}

	public static JsonArrayWithUnorderedJsonObject newJsonArray(final Object... elements)
	{
		return new JsonArrayWithUnorderedJsonObject(elements);
	}

	public static JsonArrayWithUnorderedJsonObject newJsonArray(final JsonScanner jsonScanner)
	{
		return new JsonArrayWithUnorderedJsonObject(jsonScanner);
	}

	public static JsonArrayWithUnorderedJsonObject newJsonArray(final String jsonString)
	{
		final JsonScanner jsonScanner = new JsonScannerForUnorderedJsonObject(jsonString);
		return new JsonArrayWithUnorderedJsonObject(jsonScanner);
	}
}
