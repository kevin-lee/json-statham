/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original KommonLee project is owned by Lee, Seong Hyun (Kevin).
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

import java.util.Map;

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
public class UnorderedJsonObject extends AbstractJsonObject
{
	protected UnorderedJsonObject()
	{
		super(false);
	}

	protected UnorderedJsonObject(final JsonScanner jsonScanner)
	{
		super(jsonScanner, false);
	}

	protected UnorderedJsonObject(final Map<Object, Object> jsonFieldMap)
	{
		super(jsonFieldMap, false);
	}

	public UnorderedJsonObject(final Object javaBean)
	{
		super(javaBean, false);
	}

	public static UnorderedJsonObject newJsonObject()
	{
		return new UnorderedJsonObject();
	}

	public static UnorderedJsonObject newJsonObject(final Map<Object, Object> jsonFieldMap)
	{
		return new UnorderedJsonObject(jsonFieldMap);
	}

	public static UnorderedJsonObject newJsonObject(final JsonScanner jsonScanner)
	{
		return new UnorderedJsonObject(jsonScanner);
	}

	public static UnorderedJsonObject newJsonObject(final String jsonString)
	{
		final JsonScanner jsonScanner = new JsonScannerForUnorderedJsonObject(jsonString);
		return new UnorderedJsonObject(jsonScanner);
	}

	public static UnorderedJsonObject newJsonObject(final Object javaBean)
	{
		return new UnorderedJsonObject(javaBean);
	}
}
