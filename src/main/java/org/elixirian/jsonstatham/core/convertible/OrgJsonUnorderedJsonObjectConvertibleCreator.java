/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import java.util.HashMap;

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
 * @version 0.0.1 (2010-02-03)
 */
public final class OrgJsonUnorderedJsonObjectConvertibleCreator extends AbstractOrgJsonJsonObjectConvertibleCreator
{
  /*
   * (non-Javadoc)
   * @see org.elixirian.jsonstatham.core.JsonObjectConvertibleCreator#newJSONObject()
   */
  @Override
  public JsonObject newJsonObjectConvertible()
  {
    return new OrgJsonJsonObjectConvertible(new JSONObject(new HashMap<String, Object>()));
  }

}
