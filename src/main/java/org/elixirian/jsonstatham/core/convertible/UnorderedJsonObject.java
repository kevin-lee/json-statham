/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.kommonlee.collect.Maps.*;

import java.util.HashMap;
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
public class UnorderedJsonObject extends AbstractJsonObject
{

	protected UnorderedJsonObject(final JsonScanner jsonScanner)
	{
		super(jsonScanner, Maps.<String, Object> newHashMap());
	}

	protected UnorderedJsonObject(final Map<String, Object> jsonFieldMap)
	{
		super(newHashMap(jsonFieldMap));
	}

	public static UnorderedJsonObject newJsonObject()
	{
		return new UnorderedJsonObject(new HashMap<String, Object>());
	}

	public static UnorderedJsonObject newJsonObject(final Map<String, Object> jsonFieldMap)
	{
		return new UnorderedJsonObject(jsonFieldMap);
	}

	public static UnorderedJsonObject newJsonObject(final JsonScanner jsonScanner)
	{
		return new UnorderedJsonObject(jsonScanner);
	}

	public static UnorderedJsonObject newJsonObject(final String jsonString)
	{
		final JsonScanner jsonScanner = new JsonScannerForUnorderedJsonObject(jsonString);
		return new UnorderedJsonObject(jsonScanner);
	}
}
