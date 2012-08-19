/**
 *
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.kommonlee.collect.Lists.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.elixirian.jsonstatham.core.util.JsonUtil;

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
public abstract class JsonArray implements JsonArrayConvertible
{
	private final List<Object> list;

	protected JsonArray()
	{
		this.list = newArrayList();
	}

	protected JsonArray(final JsonScanner jsonScanner)
	{
		this.list = newArrayList();
		// TODO: do the rest!!!
	}

	protected JsonArray(final Collection<?> elements)
	{
		this.list = newArrayList(elements);
	}

	protected JsonArray(final Object[] elements)
	{
		this.list = newArrayList(elements);
	}

	@Override
	public Object get(final int index)
	{
		return list.get(index);
	}

	@Override
	public <T> JsonArrayConvertible put(final T value)
	{
		list.add(value);
		return this;
	}

	@Override
	public int length()
	{
		return list.size();
	}

	@Override
	public Object getActualObject()
	{
		return this;
	}

	@Override
	public String toString()
	{
		final StringBuilder stringBuilder = new StringBuilder("[");
		final Iterator<Object> iterator = list.iterator();

		if (iterator.hasNext())
		{
			final String value = JsonUtil.toStringValue(iterator.next(), this);
			stringBuilder.append(value);
		}

		while (iterator.hasNext())
		{
			final String value = JsonUtil.toStringValue(iterator.next(), this);
			stringBuilder.append(',')
					.append(value);
		}
		return stringBuilder.append(']')
				.toString();
	}
}
