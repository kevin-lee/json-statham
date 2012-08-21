/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import java.util.LinkedHashMap;

import org.json.JSONObject;

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
 * @version 0.0.1 (2010-02-03)
 */
public class OrgJsonOrderedJsonObjectCreator extends AbstractOrgJsonJsonObjectConvertibleCreator
{
	@Override
	public JsonObject newJsonObjectConvertible()
	{
		return new OrgJsonJsonObject(new JSONObject(new LinkedHashMap<String, Object>()));
	}
}
