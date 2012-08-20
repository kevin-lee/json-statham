/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import java.util.Map;

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
	protected UnorderedJsonObject()
	{
		super(false);
	}

	protected UnorderedJsonObject(final JsonScanner jsonScanner)
	{
		super(jsonScanner, false);
	}

	protected UnorderedJsonObject(final Map<Object, Object> jsonFieldMap)
	{
		super(jsonFieldMap, false);
	}

	public UnorderedJsonObject(final Object javaBean)
	{
		super(javaBean, false);
	}

	public static UnorderedJsonObject newJsonObject()
	{
		return new UnorderedJsonObject();
	}

	public static UnorderedJsonObject newJsonObject(final Map<Object, Object> jsonFieldMap)
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

	public static UnorderedJsonObject newJsonObject(final Object javaBean)
	{
		return new UnorderedJsonObject(javaBean);
	}
}
