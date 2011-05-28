/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import java.util.LinkedHashMap;

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
public class OrgJsonOrderedJsonObjectConvertibleCreator extends AbstractOrgJsonJsonObjectConvertibleCreator
{
  @Override
  public JsonObjectConvertible newJsonObjectConvertible()
  {
    return new OrgJsonJsonObjectConvertible(new JSONObject(new LinkedHashMap<String, Object>()));
  }

}
