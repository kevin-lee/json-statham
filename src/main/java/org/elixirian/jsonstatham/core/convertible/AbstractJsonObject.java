/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.jsonstatham.core.util.JsonUtil.*;
import static org.elixirian.kommonlee.util.MessageFormatter.*;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import org.elixirian.jsonstatham.core.util.JsonUtil;
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
public abstract class AbstractJsonObject implements JsonObjectConvertible
{
	public static final JsonObjectConvertible NULL_JSON_OBJECT = new JsonObjectConvertible() {
		@Override
		public JsonObjectConvertible put(final String name, final Object value) throws JsonStathamException
		{
			throw new JsonStathamException(format("The put method in NullJsonObject cannot used.\n"
					+ "[input] String name: %s, Object value: %s", name, value));
		}

		@Override
		public String[] getNames()
		{
			throw new JsonStathamException("The getNames method in NullJsonObject cannot used.");
		}

		@Override
		public Object getActualObject()
		{
			return this;
		}

		/* @formatter:off */
    @Override
    public boolean containsName(@SuppressWarnings("unused") final String name) { return false; }
    /* @formatter:on */

		@Override
		public Object get(final String name)
		{
			throw new JsonStathamException(format("The name method in NullJsonObject cannot used.\n[input] String name: %s",
					name));
		}

		@Override
		public int fieldLength()
		{
			return 0;
		}

		@Override
		public boolean isNull()
		{
			return true;
		}

		@Override
		public String toString()
		{
			return "null";
		}
	};

	private static final String[] EMPTY_NAMES = new String[0];

	private final Map<String, Object> jsonFieldMap;
	private final boolean ordered;

	protected AbstractJsonObject(final JsonScanner jsonScanner, final Map<String, Object> jsonFieldMap)
	{
		this(jsonFieldMap);
		// TODO: use jsonScanner
	}

	protected AbstractJsonObject(final Map<String, Object> jsonFieldMap)
	{
		this.ordered = (jsonFieldMap instanceof LinkedHashMap || jsonFieldMap instanceof SortedMap);
		this.jsonFieldMap = jsonFieldMap;
	}

	@Override
	public String[] getNames()
	{
		if (0 == jsonFieldMap.size())
			return EMPTY_NAMES;

		final Set<String> keySet = jsonFieldMap.keySet();
		return keySet.toArray(new String[keySet.size()]);
	}

	@Override
	public int fieldLength()
	{
		return jsonFieldMap.size();
	}

	@Override
	public boolean containsName(final String name)
	{
		return jsonFieldMap.containsKey(name);
	}

	@Override
	public Object get(final String name)
	{
		return jsonFieldMap.get(name);
	}

	@Override
	public JsonObjectConvertible put(final String name, final Object value) throws JsonStathamException
	{
		put0(name, value);
		return this;
	}

	private void put0(final String name, final Object value)
	{
		if (null == name)
			throw new JsonStathamException(format("The name must not be null.\n[input] String name: %s, Object value: %s]",
					name, value));

		validate(value);
		jsonFieldMap.put(name, value);
	}

	@Override
	public Object getActualObject()
	{
		return this;
	}

	@Override
	public boolean isNull()
	{
		return false;
	}

	@Override
	public String toString()
	{
		final StringBuilder stringBuilder = new StringBuilder("{");
		final Iterator<Entry<String, Object>> iterator = jsonFieldMap.entrySet()
				.iterator();

		if (iterator.hasNext())
		{
			final Entry<String, Object> field = iterator.next();

			final String value = JsonUtil.toStringValue(field.getValue(), this);

			stringBuilder.append(doubleQuote(field.getKey()))
					.append(':')
					.append(value);
		}

		while (iterator.hasNext())
		{
			final Entry<String, Object> field = iterator.next();
			final String value = JsonUtil.toStringValue(field.getValue(), this);
			stringBuilder.append(',')
					.append(doubleQuote(field.getKey()))
					.append(':')
					.append(value);
		}
		return stringBuilder.append('}')
				.toString();
	}
}
