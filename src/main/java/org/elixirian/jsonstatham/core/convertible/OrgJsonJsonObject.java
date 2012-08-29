/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original JSON Statham project is owned by Lee, Seong Hyun (Kevin).
 *
 * -What does it mean to you?
 * Nothing, unless you want to take the ownership of
 * "the original project" (not yours or forked & modified one).
 * You are free to use it for both non-commercial and commercial projects
 * and free to modify it as the Apache License allows.
 *
 * -So why is this condition necessary?
 * It is only to protect the original project (See the case of Java).
 *
 *
 * Copyright 2009 Lee, Seong Hyun (Kevin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
