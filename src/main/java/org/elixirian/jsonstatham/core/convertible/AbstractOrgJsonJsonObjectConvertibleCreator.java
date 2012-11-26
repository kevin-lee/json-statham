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
public abstract class AbstractOrgJsonJsonObjectConvertibleCreator implements JsonObjectCreator
{
	static final JsonObject NULL_JSON_OBJECT_CONVERTIBLE = new JsonObject() {

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
		public <T> T get(final String name)
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
		public <T> JsonObject put(final String name, final T value) throws JsonStathamException
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
	public JsonObject newJsonObjectConvertible(final String jsonString) throws JsonStathamException
	{
		try
		{
			return new OrgJsonJsonObject(new JSONObject(jsonString));
		}
		catch (final JSONException e)
		{
			throw new JsonStathamException(format("[input] String jsonString: %s", jsonString), e);
		}
	}

	@Override
	public abstract JsonObject newJsonObjectConvertible();

	@Override
	public JsonObject nullJsonObjectConvertible()
	{
		return NULL_JSON_OBJECT_CONVERTIBLE;
	}

}
