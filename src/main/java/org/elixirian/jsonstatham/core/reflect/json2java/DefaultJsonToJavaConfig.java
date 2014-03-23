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
package org.elixirian.jsonstatham.core.reflect.json2java;

import static org.elixirian.kommonlee.util.collect.Lists.*;

import java.util.Collections;
import java.util.List;

import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava;
import org.elixirian.jsonstatham.core.convertible.JsonArrayCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonScannerCreator;
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
  private final JsonScannerCreator jsonScannerCreator;
  private final JsonObjectCreator jsonObjectCreator;
  private final JsonArrayCreator jsonArrayCreator;

  private final List<KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>> knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList;

  private DefaultJsonToJavaConfig(final Builder builder)
  {
    this.jsonScannerCreator = builder.jsonScannerCreator;
    this.jsonObjectCreator = builder.jsonObjectCreator;
    this.jsonArrayCreator = builder.jsonArrayCreator;
    this.knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList =
      Collections.unmodifiableList(builder.knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList);
  }

  public static class Builder implements GenericBuilder<JsonToJavaConfig>
  {
    final JsonScannerCreator jsonScannerCreator;
    final JsonObjectCreator jsonObjectCreator;
    final JsonArrayCreator jsonArrayCreator;

    List<KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>> knownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJavaList =
      newArrayList();

    public Builder(final JsonScannerCreator jsonScannerCreator, final JsonObjectCreator jsonObjectCreator,
        final JsonArrayCreator jsonArrayCreator)
    {
      this.jsonScannerCreator = jsonScannerCreator;
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

  public static Builder builder(final JsonScannerCreator jsonScannerCreator, final JsonObjectCreator jsonObjectCreator,
      final JsonArrayCreator jsonArrayCreator)
  {
    return new Builder(jsonScannerCreator, jsonObjectCreator, jsonArrayCreator);
  }

  @Override
  public JsonScannerCreator getJsonScannerCreator()
  {
    return jsonScannerCreator;
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
