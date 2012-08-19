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
public abstract class AbstractOrgJsonJsonObjectConvertibleCreator implements JsonObjectConvertibleCreator
{
	static final JsonObjectConvertible NULL_JSON_OBJECT_CONVERTIBLE = new JsonObjectConvertible() {

		@Override
		public String[] getNames()
		{
			throw new JsonStathamException("The getNames method in NullJsonObjectConvertible cannot used.");
		}

		@Override
		public int fieldLength()
		{
			return 0;
		}

		/* @formatter:off */
    @Override
    public boolean containsName(@SuppressWarnings("unused") final String name) { return false; }
    /* @formatter:on */

		@Override
		public Object get(final String name)
		{
			throw new JsonStathamException(format(
					"The name method in NullJsonObjectConvertible cannot used.\n[input] name: %s", name));
		}

		@Override
		public Object getActualObject()
		{
			return JSONObject.NULL;
		}

		@Override
		public JsonObjectConvertible put(final String name, final Object value) throws JsonStathamException
		{
			throw new JsonStathamException(format(
					"The put method in NullJsonObjectConvertible cannot used.\n[input] String name: %s, Object value: %s", name,
					value));
		}

		@Override
		public boolean isNull()
		{
			return true;
		}

		@Override
		public String toString()
		{
			return JSONObject.NULL.toString();
		}

	};

	@Override
	public JsonObjectConvertible newJsonObjectConvertible(final String jsonString) throws JsonStathamException
	{
		try
		{
			return new OrgJsonJsonObjectConvertible(new JSONObject(jsonString));
		}
		catch (final JSONException e)
		{
			throw new JsonStathamException(format("[input] String jsonString: %s", jsonString), e);
		}
	}

	@Override
	public abstract JsonObjectConvertible newJsonObjectConvertible();

	@Override
	public JsonObjectConvertible nullJsonObjectConvertible()
	{
		return NULL_JSON_OBJECT_CONVERTIBLE;
	}

}
