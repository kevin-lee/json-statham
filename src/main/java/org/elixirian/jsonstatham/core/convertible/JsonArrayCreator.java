/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

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
public class JsonArrayCreator implements JsonArrayConvertibleCreator
{

  @Override
  public JsonArrayConvertible newJsonArrayConvertible()
  {
    return JsonArray.newJsonArray();
  }

  @Override
  public JsonArrayConvertible newJsonArrayConvertible(String jsonString)
  {
    return JsonArray.newJsonArray(jsonString);
  }
}
