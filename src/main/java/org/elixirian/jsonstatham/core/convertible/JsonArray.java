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
 * @version 0.0.1 (2010-06-02)
 * @version 0.0.2 (2010-09-13)
 */
public interface JsonArray extends JsonConvertible
{
  Object get(int index);

  <T> JsonArray put(T value);

  int length();

  @Override
  Object getActualObject();

  @Override
  String toString();
}
