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
 * @version 0.0.2 (2010-09-13)
 */
public interface JsonArrayConvertible extends JsonConvertible
{
  Object get(int index);

  <T> JsonArrayConvertible put(T value);

  int length();

  @Override
  Object getActualObject();

  @Override
  String toString();
}
