/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.jsonstatham.core.util.JsonUtil.doubleQuote;
import static org.elixirian.jsonstatham.core.util.JsonUtil.validate;
import static org.elixirian.kommonlee.util.MessageFormatter.format;
import static org.elixirian.kommonlee.util.Objects.toStringOf;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.elixirian.jsonstatham.core.util.JsonUtil;
import org.elixirian.jsonstatham.exception.JsonStathamException;
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
public abstract class AbstractJsonObject implements JsonObject
{
	public static final JsonObject NULL_JSON_OBJECT = new JsonObject() {
		@Override
		public JsonObject put(final String name, final Object value) throws JsonStathamException
		{
			throw JsonStathamException.newJsonStathamException("The put method in NullJsonObject cannot used.\n"
					+ "[input] String name: %s, Object value: %s", name, value);
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
			throw JsonStathamException.newJsonStathamException(
					"The name method in NullJsonObject cannot used.\n[input] String name: %s", name);
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

	protected AbstractJsonObject(final JsonScanner jsonScanner, final boolean ordered)
	{
		this(ordered);
		map(jsonScanner, this.jsonFieldMap);
	}

	private void map(final JsonScanner jsonScanner, final Map<String, Object> jsonFieldMap) throws JsonStathamException
	{
		char c = jsonScanner.nextNonWhiteSpaceChar();
		if ('{' != c)
		{
			throw JsonStathamException.newJsonStathamException("Invalid JSON String found in the JsonScanner. "
					+ "It must start with { but does not.\n[char found:[int char: %s][char found: '%s']]%s", (int) c, c,
					jsonScanner.getPreviousCharInfo());
		}

		String key = null;
		while (true)
		{
			c = jsonScanner.nextNonWhiteSpaceChar();
			switch (c)
			{
				case 0:
					throw JsonStathamException.newJsonStathamException("Invalid JSON String found in the JsonScanner. "
							+ "It must end with } but does not.\n[char found:[int char: %s][char found: '%s']]%s", (int) c, c,
							jsonScanner.getPreviousCharInfo());
				case '}':
					/* an empty JSON object "{}" */
					return;
				default:
					jsonScanner.backToPrevious();
					key = toStringOf(jsonScanner.nextValue());
			}

			c = jsonScanner.nextNonWhiteSpaceChar();
			if ('=' == c)
			{
				if ('>' != jsonScanner.nextChar())
				{
					jsonScanner.backToPrevious();
				}
			}
			else if (':' != c)
			{
				throw JsonStathamException.newJsonStathamException(
						"The separator char that is : is expected after the key yet not found.\n{char found:[int char: %s][char found: '%s']}%s",
						(int) c, c, jsonScanner.getPreviousCharInfo());
			}

			if (jsonFieldMap.containsKey(key))
			{
				throw JsonStathamException.newJsonStathamException(
						"Duplicate key found!\nThe key [%s] already exists in this JSON object", key);
			}
			final Object value = jsonScanner.nextValue();
			if (null != key && null != value)
			{
				jsonFieldMap.put(key, value);
			}

			c = jsonScanner.nextNonWhiteSpaceChar();
			switch (c)
			{
				case ',':
				case ';':
					if ('}' == jsonScanner.nextNonWhiteSpaceChar())
					{
						return;
					}
					jsonScanner.backToPrevious();
					break;
				case '}':
					return;
				default:
					throw JsonStathamException.newJsonStathamException(
							", (line delimiter) or } is expected but neither is found.\n[char found:[int char: %s][char found: '%s']]%s",
							(int) c, c, jsonScanner.getPreviousCharInfo());
			}
		}
	}

	protected AbstractJsonObject(final boolean ordered)
	{
		this.ordered = ordered;
		this.jsonFieldMap = this.ordered ? Maps.<String, Object> newLinkedHashMap() : Maps.<String, Object> newHashMap();
	}

	protected AbstractJsonObject(final Map<Object, Object> jsonFieldMap, final boolean ordered)
	{
		this(ordered);
		for (final Entry<Object, Object> entry : jsonFieldMap.entrySet())
		{
			this.jsonFieldMap.put(toStringOf(entry.getKey()), JsonUtil.convert(entry.getValue(), this));
		}
	}

	protected AbstractJsonObject(final Object javaBean, final boolean ordered)
	{
		this(ordered);
		final Class<?> theClass = javaBean.getClass();
		final boolean includeSuperClass = theClass.getClassLoader() != null;

		final Method[] methods = includeSuperClass ? theClass.getMethods() : theClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++)
		{
			try
			{
				final Method method = methods[i];
				if (Modifier.isPublic(method.getModifiers()))
				{
					final String name = method.getName();
					String key = "";
					if (name.startsWith("get"))
					{
						/* @formatter:off */
						key = ("getClass".equals(name) || "getDeclaringClass".equals(name)) ?
										"" :
										name.substring(3);
						/* @formatter:on */
					}
					else if (name.startsWith("is"))
					{
						key = name.substring(2);
					}
					final int length = key.length();
					if (0 < length && Character.isUpperCase(key.charAt(0)) && 0 == method.getParameterTypes().length)
					{
						if (length == 1)
						{
							key = key.toLowerCase();
						}
						else
						{
							final boolean isSecondNotUpperCase = !Character.isUpperCase(key.charAt(1));

							if (2 == length)
							{
								if (isSecondNotUpperCase)
								{
									key = key.substring(0, 1)
											.toLowerCase() + key.substring(1);
								}
							}
							else
							{
								key = key.substring(0, 1)
										.toLowerCase() + key.substring(1);
							}
						}
						final Object result = method.invoke(javaBean, (Object[]) null);
						if (null != result)
						{
							this.jsonFieldMap.put(key, JsonUtil.convert(result, this));
						}
					}
				}
			}
			catch (final Throwable e)
			{
				/* Any exception (or throwable) should be ignored. */
			}
		}
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
	public JsonObject put(final String name, final Object value) throws JsonStathamException
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
