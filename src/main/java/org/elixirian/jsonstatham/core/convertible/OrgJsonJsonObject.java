/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.kommonlee.util.MessageFormatter.*;

import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.json.JSONException;
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
 * @version 0.0.1 (2010-06-02)
 */
public final class OrgJsonJsonObject implements JsonObject
{
	private static final String[] EMPTY_NAMES = new String[0];

	private final JSONObject orgJsonObject;

	public OrgJsonJsonObject(final JSONObject orgJsonObject)
	{
		this.orgJsonObject = orgJsonObject;
	}

	@Override
	public String[] getNames()
	{
		final String[] names = JSONObject.getNames(orgJsonObject);
		return (null == names ? EMPTY_NAMES : names);
	}

	@Override
	public int fieldLength()
	{
		return orgJsonObject.length();
	}

	@Override
	public boolean containsName(final String name)
	{
		return orgJsonObject.has(name);
	}

	@Override
	public Object get(final String name) throws JsonStathamException
	{
		try
		{
			return orgJsonObject.get(name);
		}
		catch (final JSONException e)
		{
			throw new JsonStathamException(format("[input] String name: %s", name), e);
		}
	}

	@Override
	public JsonObject put(final String name, final Object value) throws JsonStathamException
	{
		try
		{
			if (value instanceof JsonConvertible)
			{
				final Object actualObject = ((JsonConvertible) value).getActualObject();
				orgJsonObject.put(name, actualObject);
			}
			else
			{
				orgJsonObject.put(name, value);
			}
			return this;
		}
		catch (final JSONException e)
		{
			throw new JsonStathamException(format("[input] String name: %s, Object value: %s", name, value), e);
		}
	}

	@Override
	public Object getActualObject()
	{
		return orgJsonObject;
	}

	@Override
	public boolean isNull()
	{
		return false;
	}

	@Override
	public String toString()
	{
		return orgJsonObject.toString();
	}
}
