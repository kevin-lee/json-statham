/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.kommonlee.util.MessageFormatter.format;

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
 * @version 0.0.2 (2010-09-13)
 */
public final class OrgJsonJsonArrayConvertible implements JsonArray
{
	private final JSONArray orgJsonArray;

	public OrgJsonJsonArrayConvertible(final JSONArray orgJsonArray)
	{
		this.orgJsonArray = orgJsonArray;
	}

	@Override
	public Object get(final int index)
	{
		try
		{
			return orgJsonArray.get(index);
		}
		catch (final JSONException e)
		{
			throw new JsonStathamException(format("[input] int index: %s", Integer.valueOf(index)), e);
		}
	}

	@Override
	public <T> JsonArray put(final T value)
	{
		if (value instanceof JsonConvertible)
		{
			orgJsonArray.put(((JsonConvertible) value).getActualObject());
		}
		else
		{
			orgJsonArray.put(value);
		}
		return this;
	}

	@Override
	public int length()
	{
		return orgJsonArray.length();
	}

	@Override
	public Object getActualObject()
	{
		return orgJsonArray;
	}

	@Override
	public String toString()
	{
		return orgJsonArray.toString();
	}
}
