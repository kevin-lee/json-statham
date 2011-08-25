/**
 * 
 */
package org.elixirian.jsonstatham.core;

import java.lang.reflect.InvocationTargetException;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.JsonObject;
import org.elixirian.jsonstatham.annotation.ValueAccessor;
import org.elixirian.jsonstatham.core.convertible.JsonArrayConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.reflect.TypeHolder;

/**
 * <pre>
 *     ___  _____  __________  ___________ _____  ____
 *    /   \/    / /      \   \/   /_    _//     \/   /
 *   /        /  /    ___/\      / /   / /          /
 *  /        \  /    ___/  \    /_/   /_/          /
 * /____/\____\/_______/    \__//______/___/\_____/
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
 * @version 0.0.6 (2010-02-03) {@link JsonObjectConvertibleCreator} is added to create a new {@link org.json.JSONObject}
 *          .
 * @version 0.0.7 (2010-02-12) The name is changed from NonIndentedJsonStatham to JsonStathamInAction. When the
 *          JsonObject is converted into JSON, if any fields annotated with @JsonField without the 'name' element
 *          explicitly set, it will use the actual field names as the JsonField names.
 * @version 0.0.8 (2010-03-02) refactoring...
 * @version 0.0.9 (2010-03-06)
 *          <ul>
 *          <li>It can process {@link java.util.Iterator}, {@link java.lang.Iterable} and {@link java.util.Map.Entry}.</li>
 *          <li>If there is no explicit @ValueAccessor name, it uses the getter name that is get + the field name (e.g.
 *          field name: name => getName / field name: id => getId).</li>
 *          <li>It can handle proxied objects created by javassist.</li>
 *          <li>It ignores any super classes of the given JSON object if the classes are not annotated with the
 *          {@link JsonObject} annotation.</li>
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
 *          These are replaced by {@link JsonObjectConvertible} and {@link JsonArrayConvertible} respectively.
 * @version 0.0.15 (2010-06-10) known types are injectable (more extensible design).
 * @version 0.0.16 (2010-06-14) refactoring...
 * @version 0.1.0 (2010-09-08) {@link #convertFromJson(Class, String)} is added.
 * @version 0.1.0 (2010-10-09) {@link #convertFromJson(TypeHolder, String)} is added.
 */
public class JsonStathamInAction implements JsonStatham
{
  private final JavaToJsonConverter javaToJsonConverter;
  private final JsonToJavaConverter jsonToJavaConverter;

  public JsonStathamInAction(JavaToJsonConverter javaToJsonConverter, JsonToJavaConverter jsonToJavaConverter)
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
  public String convertIntoJson(Object source) throws JsonStathamException
  {
    try
    {
      return javaToJsonConverter.convertIntoJson(source);
    }
    catch (IllegalArgumentException e)
    {
      // throw new JsonStathamException(format(
      // "Wrong object [object: %s] is passed or it has illegal fields with the @JsonField annotation",
      // source), e);
      throw new JsonStathamException(e);
    }
    catch (IllegalAccessException e)
    {
      throw new JsonStathamException(e);
    }
    catch (JsonStathamException e)
    {
      throw e;
    }
  }

  @Override
  public <T> T convertFromJson(Class<T> type, String json) throws JsonStathamException
  {
    try
    {
      return jsonToJavaConverter.convertFromJson(type, json);
    }
    catch (IllegalArgumentException e)
    {
      throw new JsonStathamException(e);
    }
    catch (InstantiationException e)
    {
      throw new JsonStathamException(e);
    }
    catch (IllegalAccessException e)
    {
      throw new JsonStathamException(e);
    }
    catch (InvocationTargetException e)
    {
      throw new JsonStathamException(e);
    }
    catch (JsonStathamException e)
    {
      throw e;
    }
  }

  @Override
  public <T> T convertFromJson(TypeHolder<T> typeHolder, String jsonString) throws JsonStathamException
  {
    try
    {
      return jsonToJavaConverter.convertFromJson(typeHolder, jsonString);
    }
    catch (IllegalArgumentException e)
    {
      throw new JsonStathamException(e);
    }
    catch (InstantiationException e)
    {
      throw new JsonStathamException(e);
    }
    catch (IllegalAccessException e)
    {
      throw new JsonStathamException(e);
    }
    catch (InvocationTargetException e)
    {
      throw new JsonStathamException(e);
    }
    catch (JsonStathamException e)
    {
      throw e;
    }
  }
}