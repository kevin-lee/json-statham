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
package org.elixirian.jsonstatham.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.ValueAccessor;
import org.elixirian.jsonstatham.core.convertible.JsonArray;
import org.elixirian.jsonstatham.core.convertible.JsonConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.JsonObjectCreator;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.io.CharAndStringWritable;
import org.elixirian.kommonlee.io.CharAndStringWritableToOutputStream;
import org.elixirian.kommonlee.io.CharAndStringWritableToWriter;
import org.elixirian.kommonlee.io.CharReadable;
import org.elixirian.kommonlee.io.CharReadableFromInputStream;
import org.elixirian.kommonlee.io.CharReadableFromReader;
import org.elixirian.kommonlee.io.util.IoUtil;
import org.elixirian.kommonlee.reflect.TypeHolder;

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
 * @version 0.0.1 (2009-11-21)
 * @version 0.0.2 (2009-12-07) It is refactored.
 * @version 0.0.3 (2009-12-07) It is redesigned.
 * @version 0.0.4 (2009-12-12) It can handle array, List and Map.
 * @version 0.0.5 (2009-12-20)
 *          <p>
 *          It can handle duplicate {@link JsonField} names. => It throws an exception.
 *          </p>
 *          <p>
 *          It can also handle {@link java.util.Date} type value annotated with {@link JsonField}. => It uses the
 *          toString() method of the value object, or if the field is also annotated with {@link ValueAccessor}
 *          annotation, it uses the method specified with the {@link ValueAccessor} annotation in order to get the
 *          value.
 *          </p>
 * @version 0.0.6 (2010-02-03) {@link JsonObjectCreator} is added to create a new {@link org.json.JSONObject} .
 * @version 0.0.7 (2010-02-12) The name is changed from NonIndentedJsonStatham to JsonStathamInAction. When the Json is
 *          converted into JSON, if any fields annotated with @JsonField without the 'name' element explicitly set, it
 *          will use the actual field names as the JsonField names.
 * @version 0.0.8 (2010-03-02) refactoring...
 * @version 0.0.9 (2010-03-06)
 *          <ul>
 *          <li>It can process {@link java.util.Iterator}, {@link java.lang.Iterable} and {@link java.util.Map.Entry}.</li>
 *          <li>If there is no explicit @ValueAccessor name, it uses the getter name that is get + the field name (e.g.
 *          field name: name => getName / field name: id => getId).</li>
 *          <li>It can handle proxied objects created by javassist.</li>
 *          <li>It ignores any super classes of the given JSON object if the classes are not annotated with the
 *          {@link Json} annotation.</li>
 *          </ul>
 * @version 0.0.10 (2010-03-07) It does not throw an exception when the given JSON object has a proxied object created
 *          by javassist as a field value. Instead it tries to find any JSON objects from its super classes.
 * @version 0.0.11 (2010-03-14) If the {@link ValueAccessor} without its name explicitly set is used on a field and the
 *          field type is <code>boolean</code> or {@link Boolean}, it tries to get the value by calling isField() method
 *          that is "is" + the field name instead of "get" + the field name.
 * @version 0.0.12 (2010-04-20) refactoring...
 * @version 0.0.13 (2010-05-10) It can handle enum type fields (it uses enumType.toString() method to use the returned
 *          String as the value of the field).
 * @version 0.0.14 (2010-06-02) The following types are not used anymore.
 *          <p>
 *          {@link org.json.JSONObject} and {@link org.json.JSONArray}
 *          <p>
 *          These are replaced by {@link JsonObject} and {@link JsonArray} respectively.
 * @version 0.0.15 (2010-06-10) known types are injectable (more extensible design).
 * @version 0.0.16 (2010-06-14) refactoring...
 * @version 0.1.0 (2010-09-08) {@link #convertFromJson(Class, String)} is added.
 * @version 0.1.0 (2010-10-09) {@link #convertFromJson(TypeHolder, String)} is added.
 */
public class JsonStathamInAction implements JsonStatham
{
  private final JavaToJsonConverter javaToJsonConverter;
  private final JsonToJavaConverter jsonToJavaConverter;

  public JsonStathamInAction(final JavaToJsonConverter javaToJsonConverter, final JsonToJavaConverter jsonToJavaConverter)
  {
    this.javaToJsonConverter = javaToJsonConverter;
    this.jsonToJavaConverter = jsonToJavaConverter;
  }

  public JavaToJsonConverter getJavaToJsonConverter()
  {
    return javaToJsonConverter;
  }

  public JsonToJavaConverter getJsonToJavaConverter()
  {
    return jsonToJavaConverter;
  }

  @Override
  public String convertIntoJson(final Object source) throws JsonStathamException
  {
    try
    {
      return javaToJsonConverter.convertIntoJson(source);
    }
    catch (final IllegalArgumentException e)
    {
      // throw new JsonStathamException(format(
      // "Wrong object [object: %s] is passed or it has illegal fields with the @JsonField annotation",
      // source), e);
      throw new JsonStathamException(e);
    }
    catch (final IllegalAccessException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final JsonStathamException e)
    {
      throw e;
    }
  }

  @Override
  public <T extends JsonConvertible> T convertIntoJsonConvertible(final Object source) throws JsonStathamException
  {
    try
    {
      return javaToJsonConverter.convertIntoJsonConvertible(source);
    }
    catch (final IllegalArgumentException e)
    {
      // throw new JsonStathamException(format(
      // "Wrong object [object: %s] is passed or it has illegal fields with the @JsonField annotation",
      // source), e);
      throw new JsonStathamException(e);
    }
    catch (final IllegalAccessException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final JsonStathamException e)
    {
      throw e;
    }
  }

  @Override
  public CharAndStringWritable convertIntoJsonWriteAndGetWriter(final Object source, final CharAndStringWritable charAndStringWritable)
      throws JsonStathamException
  {
    try
    {
      javaToJsonConverter.convertIntoJsonAndWrite(source, charAndStringWritable);
      return charAndStringWritable;
    }
    catch (final IllegalArgumentException e)
    {
      // throw new JsonStathamException(format(
      // "Wrong object [object: %s] is passed or it has illegal fields with the @JsonField annotation",
      // source), e);
      throw new JsonStathamException(e);
    }
    catch (final IllegalAccessException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final JsonStathamException e)
    {
      throw e;
    }
  }

  @Override
  public CharAndStringWritable convertIntoJsonWriteAndGetWriter(final Object source, final Writer writer) throws JsonStathamException
  {
    return convertIntoJsonWriteAndGetWriter(source, new CharAndStringWritableToWriter(writer));
  }

  @Override
  public CharAndStringWritable convertIntoJsonWriteAndGetWriter(final Object source, final OutputStream outputStream)
      throws JsonStathamException
  {
    return convertIntoJsonWriteAndGetWriter(source, new CharAndStringWritableToOutputStream(outputStream));
  }

  @Override
  public void convertIntoJsonAndWrite(final Object source, final CharAndStringWritable charAndStringWritable) throws JsonStathamException
  {
    try
    {
      convertIntoJsonWriteAndGetWriter(source, charAndStringWritable)
      .flush();
    }
    catch (final IllegalArgumentException e)
    {
      // throw new JsonStathamException(format(
      // "Wrong object [object: %s] is passed or it has illegal fields with the @JsonField annotation",
      // source), e);
      throw new JsonStathamException(e);
    }
    finally
    {
      IoUtil.closeQuietly(charAndStringWritable);
    }
  }

  @Override
  public void convertIntoJsonAndWrite(final Object source, final Writer writer) throws JsonStathamException
  {
    convertIntoJsonAndWrite(source, new CharAndStringWritableToWriter(writer));
  }

  @Override
  public void convertIntoJsonAndWrite(final Object source, final OutputStream outputStream)
  {
    convertIntoJsonAndWrite(source, new CharAndStringWritableToOutputStream(outputStream));
  }

  @Override
  public JsonConvertible convertJsonStringIntoJsonConvertible(final String json) throws JsonStathamException
  {
    return jsonToJavaConverter.convertJsonStringIntoJsonConvertible(json);
  }

  @Override
  public <T> T convertFromJson(final Class<T> type, final String json) throws JsonStathamException
  {
    try
    {
      return jsonToJavaConverter.convertFromJson(type, json);
    }
    catch (final IllegalArgumentException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final InstantiationException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final IllegalAccessException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final InvocationTargetException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final JsonStathamException e)
    {
      throw e;
    }
  }

  @Override
  public <T> T convertFromJson(final TypeHolder<T> typeHolder, final String jsonString) throws JsonStathamException
  {
    try
    {
      return jsonToJavaConverter.convertFromJson(typeHolder, jsonString);
    }
    catch (final IllegalArgumentException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final InstantiationException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final IllegalAccessException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final InvocationTargetException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final JsonStathamException e)
    {
      throw e;
    }
  }

  @Override
  public <T> T convertFromJsonConvertible(final Class<T> type, final JsonConvertible jsonConvertible) throws JsonStathamException
  {
    try
    {
      return jsonToJavaConverter.convertFromJsonConvertible(type, jsonConvertible);
    }
    catch (final IllegalArgumentException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final InstantiationException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final IllegalAccessException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final InvocationTargetException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final JsonStathamException e)
    {
      throw e;
    }
  }

  @Override
  public <T> T convertFromJsonConvertible(final TypeHolder<T> typeHolder, final JsonConvertible jsonConvertible)
      throws JsonStathamException
  {
    try
    {
      return jsonToJavaConverter.convertFromJsonConvertible(typeHolder, jsonConvertible);
    }
    catch (final IllegalArgumentException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final InstantiationException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final IllegalAccessException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final InvocationTargetException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final JsonStathamException e)
    {
      throw e;
    }
  }

  @Override
  public <T> T convertFromJson(final Class<T> type, final CharReadable charReadable)
  {
    try
    {
      return jsonToJavaConverter.convertFromJson(type, charReadable);
    }
    catch (final IllegalArgumentException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final InstantiationException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final IllegalAccessException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final InvocationTargetException e)
    {
      throw new JsonStathamException(e);
    }
    catch (final JsonStathamException e)
    {
      throw e;
    }
    finally
    {
      IoUtil.closeQuietly(charReadable);
    }
  }

  @Override
  public <T> T convertFromJson(final Class<T> type, final Reader reader)
  {
    return convertFromJson(type, new CharReadableFromReader(reader));
  }

  @Override
  public <T> T convertFromJson(final Class<T> type, final InputStream inputStream)
  {
    return convertFromJson(type, new CharReadableFromInputStream(inputStream));
  }

}