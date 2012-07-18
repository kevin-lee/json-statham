/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.kommonlee.util.MessageFormatter.*;

import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.json.JSONException;
import org.json.JSONObject;

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
 * @version 0.0.1 (2010-06-02)
 */
public abstract class AbstractJsonObjectCreator implements JsonObjectConvertibleCreator
{

  @Override
  public JsonObjectConvertible newJsonObjectConvertible(final String jsonString) throws JsonStathamException
  {
    try
    {
      return new OrgJsonJsonObjectConvertible(new JSONObject(jsonString));
    }
    catch (final JSONException e)
    {
      throw new JsonStathamException(format("[input] String jsonString: %s", jsonString), e);
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
