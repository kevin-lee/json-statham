/**
 * 
 */
package org.elixirian.jsonstatham.core.reflect.json2java;

import static org.elixirian.kommonlee.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.List;

import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava;
import org.elixirian.jsonstatham.core.convertible.JsonArrayConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
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
	private final JsonObjectConvertibleCreator jsonObjectConvertibleCreator;
	private final JsonArrayConvertibleCreator jsonArrayConvertibleCreator;

	private final List<KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>> knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList;

	private DefaultJsonToJavaConfig(final Builder builder)
	{
		this.jsonObjectConvertibleCreator = builder.jsonObjectConvertibleCreator;
		this.jsonArrayConvertibleCreator = builder.jsonArrayConvertibleCreator;
		this.knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList =
			Collections.unmodifiableList(builder.knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList);
	}

	public static class Builder implements GenericBuilder<JsonToJavaConfig>
	{
		final JsonObjectConvertibleCreator jsonObjectConvertibleCreator;
		final JsonArrayConvertibleCreator jsonArrayConvertibleCreator;

		List<KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>> knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList =
			newArrayList();

		public Builder(final JsonObjectConvertibleCreator jsonObjectConvertibleCreator,
				final JsonArrayConvertibleCreator jsonArrayConvertibleCreator)
		{
			this.jsonObjectConvertibleCreator = jsonObjectConvertibleCreator;
			this.jsonArrayConvertibleCreator = jsonArrayConvertibleCreator;
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

	public static Builder builder(final JsonObjectConvertibleCreator jsonObjectConvertibleCreator,
			final JsonArrayConvertibleCreator jsonArrayConvertibleCreator)
	{
		return new Builder(jsonObjectConvertibleCreator, jsonArrayConvertibleCreator);
	}

	@Override
	public JsonObjectConvertibleCreator getJsonObjectConvertibleCreator()
	{
		return jsonObjectConvertibleCreator;
	}

	@Override
	public JsonArrayConvertibleCreator getJsonArrayConvertibleCreator()
	{
		return jsonArrayConvertibleCreator;
	}

	@Override
	public List<KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>> getKnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList()
	{
		return knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList;
	}
}
