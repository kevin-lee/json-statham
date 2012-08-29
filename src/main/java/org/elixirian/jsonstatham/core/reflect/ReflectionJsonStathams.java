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
package org.elixirian.jsonstatham.core.reflect;

import org.elixirian.jsonstatham.core.JsonStathamInAction;
import org.elixirian.jsonstatham.core.convertible.JsonArrayCreator;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithOrderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithUnorderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.OrderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.UnorderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.ReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.reflect.json2java.DefaultJsonToJavaConfig;
import org.elixirian.jsonstatham.core.reflect.json2java.JsonToJavaConfig;
import org.elixirian.jsonstatham.core.reflect.json2java.ReflectionJsonToJavaConverter;
import org.elixirian.kommonlee.util.CommonConstants;

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
 * @version 0.0.1 (2010-06-12)
 */
public final class ReflectionJsonStathams
{
	private ReflectionJsonStathams() throws IllegalAccessException
	{
		throw new IllegalAccessException(getClass().getName() + CommonConstants.CANNOT_BE_INSTANTIATED);
	}

	public static JsonStathamInAction newJsonStathamInAction(
			final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
			final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter)
	{
		return new JsonStathamInAction(reflectionJavaToJsonConverter, reflectionJsonToJavaConverter);
	}

	/**
	 * Returns JsonStathamInAction object created with ReflectionJavaToJsonConverter and ReflectionJsonToJavaConverter.
	 * The ReflectionJavaToJsonConverter object is created with OrgJsonOrderedJsonObjectCreator, OrgJsonJsonArrayCreator,
	 * KnownDataStructureTypeProcessorDecider, KnownObjectReferenceTypeProcessorDecider and
	 * OneProcessorForKnownTypeDecider.
	 *
	 * @return JsonStathamInAction object created with ReflectionJavaToJsonConverter and ReflectionJsonToJavaConverter.
	 */
	public static JsonStathamInAction newReflectionJsonStathamInAction()
	{
		final JsonObjectCreator orderedJsonObjectCreator = new OrderedJsonObjectCreator();
		final JsonArrayCreator jsonArrayCreator = new JsonArrayWithOrderedJsonObjectCreator();

		return newReflectionJsonStathamInAction(DefaultJsonToJavaConfig.builder(orderedJsonObjectCreator, jsonArrayCreator)
				.build());
	}

	/**
	 * Returns JsonStathamInAction object created with ReflectionJavaToJsonConverter and ReflectionJsonToJavaConverter.
	 * The ReflectionJavaToJsonConverter object is created with OrgJsonOrderedJsonObjectCreator, OrgJsonJsonArrayCreator,
	 * KnownDataStructureTypeProcessorDecider, KnownObjectReferenceTypeProcessorDecider and
	 * OneProcessorForKnownTypeDecider.
	 *
	 * @param jsonToJavaConfig
	 * @return JsonStathamInAction object created with ReflectionJavaToJsonConverter and ReflectionJsonToJavaConverter.
	 */
	public static JsonStathamInAction newReflectionJsonStathamInAction(final JsonToJavaConfig jsonToJavaConfig)
	{
		// final OrgJsonOrderedJsonObjectCreator orderedJsonObjectConvertibleCreator =
		// new OrgJsonOrderedJsonObjectCreator();
		// final OrgJsonJsonArrayCreator orgJsonJsonArrayConvertibleCreator =
		// new OrgJsonJsonArrayCreator();
		final JsonObjectCreator orderedJsonObjectCreator = new OrderedJsonObjectCreator();
		final JsonArrayCreator jsonArrayCreator = new JsonArrayWithOrderedJsonObjectCreator();

		final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter =
			new ReflectionJavaToJsonConverter(orderedJsonObjectCreator, jsonArrayCreator,
					new KnownDataStructureTypeProcessorDecider(), new KnownObjectReferenceTypeProcessorDecider(),
					new OneProcessorForKnownTypeDecider());

		final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter =
			new ReflectionJsonToJavaConverter(jsonToJavaConfig);

		return newJsonStathamInAction(reflectionJavaToJsonConverter, reflectionJsonToJavaConverter);
	}

	public static JsonStathamInAction newUnorderedReflectionJsonStathamInAction()
	{
		// final OrgJsonUnorderedJsonObjectCreator orgJsonUnorderedJsonObjectConvertibleCreator =
		// new OrgJsonUnorderedJsonObjectCreator();
		// final OrgJsonJsonArrayCreator orgJsonJsonArrayConvertibleCreator =
		// new OrgJsonJsonArrayCreator();
		final UnorderedJsonObjectCreator unorderedJsonObjectCreator = new UnorderedJsonObjectCreator();
		final JsonArrayCreator jsonArrayCreator = new JsonArrayWithUnorderedJsonObjectCreator();
		// final JsonArrayCreator orgJsonJsonArrayConvertibleCreator = new OrgJsonJsonArrayCreator();

		final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter =
			new ReflectionJavaToJsonConverter(unorderedJsonObjectCreator, jsonArrayCreator,
					new KnownDataStructureTypeProcessorDecider(), new KnownObjectReferenceTypeProcessorDecider(),
					new OneProcessorForKnownTypeDecider());

		final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter =
			new ReflectionJsonToJavaConverter(DefaultJsonToJavaConfig.builder(unorderedJsonObjectCreator, jsonArrayCreator)
					.build());

		return newJsonStathamInAction(reflectionJavaToJsonConverter, reflectionJsonToJavaConverter);
	}
}
