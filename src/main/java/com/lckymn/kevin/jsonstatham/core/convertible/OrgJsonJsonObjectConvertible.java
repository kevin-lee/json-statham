/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

import org.json.JSONException;
import org.json.JSONObject;

import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

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
  public Object get(String name) throws JsonStathamException
  {
    try
    {
      return jsonObject.get(name);
    }
    catch (JSONException e)
    {
      throw new JsonStathamException(e);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.lckymn.kevin.jsonstatham.core.JsonObjectConvertible#put(java.lang.String, java.lang.Object)
   */
  @Override
  public JsonObjectConvertible put(String name, Object value) throws JsonStathamException
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
      throw new JsonStathamException(e);
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
