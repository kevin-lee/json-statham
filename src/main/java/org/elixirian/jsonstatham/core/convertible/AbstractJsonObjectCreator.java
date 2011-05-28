/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

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
public abstract class AbstractJsonObjectCreator implements JsonObjectConvertibleCreator
{

  @Override
  public JsonObjectConvertible newJsonObjectConvertible(String jsonString) throws JsonStathamException
  {
    try
    {
      return new OrgJsonJsonObjectConvertible(new JSONObject(jsonString));
    }
    catch (JSONException e)
    {
      throw new JsonStathamException(e);
    }
  }

  @Override
  public abstract JsonObjectConvertible newJsonObjectConvertible();

  @Override
  public JsonObjectConvertible nullJsonObjectConvertible()
  {
    return JsonObjectImpl.NULL_JSON_OBJECT;
  }

}
