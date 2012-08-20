/**
 *
 */
package org.elixirian.jsonstatham.core.reflect;

import org.elixirian.jsonstatham.core.JsonStathamInAction;
import org.elixirian.jsonstatham.core.convertible.JsonArrayConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithOrderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithUnorderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.OrderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.UnorderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.ReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.reflect.json2java.DefaultJsonToJavaConfig;
import org.elixirian.jsonstatham.core.reflect.json2java.JsonToJavaConfig;
import org.elixirian.jsonstatham.core.reflect.json2java.ReflectionJsonToJavaConverter;

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
	private ReflectionJsonStathams()
	{
	}

	public static JsonStathamInAction newJsonStathamInAction(
			final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
			final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter)
	{
		return new JsonStathamInAction(reflectionJavaToJsonConverter, reflectionJsonToJavaConverter);
	}

	/**
	 * Returns JsonStathamInAction object created with ReflectionJavaToJsonConverter and ReflectionJsonToJavaConverter.
	 * The ReflectionJavaToJsonConverter object is created with OrgJsonOrderedJsonObjectConvertibleCreator,
	 * OrgJsonJsonArrayConvertibleCreator, KnownDataStructureTypeProcessorDecider,
	 * KnownObjectReferenceTypeProcessorDecider and OneProcessorForKnownTypeDecider.
	 *
	 * @return JsonStathamInAction object created with ReflectionJavaToJsonConverter and ReflectionJsonToJavaConverter.
	 */
	public static JsonStathamInAction newReflectionJsonStathamInAction()
	{
		final JsonObjectConvertibleCreator orderedJsonObjectCreator = new OrderedJsonObjectCreator();
		final JsonArrayConvertibleCreator jsonArrayConvertibleCreator = new JsonArrayWithOrderedJsonObjectCreator();

		return newReflectionJsonStathamInAction(DefaultJsonToJavaConfig.builder(orderedJsonObjectCreator,
				jsonArrayConvertibleCreator)
				.build());
	}

	/**
	 * Returns JsonStathamInAction object created with ReflectionJavaToJsonConverter and ReflectionJsonToJavaConverter.
	 * The ReflectionJavaToJsonConverter object is created with OrgJsonOrderedJsonObjectConvertibleCreator,
	 * OrgJsonJsonArrayConvertibleCreator, KnownDataStructureTypeProcessorDecider,
	 * KnownObjectReferenceTypeProcessorDecider and OneProcessorForKnownTypeDecider.
	 *
	 * @param jsonToJavaConfig
	 * @return JsonStathamInAction object created with ReflectionJavaToJsonConverter and ReflectionJsonToJavaConverter.
	 */
	public static JsonStathamInAction newReflectionJsonStathamInAction(final JsonToJavaConfig jsonToJavaConfig)
	{
		// final OrgJsonOrderedJsonObjectConvertibleCreator orderedJsonObjectConvertibleCreator =
		// new OrgJsonOrderedJsonObjectConvertibleCreator();
		// final OrgJsonJsonArrayConvertibleCreator orgJsonJsonArrayConvertibleCreator =
		// new OrgJsonJsonArrayConvertibleCreator();
		final JsonObjectConvertibleCreator orderedJsonObjectCreator = new OrderedJsonObjectCreator();
		final JsonArrayConvertibleCreator jsonArrayCreator = new JsonArrayWithOrderedJsonObjectCreator();

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
		// final OrgJsonUnorderedJsonObjectConvertibleCreator orgJsonUnorderedJsonObjectConvertibleCreator =
		// new OrgJsonUnorderedJsonObjectConvertibleCreator();
		// final OrgJsonJsonArrayConvertibleCreator orgJsonJsonArrayConvertibleCreator =
		// new OrgJsonJsonArrayConvertibleCreator();
		final UnorderedJsonObjectCreator unorderedJsonObjectCreator = new UnorderedJsonObjectCreator();
		final JsonArrayConvertibleCreator jsonArrayCreator = new JsonArrayWithUnorderedJsonObjectCreator();
		// final JsonArrayConvertibleCreator orgJsonJsonArrayConvertibleCreator = new OrgJsonJsonArrayConvertibleCreator();

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
