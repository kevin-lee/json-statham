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
public class OrderedJsonObject extends AbstractJsonObject
{
	protected OrderedJsonObject()
	{
		super(true);
	}

	protected OrderedJsonObject(final JsonScanner jsonScanner)
	{
		super(jsonScanner, true);
	}

	protected OrderedJsonObject(final Map<Object, Object> jsonFieldMap)
	{
		super(jsonFieldMap, true);
	}

	protected OrderedJsonObject(final Object javaBean)
	{
		super(javaBean, true);
	}

	public static OrderedJsonObject newJsonObject(final Map<Object, Object> fieldMap)
	{
		return new OrderedJsonObject(fieldMap);
	}

	public static OrderedJsonObject newJsonObject()
	{
		return new OrderedJsonObject();
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

	public static OrderedJsonObject newJsonObject(final Object javaBean)
	{
		return new OrderedJsonObject(javaBean);
	}

}
