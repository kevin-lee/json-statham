/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.common.util.MessageFormatter.*;

import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.json.JSONException;
import org.json.JSONObject;

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
 * @version 0.0.1 (2010-06-02)
 */
public final class OrgJsonJsonObjectConvertible implements JsonObjectConvertible
{
  private static final String[] EMPTY_NAMES = new String[0];

  private final JSONObject jsonObject;

  public OrgJsonJsonObjectConvertible(JSONObject jsonObject)
  {
    this.jsonObject = jsonObject;
  }

  @Override
  public String[] getNames()
  {
    final String[] names = JSONObject.getNames(jsonObject);
    return (null == names ? EMPTY_NAMES : names);
  }

  @Override
  public Object get(final String name) throws JsonStathamException
  {
    try
    {
      return jsonObject.get(name);
    }
    catch (JSONException e)
    {
      throw new JsonStathamException(format("[input] String name: %s", name), e);
    }
  }

  @Override
  public JsonObjectConvertible put(final String name, final Object value) throws JsonStathamException
  {
    try
    {
      if (value instanceof JsonConvertible)
      {
        jsonObject.put(name, ((JsonConvertible) value).getActualObject());
      }
      else
      {
        jsonObject.put(name, value);
      }
      return this;
    }
    catch (JSONException e)
    {
      throw new JsonStathamException(format("[input] String name: %s, Object value: %s", name, value), e);
    }
  }

  @Override
  public Object getActualObject()
  {
    return jsonObject;
  }

  @Override
  public String toString()
  {
    return jsonObject.toString();
  }
}
