/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.common.util.MessageFormatter.*;

import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.json.JSONArray;
import org.json.JSONException;

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
 * @version 0.0.2 (2010-09-13)
 */
public final class OrgJsonJsonArrayConvertible implements JsonArrayConvertible
{
  private final JSONArray jsonArray;

  public OrgJsonJsonArrayConvertible(JSONArray jsonArray)
  {
    this.jsonArray = jsonArray;
  }

  @Override
  public Object get(final int index)
  {
    try
    {
      return jsonArray.get(index);
    }
    catch (JSONException e)
    {
      throw new JsonStathamException(format("[input] int index: %s", Integer.valueOf(index)), e);
    }
  }

  @Override
  public <T> JsonArrayConvertible put(T value)
  {
    if (value instanceof JsonConvertible)
    {
      jsonArray.put(((JsonConvertible) value).getActualObject());
    }
    else
    {
      jsonArray.put(value);
    }
    return this;
  }

  @Override
  public int length()
  {
    return jsonArray.length();
  }

  @Override
  public Object getActualObject()
  {
    return jsonArray;
  }

  @Override
  public String toString()
  {
    return jsonArray.toString();
  }
}
