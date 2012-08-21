/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.kommonlee.util.MessageFormatter.*;

import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.json.JSONArray;
import org.json.JSONException;

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
 * @version 0.0.1 (2010-06-02)
 */
public final class OrgJsonJsonArrayCreator implements JsonArrayCreator
{

	@Override
	public JsonArray newJsonArrayConvertible()
	{
		return new OrgJsonJsonArray(new JSONArray());
	}

	@Override
	public JsonArray newJsonArrayConvertible(final String jsonString)
	{
		try
		{
			return new OrgJsonJsonArray(new JSONArray(jsonString));
		}
		catch (final JSONException e)
		{
			throw new JsonStathamException(format("[input] String jsonString: %s", jsonString), e);
		}
	}

}
