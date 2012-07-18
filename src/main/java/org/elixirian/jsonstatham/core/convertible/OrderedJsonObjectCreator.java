/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

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
 * @version 0.0.1 (2010-12-25)
 */
public class OrderedJsonObjectCreator extends AbstractJsonObjectCreator
{
  @Override
  public JsonObjectConvertible newJsonObjectConvertible()
  {
    return JsonObjectImpl.newOrderedJsonObject();
  }
}
