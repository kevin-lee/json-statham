/**
 * 
 */
package org.elixirian.jsonstatham.core.convertible;

import org.elixirian.jsonstatham.exception.JsonStathamException;

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
public interface JsonObjectConvertible extends JsonConvertible
{
  String[] getNames();

  Object get(String name);

  JsonObjectConvertible put(String name, Object value) throws JsonStathamException;

  @Override
  Object getActualObject();

  @Override
  String toString();
}
