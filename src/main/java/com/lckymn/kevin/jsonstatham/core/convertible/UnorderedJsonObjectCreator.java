/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.convertible;

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
public final class UnorderedJsonObjectCreator extends AbstractJsonObjectCreator
{
  @Override
  public JsonObjectConvertible newJsonObjectConvertible()
  {
    return JsonObjectImpl.newUnorderedJsonObject();
  }
}
