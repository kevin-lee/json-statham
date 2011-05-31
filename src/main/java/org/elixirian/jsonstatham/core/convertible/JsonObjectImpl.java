/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.common.util.MessageFormatter.*;
import static org.elixirian.jsonstatham.core.util.JsonUtil.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.elixirian.jsonstatham.exception.JsonStathamException;

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
 * @version 0.0.1 (2010-12-25)
 */
public class JsonObjectImpl implements JsonObjectConvertible
{
  public static final JsonObjectConvertible NULL_JSON_OBJECT = new JsonObjectConvertible() {
    @Override
    public JsonObjectConvertible put(final String name, final Object value) throws JsonStathamException
    {
      throw new JsonStathamException(format("The put method in NullJsonObject cannot used.\n"
          + "[input] String name: %s, Object value: %s", name, value));
    }

    @Override
    public String[] getNames()
    {
      throw new JsonStathamException("The getNames method in NullJsonObject cannot used.");
    }

    @Override
    public Object getActualObject()
    {
      return this;
    }

    @Override
    public Object get(final String name)
    {
      throw new JsonStathamException(format("The name method in NullJsonObject cannot used.\n[input] String name: %s",
          name));
    }

    @Override
    public String toString()
    {
      return "null";
    }
  };

  private static final String[] EMPTY_NAMES = new String[0];

  private final Map<String, Object> jsonFieldMap;

  protected JsonObjectImpl(final Map<String, Object> jsonFieldMap)
  {
    this.jsonFieldMap = jsonFieldMap;
  }

  @Override
  public String[] getNames()
  {
    if (0 == jsonFieldMap.size())
      return EMPTY_NAMES;

    final Set<String> keySet = jsonFieldMap.keySet();
    return keySet.toArray(new String[keySet.size()]);
  }

  @Override
  public Object get(String name)
  {
    return jsonFieldMap.get(name);
  }

  @Override
  public JsonObjectConvertible put(String name, Object value) throws JsonStathamException
  {
    put0(name, value);
    return this;
  }

  private void put0(final String name, final Object value)
  {
    if (null == name)
      throw new JsonStathamException(format("The name must not be null.\n[input] String name: %s, Object value: %s]",
          name, value));

    validate(value);
    jsonFieldMap.put(name, value);
  }

  @Override
  public Object getActualObject()
  {
    return this;
  }

  @Override
  public String toString()
  {
    final StringBuilder stringBuilder = new StringBuilder("{");
    final Iterator<Entry<String, Object>> iterator = jsonFieldMap.entrySet()
        .iterator();

    if (iterator.hasNext())
    {
      final Entry<String, Object> field = iterator.next();
      stringBuilder.append(doubleQuote(field.getKey()))
          .append(':')
          .append(toStringValue(field.getValue()));
    }

    while (iterator.hasNext())
    {
      final Entry<String, Object> field = iterator.next();
      stringBuilder.append(',')
          .append(doubleQuote(field.getKey()))
          .append(':')
          .append(toStringValue(field.getValue()));
    }
    return stringBuilder.append('}')
        .toString();
  }

  public static JsonObjectImpl newOrderedJsonObject()
  {
    return new JsonObjectImpl(new LinkedHashMap<String, Object>());
  }

  public static JsonObjectImpl newUnorderedJsonObject()
  {
    return new JsonObjectImpl(new HashMap<String, Object>());
  }

  public static JsonObjectImpl newJsonObject(final Map<String, Object> jsonFieldMap)
  {
    return new JsonObjectImpl(jsonFieldMap);
  }
}
