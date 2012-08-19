/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.kommonlee.collect.Maps.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.elixirian.kommonlee.collect.Maps;

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
public class OrderedJsonObject extends AbstractJsonObject
{

	protected OrderedJsonObject(final JsonScanner jsonScanner)
	{
		super(jsonScanner, Maps.<String, Object> newLinkedHashMap());
	}

	protected OrderedJsonObject(final Map<String, Object> jsonFieldMap)
	{
		super(newLinkedHashMap(jsonFieldMap));
	}

	public static OrderedJsonObject newJsonObject(final Map<String, Object> fieldMap)
	{
		return new OrderedJsonObject(fieldMap);
	}

	public static OrderedJsonObject newJsonObject()
	{
		return new OrderedJsonObject(new LinkedHashMap<String, Object>());
	}

	public static OrderedJsonObject newJsonObject(final JsonScanner jsonScanner)
	{
		return new OrderedJsonObject(jsonScanner);
	}

	public static OrderedJsonObject newJsonObject(final String jsonString)
	{
		final JsonScannerForOrderedJsonObject jsonScanner = new JsonScannerForOrderedJsonObject(jsonString);
		return new OrderedJsonObject(jsonScanner);
	}

}
