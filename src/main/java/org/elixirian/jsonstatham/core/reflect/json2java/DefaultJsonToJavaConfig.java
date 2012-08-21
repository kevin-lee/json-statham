/**
 * 
 */
package org.elixirian.jsonstatham.core.reflect.json2java;

import static org.elixirian.kommonlee.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.List;

import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava;
import org.elixirian.jsonstatham.core.convertible.JsonArrayCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObjectCreator;
import org.elixirian.kommonlee.type.GenericBuilder;

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
 * @version 0.0.1 (2012-07-18)
 */
public class DefaultJsonToJavaConfig implements JsonToJavaConfig
{
	private final JsonObjectCreator jsonObjectCreator;
	private final JsonArrayCreator jsonArrayCreator;

	private final List<KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>> knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList;

	private DefaultJsonToJavaConfig(final Builder builder)
	{
		this.jsonObjectCreator = builder.jsonObjectCreator;
		this.jsonArrayCreator = builder.jsonArrayCreator;
		this.knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList =
			Collections.unmodifiableList(builder.knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList);
	}

	public static class Builder implements GenericBuilder<JsonToJavaConfig>
	{
		final JsonObjectCreator jsonObjectCreator;
		final JsonArrayCreator jsonArrayCreator;

		List<KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>> knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList =
			newArrayList();

		public Builder(final JsonObjectCreator jsonObjectCreator,
				final JsonArrayCreator jsonArrayCreator)
		{
			this.jsonObjectCreator = jsonObjectCreator;
			this.jsonArrayCreator = jsonArrayCreator;
		}

		public Builder addKnownTypeProcessor(
				final KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>> knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava)
		{
			this.knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList.add(knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava);
			return this;
		}

		@Override
		public DefaultJsonToJavaConfig build()
		{
			return new DefaultJsonToJavaConfig(this);
		}
	}

	public static Builder builder(final JsonObjectCreator jsonObjectCreator,
			final JsonArrayCreator jsonArrayCreator)
	{
		return new Builder(jsonObjectCreator, jsonArrayCreator);
	}

	@Override
	public JsonObjectCreator getJsonObjectConvertibleCreator()
	{
		return jsonObjectCreator;
	}

	@Override
	public JsonArrayCreator getJsonArrayConvertibleCreator()
	{
		return jsonArrayCreator;
	}

	@Override
	public List<KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>> getKnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList()
	{
		return knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList;
	}
}
