/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import java.util.HashMap;

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
 * @version 0.0.1 (2010-02-03)
 */
public final class OrgJsonUnorderedJsonObjectConvertibleCreator extends AbstractOrgJsonJsonObjectConvertibleCreator
{
  /*
   * (non-Javadoc)
   * @see org.elixirian.jsonstatham.core.JsonObjectConvertibleCreator#newJSONObject()
   */
  @Override
  public JsonObjectConvertible newJsonObjectConvertible()
  {
    return new OrgJsonJsonObjectConvertible(new JSONObject(new HashMap<String, Object>()));
  }

}
